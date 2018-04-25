source $REMOTE_SCRIPT_FOLDER/constants.sh

nohup $STORM_HOME/bin/storm nimbus > /dev/null 2>&1 &
nohup $STORM_HOME/bin/storm ui > /dev/null 2>&1 &
