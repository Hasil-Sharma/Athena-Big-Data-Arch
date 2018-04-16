source ./constants.sh

chmod 400 $KEY

ZK_ID=1
for instance in "${AWS_INSTANCES[@]}"
do
  ssh -o "StrictHostKeyChecking no" $instance "rm -rf $REMOTE_SCRIPT_FOLDER;mkdir -p $REMOTE_SCRIPT_FOLDER"
  scp -o "StrictHostKeyChecking no" ./constants.sh $USER@$instance:$REMOTE_SCRIPT_FOLDER/constants.sh
  scp -o "StrictHostKeyChecking no" $KEY $instance:$REMOTE_SCRIPT_FOLDER/$KEY
  echo -e "${RED}For: $instance${NC}"
  for point; do
      echo -e "${RED}Running $point script${NC}"
      ssh -o "StrictHostKeyChecking no" $instance "ZK_ID=$ZK_ID REMOTE_SCRIPT_FOLDER=$REMOTE_SCRIPT_FOLDER bash -s" < $point
  done
  ZK_ID=$(($ZK_ID + 1))
done
