source ./constants.sh

for i in "${!aws_instances[@]}"; do
  ssh -o "StrictHostKeyChecking no" ${aws_instances[i]} "rm -rf $REMOTE_SCRIPT_FOLDER;mkdir -p $REMOTE_SCRIPT_FOLDER"
  scp -o "StrictHostKeyChecking no" ./constants.sh ${aws_instances[i]}:$REMOTE_SCRIPT_FOLDER/constants.sh
  echo -e "${RED}For: ${aws_instances[i]}${NC}"

  for point; do
    echo -e "${RED}Running $point script${NC}"
    ssh -o "StrictHostKeyChecking no" ${aws_instances[i]} "IDX=$i REMOTE_SCRIPT_FOLDER=$REMOTE_SCRIPT_FOLDER bash -s" < $point
  done;
done
