# Setting up Zookeeper Cluster on EC2


After making necessary changes in `constants.sh`. Run the following command:

```
bash driver.sh setup_zk_hosts_file.sh setup_zk_file_prereq.sh setup_zk_config.sh setup_zk_env_vars.sh setup_zk_server_script.sh
```


Description of shell scripts:
- `driver.sh` : Sends the script paramters to AWS instances one by one
- `setup_zk_hosts_file.sh` : Add IP and hostname on AWS Instances
- `setup_zk_file_prereq.sh` : Download, extract the proper zookeeper version
- `setup_zk_config.sh` : Creates configuration file zoo.cfg
- `setup_zk_env_vars.sh` : Exports necessary environment variables
- `setup_zk_server_script.sh` : Starts server on all the instances. Make sure this script is execute in the end

To check whether quorum is working properly use `echo srve|nc <zk instance address> <default port>` on all the quorm member.
Note: In constants `aws_ip` should be private IP (preferrably)
