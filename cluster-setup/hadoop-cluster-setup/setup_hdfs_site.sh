source $REMOTE_SCRIPT_FOLDER/constants.sh
cp $HDFS_SITE $HDFS_SITE.bak

mkdir -p $DFS_DATANODE_DATA_DIR
mkdir -p $DFS_NAMENODE_NAME_DIR

sed -i "/<\/configuration>/i \
  <property>\
   <name>dfs.datanode.data.dir</name>\
   <value>$DFS_DATANODE_DATA_DIR</value>\
 </property>" $HDFS_SITE

sed -i "/<\/configuration>/i \
  <property>\
    <name>dfs.namenode.name.dir</name>\
    <value>$DFS_NAMENODE_NAME_DIR</value>\
  </property>" $HDFS_SITE

sed -i "/<\/configuration>/i \
  <property>\
   <name>dfs.blocksize</name>\
   <value>$DFS_BLOCK_SIZE</value>\
  </property>" $HDFS_SITE

 sed -i "/<\/configuration>/i \
  <property>\
    <name>dfs.namenode.handler.count</name>\
    <value>$DFS_NAMENODE_HANDLER_COUNT</value>\
  </property>" $HDFS_SITE

  sed -i "/<\/configuration>/i \
   <property>\
     <name>dfs.replication</name>\
     <value>$DFS_REPLICATION</value>\
   </property>" $HDFS_SITE

xmllint --format $HDFS_SITE > $HDFS_SITE.tmp
mv $HDFS_SITE.tmp $HDFS_SITE
