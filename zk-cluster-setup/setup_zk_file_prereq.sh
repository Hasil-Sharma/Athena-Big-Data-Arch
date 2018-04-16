source $REMOTE_SCRIPT_FOLDER/constants.sh


if [ ! -f $ZK_DOWNLOAD_ZIP ]; then
  wget $ZK_DOWNLOAD_URL
fi

tar -xf $ZK_DOWNLOAD_ZIP
