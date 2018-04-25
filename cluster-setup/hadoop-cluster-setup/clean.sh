source $REMOTE_SCRIPT_FOLDER/constants.sh

rm -rf $HADOOP_PREFIX
rm -rf $DFS_NAMENODE_NAME_DIR
rm -rf $DFS_DATANODE_DATA_DIR
rm -rf $REMOTE_SCRIPT_FOLDER
rm -rf $YARN_NODEMANGER_LOCAL_DIRS
rm -rf $YARN_NODEMANGER_LOG_DIRS
rm -rf $HADOOP_PREFIX/logs
ps aux | grep "hadoop" | awk {'print $2'} | xargs kill -9
sudo rm -rf $HADOOP_PREFIX_FILE
