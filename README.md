# Athena

![Image Demo](https://github.com/Hasil-Sharma/Athena-Big-Data-Arch/blob/master/Athena%20Demo.png)

Demo Video: https://www.youtube.com/watch?v=EP59ny__mq4

Real time tweet monitoring system to identify toxic tweets and mapping them on Kibana Map accorind to randomized locations.

Specifications:
- Java 7
- Scala 2.11.12
- sbt 0.13.16

Running Sample Storm Project:

`sbt clean compile assembly`
`java -jar target/scala-2.11/athena-assembly-0.1-SNAPSHOT.jar`

Running `TwitterLiveStream` to take json dump

`sbt 'project utils' clean assembly`

On Spark Master execute the following command:
```
spark-submit --master <master-url> --class spark.TwitterLiveStream <jar-file> <output-location>
```

Output Location - Can be either an HDFS or S3 bucket path


Links:
- https://github.com/sbt/sbt-assembly
- https://pbassiner.github.io/blog/defining_multi-project_builds_with_sbt.html
- http://jsuereth.com/scala/2013/06/11/effective-sbt.html
- https://dzone.com/articles/wordcount-with-storm-and-scala
