source $REMOTE_SCRIPT_FOLDER/constants.sh

# sudo yum -y update
if [ ! -f $HADOOP_ZIP ]; then
  wget --quiet $HADOOP_DOWNLOAD_URL
else
  echo "Tar found Skipping download"
fi

tar -xf $HADOOP_ZIP
