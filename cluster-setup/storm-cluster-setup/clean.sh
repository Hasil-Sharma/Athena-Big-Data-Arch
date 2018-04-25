source $REMOTE_SCRIPT_FOLDER/constants.sh

rm -rf $STORM_HOME
ps aux | grep "storm" | awk '{ print $2 }' | xargs kill -9
sudo rm -rf $STORM_ENV_FILE
sudo sed -i /$NIMBUS_STR/d $HOST_FILE
sudo sed -i /$SUPERVISOR_STR./d $HOST_FILE
sudo sed -i /$ZK_HOST_STRING./d $HOST_FILE
