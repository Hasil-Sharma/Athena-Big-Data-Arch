source $REMOTE_SCRIPT_FOLDER/constants.sh

echo "storm.zookeeper.servers:" >> $STORM_CONF_FILE

for i in "${!ZK_SERVERS[@]}"; do
  printf " - \"$(getent hosts ${ZK_SERVERS[$i]} | awk '{ print $1 }')\"\n" >> $STORM_CONF_FILE
done


echo "storm.local.dir: \"$STORM_LOCAL_DIR\"" >> $STORM_CONF_FILE

echo "nimbus.seeds: [ $NIMBUS_STR ]" >> $STORM_CONF_FILE

echo "supervisor.slots.ports:" >> $STORM_CONF_FILE

for port in "${SUPERVISOR_SLOTS[@]}"; do
  printf " - $port\n" >> $STORM_CONF_FILE
done
