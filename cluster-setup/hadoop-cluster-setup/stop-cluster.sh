source ./constants.sh
ssh -o "StrictHostKeyChecking no" ${aws_instances[0]} "$HADOOP_PREFIX/sbin/hadoop-daemon.sh --config $HADOOP_CONF_DIR --script hdfs stop namenode"
ssh -o "StrictHostKeyChecking no" ${aws_instances[0]} "$HADOOP_YARN_HOME/sbin/yarn-daemon.sh --config $HADOOP_CONF_DIR stop resourcemanager"
ssh -o "StrictHostKeyChecking no" ${aws_instances[0]} "$HADOOP_PREFIX/sbin/mr-jobhistory-daemon.sh --config $HADOOP_CONF_DIR stop historyserver"

ssh -o "StrictHostKeyChecking no" ${aws_instances[1]} "$HADOOP_PREFIX/sbin/hadoop-daemons.sh --config $HADOOP_CONF_DIR --script hdfs stop datanode"
ssh -o "StrictHostKeyChecking no" ${aws_instances[1]} "$HADOOP_YARN_HOME/sbin/yarn-daemons.sh --config $HADOOP_CONF_DIR stop nodemanager"
ssh -o "StrictHostKeyChecking no" ${aws_instances[1]} "$HADOOP_YARN_HOME/sbin/yarn-daemon.sh --config $HADOOP_CONF_DIR stop proxyserver"
