source $REMOTE_SCRIPT_FOLDER/constants.sh

mkdir -p ~/.ssh

mv $REMOTE_SCRIPT_FOLDER/$KEY ~/.ssh
chmod 400 ~/.ssh/$KEY

cat << EOF > ~/.ssh/config
HOST *amazonaws.com
StrictHostKeyChecking no
IdentityFile ~/.ssh/$KEY
User ec2-user
EOF

chmod 600 ~/.ssh/config
