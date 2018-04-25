# Storm Cluster Setup on AWS EC2

After updating `constants.sh` with the AWS and ZK host instances run the following command from you local. This command assumes that you've already setup trust with EC2 instances and added proper configuration in `~/.ssh/config` to be able to ssh into remote nodes without specifying the key.

```
bash driver.sh setup_storm_file_prereq.sh setup_storm_config.sh setup_storm_env_vars.sh setup_storm_host_file.sh
```
