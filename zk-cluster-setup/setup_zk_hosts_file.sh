source $REMOTE_SCRIPT_FOLDER/constants.sh

declare -a HOST_ARRAY=()
for i in "${!AWS_IP[@]}"; do
  HOST_ARRAY[$i]=$(printf "${AWS_IP[i]}\t$ZK_HOST_STRING$(($i + 1))")
done

printf "%s\n" "${HOST_ARRAY[@]}" | sudo tee -a $HOST_FILE
