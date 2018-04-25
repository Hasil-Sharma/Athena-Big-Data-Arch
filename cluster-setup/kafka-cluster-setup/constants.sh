declare -a aws_instances=(
  kafka-1
  kafka-2
  kafka-3
  kafka-4
)

KAFKA_URL=http://apache.claz.org/kafka/1.1.0/kafka_2.11-1.1.0.tgz
KAFKA_ZIP=kafka_2.11-1.1.0.tgz
KAFKA_DIR=kafka_2.11-1.1.0
USER=ec2-user
REMOTE_SCRIPT_FOLDER=/home/$USER/TEMP

RED='\033[0;31m'
NC='\033[0m' # No Color

KAFKA_CONF_DIR=/home/$USER/$KAFKA_DIR/config
ZOOKEEPER_SERVER=ec2-54-69-189-144.us-west-2.compute.amazonaws.com:2181,ec2-54-200-15-109.us-west-2.compute.amazonaws.com:2181,ec2-34-217-46-94.us-west-2.compute.amazonaws.com:2181
