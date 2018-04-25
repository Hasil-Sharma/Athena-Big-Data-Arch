source $REMOTE_SCRIPT_FOLDER/constants.sh

if [ ! -f $KAFKA_ZIP ]; then
  wget --quiet $KAFKA_URL
else
  echo "Tar found skipping download"
fi

tar -xf $KAFKA_ZIP
