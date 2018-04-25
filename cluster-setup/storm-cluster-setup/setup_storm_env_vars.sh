source $REMOTE_SCRIPT_FOLDER/constants.sh

cat <<EOM > /tmp/new.file
STORM_HOME=$STORM_HOME
STORM_CONF_DIR=$STORM_CONF_DIR
EOM

sudo mv /tmp/new.file $STORM_ENV_FILE
