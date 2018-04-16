source $REMOTE_SCRIPT_FOLDER/constants.sh

sudo sed -i /^$ZK_HOST_STRING/d $HOST_FILE
rm -rf $ZK_HOME
sudo rm -rf $ZK_DATA_DIR
sudo rm -rf $ZK_ENV_FILE

ps aux | grep zookeeper | awk {'print $2'}  | xargs kill -9
