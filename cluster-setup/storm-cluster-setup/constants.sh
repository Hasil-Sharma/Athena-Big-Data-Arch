# Corresponding mapping done on local in /etc/hosts
declare -a AWS_INSTANCES=(
  ec2-52-88-7-4.us-west-2.compute.amazonaws.com
  ec2-54-244-154-90.us-west-2.compute.amazonaws.com
  ec2-54-202-70-34.us-west-2.compute.amazonaws.com
  ec2-35-163-100-137.us-west-2.compute.amazonaws.com
)

# Public IP Addresses
declare -a ZK_SERVERS=(
  ec2-54-69-189-144.us-west-2.compute.amazonaws.com
  ec2-54-200-15-109.us-west-2.compute.amazonaws.com
  ec2-34-217-46-94.us-west-2.compute.amazonaws.com
)

declare -a SUPERVISOR_SLOTS=(
  6700
  6701
  6702
)
RED='\033[0;31m'
NC='\033[0m' # No Color

USER=ec2-user
REMOTE_SCRIPT_FOLDER=/home/$USER/TEMP

STORM_DOWNLOAD_URL=http://apache.claz.org/storm/apache-storm-1.2.1/apache-storm-1.2.1.tar.gz
STORM_DOWNLOAD_ZIP=apache-storm-1.2.1.tar.gz
STORM_HOME=/home/$USER/apache-storm-1.2.1

HOST_FILE=/etc/hosts
NIMBUS_HOST=ec2-52-88-7-4.us-west-2.compute.amazonaws.com
NIMBUS_STR=nimbus_host
SUPERVISOR_STR=supervisor-
ZK_HOST_STRING=ZK

STORM_CONF_DIR=$STORM_HOME/conf
STORM_CONF_FILE=$STORM_HOME/conf/storm.yaml
STORM_LOCAL_DIR=$STORM_HOME/storm-local
STORM_ENV_FILE=/etc/profile.d/storm.sh
