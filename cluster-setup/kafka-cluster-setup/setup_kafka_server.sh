source $REMOTE_SCRIPT_FOLDER/constants.sh

KAFKA_CONF_FILE=$KAFKA_CONF_DIR/server.properties

export KAFKA_HEAP_OPTS="-Xms1G -Xmx3G"
nohup $KAFKA_DIR/bin/kafka-server-start.sh $KAFKA_CONF_FILE > ~/kafka.log 2>kafka.err < /dev/null &
