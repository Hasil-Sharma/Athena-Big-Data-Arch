source ./constants.sh


for instance in "${AWS_INSTANCES[@]}"
do
  ssh -o "StrictHostKeyChecking no" $instance "rm -rf $REMOTE_SCRIPT_FOLDER;mkdir -p $REMOTE_SCRIPT_FOLDER"
  scp -o "StrictHostKeyChecking no" ./constants.sh $USER@$instance:$REMOTE_SCRIPT_FOLDER/constants.sh
  for point; do
      echo -e "${RED}Running $point script${NC}"
      ssh -o "StrictHostKeyChecking no" $instance "REMOTE_SCRIPT_FOLDER=$REMOTE_SCRIPT_FOLDER bash -s" < $point
  done
done

if [ ! $point == "clean.sh" ]; then
  for i in "${!AWS_INSTANCES[@]}"; do
    if [ $i == 0 ]; then
      ssh -o "StrictHostKeyChecking no" ${AWS_INSTANCES[$i]} "REMOTE_SCRIPT_FOLDER=$REMOTE_SCRIPT_FOLDER bash -s" < ./nimbus.sh
    else
      ssh -o "StrictHostKeyChecking no" ${AWS_INSTANCES[$i]} "REMOTE_SCRIPT_FOLDER=$REMOTE_SCRIPT_FOLDER bash -s" < ./supervisor.sh
    fi
  done
fi
