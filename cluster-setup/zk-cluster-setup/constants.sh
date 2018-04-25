declare -a AWS_INSTANCES=(
  ec2-54-69-189-144.us-west-2.compute.amazonaws.com
  ec2-54-200-15-109.us-west-2.compute.amazonaws.com
  ec2-34-217-46-94.us-west-2.compute.amazonaws.com
)

declare -a AWS_IP=(
  172.31.41.4
  172.31.38.83
  172.31.32.16
)

RED='\033[0;31m'
NC='\033[0m' # No Color

KEY=thinking-athena.pem
USER=ec2-user
REMOTE_SCRIPT_FOLDER=/home/$USER/TEMP

ZK_DOWNLOAD_URL=http://apache.claz.org/zookeeper/zookeeper-3.4.11/zookeeper-3.4.11.tar.gz
ZK_DOWNLOAD_ZIP=zookeeper-3.4.11.tar.gz
ZK_HOME=/home/$USER/zookeeper-3.4.11

HOST_FILE=/etc/hosts
ZK_HOST_STRING=ZK
ZK_CONF_FILE=$ZK_HOME/conf/zoo.cfg
ZK_DATA_DIR=/var/zookeeper

ZK_ENV_FILE=/etc/profile.d/zookeeper.sh
ZK_SERVER_START_SH=$ZOOKEEPER_HOME/start-zk-server.sh
