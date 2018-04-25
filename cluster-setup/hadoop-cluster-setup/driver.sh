source ./constants.sh

chmod 400 $KEY

for instance in "${aws_instances[@]}"
do
  ssh -o "StrictHostKeyChecking no" $instance "rm -rf $REMOTE_SCRIPT_FOLDER;mkdir -p $REMOTE_SCRIPT_FOLDER"
  scp -o "StrictHostKeyChecking no" ./constants.sh $USER@$instance:$REMOTE_SCRIPT_FOLDER/constants.sh
  scp -o "StrictHostKeyChecking no" $KEY $instance:$REMOTE_SCRIPT_FOLDER/$KEY
  echo -e "${RED}For: $instance${NC}"
  for point; do
    echo -e "5. ${RED}Running $point script${NC}"
    ssh -o "StrictHostKeyChecking no" $instance "REMOTE_SCRIPT_FOLDER=$REMOTE_SCRIPT_FOLDER bash -s" < $point
  done
done

if [ ! $point == "clean.sh" ]; then
  ssh -o "StrictHostKeyChecking no" ${aws_instances[0]} "$HADOOP_PREFIX/bin/hdfs namenode -format athena_thinking"
  ssh -o "StrictHostKeyChecking no" ${aws_instances[0]} "$HADOOP_YARN_HOME/sbin/yarn-daemon.sh --config $HADOOP_CONF_DIR start resourcemanager"
  ssh -o "StrictHostKeyChecking no" ${aws_instances[0]} "$HADOOP_PREFIX/sbin/hadoop-daemon.sh --config $HADOOP_CONF_DIR --script hdfs start namenode"

  ssh -o "StrictHostKeyChecking no" ${aws_instances[1]} "$HADOOP_PREFIX/sbin/hadoop-daemons.sh --config $HADOOP_CONF_DIR --script hdfs start datanode"
  ssh -o "StrictHostKeyChecking no" ${aws_instances[1]} "$HADOOP_YARN_HOME/sbin/yarn-daemons.sh --config $HADOOP_CONF_DIR start nodemanager"
  ssh -o "StrictHostKeyChecking no" ${aws_instances[1]} "$HADOOP_YARN_HOME/sbin/yarn-daemon.sh --config $HADOOP_CONF_DIR start proxyserver"

  ssh -o "StrictHostKeyChecking no" ${aws_instances[0]} "$HADOOP_PREFIX/bin/hadoop dfs -mkdir /user"
  ssh -o "StrictHostKeyChecking no" ${aws_instances[0]} "$HADOOP_PREFIX/bin/hadoop dfs -mkdir /user/$USER"
fi
