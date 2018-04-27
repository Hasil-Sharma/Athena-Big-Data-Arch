package spark

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.udf
import org.apache.spark.sql.functions.col

import scala.tools.nsc.interpreter.InputStream
import scala.util.Random

case class USCity(city: String, state_id: String, county_name: String, latitude: Double, longitude: Double)

object MorphFromWiki {

  private val randomLocations = {
    val stream: InputStream = getClass.getResourceAsStream("/uscities.csv")
    val lines = scala.io.Source.fromInputStream(stream).getLines()

    val list = lines.map({
      line => {
        val cols = line.split(",")
        USCity(cols(0), cols(1), cols(2), cols(3).toDouble, cols(4).toDouble)
      }
    }).toList

    stream.close()

    list
  }

  def main(args: Array[String]): Unit = {

    val year = args(0)
    val inputFile = args(1)
    val outputFile = args(2)

    val sparkSession = SparkSession.builder
      .appName(f"User Morph Job - $year")
      .config("spark.executor.memory", "2560m")
      .config("spark.driver.memory", "2560m")
      .getOrCreate()

    sparkSession.conf.set("spark.hadoop.mapred.output.committer.class","com.appsflyer.spark.DirectOutputCommitter")
    sparkSession.conf.set("mapreduce.fileoutputcommitter.marksuccessfuljobs", "false")

    val userDf = sparkSession.read.format("csv")
      .option("header", "true")
      .option("delimiter", "\t")
      .load(inputFile)
      .repartition(3)

    val sanitizeString = udf((s:String) => s
      .replaceAll("NEWLINE", "")
      .replaceAll("""[\p{Punct}&&[^.]]""", "")
      .replaceAll("TAB", "")
      .stripMargin
    )

    val randomLocation = udf({
      () => {
        randomLocations(Random.nextInt(randomLocations.size))
        }
      })

    val cleanUserDf = userDf.withColumn("clean_comment", sanitizeString(col("comment")))
    val addedColDf = cleanUserDf.withColumn("location", randomLocation())
    val selectCols = Seq("user_text", "clean_comment", "location")
    val cleanUserSubDf = addedColDf.select(selectCols.head, selectCols.tail: _*)

    cleanUserSubDf.write.json(outputFile)
    sparkSession.close()
  }
}
