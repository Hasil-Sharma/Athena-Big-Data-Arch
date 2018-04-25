source $REMOTE_SCRIPT_FOLDER/constants.sh

rm -rf $KAFKA_DIR
rm -rf $REMOTE_SCRIPT_FOLDER
ps aux | grep kafka | awk {'print $2'} | xargs kill -9
