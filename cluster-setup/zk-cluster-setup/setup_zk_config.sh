source $REMOTE_SCRIPT_FOLDER/constants.sh

cp $ZK_HOME/conf/zoo_sample.cfg $ZK_CONF_FILE

sudo mkdir -p $ZK_DATA_DIR
sudo chown -R $USER $ZK_DATA_DIR

sed -i /^dataDir/d $ZK_CONF_FILE
printf "dataDir=$ZK_DATA_DIR\n" >> $ZK_CONF_FILE

for i in "${!AWS_IP[@]}"; do
  printf "server.$(($i + 1))=$ZK_HOST_STRING$(($i + 1)):2888:3888\n" >> $ZK_CONF_FILE
done

echo $ZK_ID > $ZK_DATA_DIR/myid
