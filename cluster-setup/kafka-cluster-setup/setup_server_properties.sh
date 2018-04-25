source $REMOTE_SCRIPT_FOLDER/constants.sh

KAFKA_CONF_FILE=$KAFKA_CONF_DIR/server.properties

sed -i "s/^broker.id.*/broker.id=$IDX/" $KAFKA_CONF_FILE
sed -i "s/^zookeeper.connect=.*/zookeeper.connect=$ZOOKEEPER_SERVER/" $KAFKA_CONF_FILE
