source ./constants.sh

chmod 400 $KEY

for instance in "${AWS_INSTANCES[@]}"
do
  ssh -o "StrictHostKeyChecking no" $instance "rm -rf $REMOTE_SCRIPT_FOLDER;mkdir -p $REMOTE_SCRIPT_FOLDER"
  scp -o "StrictHostKeyChecking no" ./constants.sh $USER@$instance:$REMOTE_SCRIPT_FOLDER/constants.sh
  scp -o "StrictHostKeyChecking no" $KEY $instance:$REMOTE_SCRIPT_FOLDER/$KEY
  echo -e "${RED}For: $instance${NC}"
  for point; do
    if [ $point == "setup_slaves_on_master.sh" ]; then
      if [ $instance == $SPARK_MASTER ]; then
        echo -e "${RED}Running $point script${NC}"
        ssh -o "StrictHostKeyChecking no" $instance \
        "REMOTE_SCRIPT_FOLDER=$REMOTE_SCRIPT_FOLDER CURRENT_INSTANCE_DNS=$instance\
        bash -s" < $point
      fi
    else
      echo -e "${RED}Running $point script${NC}"
      ssh -o "StrictHostKeyChecking no" $instance \
      "REMOTE_SCRIPT_FOLDER=$REMOTE_SCRIPT_FOLDER CURRENT_INSTANCE_DNS=$instance\
      bash -s" < $point
    fi
  done
done

if [ ! $instance == "clean.sh" ]; then
  ssh -o "StrictHostKeyChecking no" $SPARK_MASTER "$SPARK_HOME/sbin/start-all.sh"
fi
