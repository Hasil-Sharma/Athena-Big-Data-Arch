source $REMOTE_SCRIPT_FOLDER/constants.sh

nohup $STORM_HOME/bin/storm supervisor > /dev/null 2>&1 &
