source $REMOTE_SCRIPT_FOLDER/constants.sh

if [ ! -f $SPARK_DOWNLOAD_ZIP ]; then
  wget --quiet $SPARK_DOWNLOAD_URL
else
  echo "Already Downloaded"
fi

tar -xf $SPARK_DOWNLOAD_ZIP
if [ ! -f $SCALA_DOWNLOAD ]; then
  wget http://downloads.lightbend.com/scala/2.11.0/scala-2.11.0.rpm
  sudo yum -y install scala-2.11.0.rpm
fi

echo "SCALA VERSION: $(scala -version)"
