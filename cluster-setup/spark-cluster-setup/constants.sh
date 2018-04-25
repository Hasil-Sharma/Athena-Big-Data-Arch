declare -a AWS_INSTANCES=(
  ec2-18-144-49-182.us-west-1.compute.amazonaws.com
  ec2-54-67-55-182.us-west-1.compute.amazonaws.com
  ec2-13-56-20-162.us-west-1.compute.amazonaws.com
  ec2-13-56-210-32.us-west-1.compute.amazonaws.com
)

SPARK_MASTER=${AWS_INSTANCES[0]}
SPARK_SLAVES=(
  ${AWS_INSTANCES[1]}
  ${AWS_INSTANCES[2]}
  ${AWS_INSTANCES[3]}
)

SPARK_DOWNLOAD_URL=http://apache.claz.org/spark/spark-2.1.2/spark-2.1.2-bin-hadoop2.7.tgz
SPARK_DOWNLOAD_ZIP=spark-2.1.2-bin-hadoop2.7.tgz
SPARK_VAR_FILE=/etc/profile.d/spark.sh
SCALA_DOWNLOAD=scala-2.11.0.rpm
KEY=athena-thinking-N-Cali.pem
USER=ec2-user

REMOTE_SCRIPT_FOLDER=/home/$USER/TEMP
RED='\033[0;31m'
NC='\033[0m' # No Color


MASTER=${AWS_INSTANCES[0]}
SPARK_HOME=/home/$USER/spark-2.1.2-bin-hadoop2.7

SPARK_ENV_SH=$SPARK_HOME/conf/spark-env.sh
SPARK_WORKER_CORES=1
