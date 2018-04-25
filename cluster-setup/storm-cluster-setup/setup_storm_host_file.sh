source $REMOTE_SCRIPT_FOLDER/constants.sh

declare -a HOST_ARRAY=()

for i in "${!AWS_INSTANCES[@]}"; do
  if [ $i == 0 ]; then
    HOST_ARRAY[$i]=$(printf "$(getent hosts ${AWS_INSTANCES[$i]} | awk '{ print $1 }')\t$NIMBUS_STR")
  else
    HOST_ARRAY[$i]=$(printf "$(getent hosts ${AWS_INSTANCES[$i]} | awk '{ print $1 }')\t$SUPERVISOR_STR$i")
  fi
done

printf "%s\n" "${HOST_ARRAY[@]}" | sudo tee -a $HOST_FILE

declare -a HOST_ARRAY=()

for i in "${!ZK_SERVERS[@]}"; do
    HOST_ARRAY[$i]=$(printf "$(getent hosts ${ZK_SERVERS[$i]} | awk '{ print $1 }')\t$ZK_HOST_STRING$(($i + 1))")
done

printf "%s\n" "${HOST_ARRAY[@]}" | sudo tee -a $HOST_FILE
