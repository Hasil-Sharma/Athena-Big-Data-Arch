source $REMOTE_SCRIPT_FOLDER/constants.sh

cat << EOF > $SLAVES_FILE
${aws_instances[1]}
${aws_instances[2]}
${aws_instances[3]}
EOF
