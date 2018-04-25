source $REMOTE_SCRIPT_FOLDER/constants.sh

cp $MAPRED_SITE.template $MAPRED_SITE

sed -i "/<\/configuration>/i \
  <property>\
   <name>mapreduce.framework.name</name>\
   <value>$MAPREDUCE_FRAMEWORK_NAME</value>\
 </property>" $MAPRED_SITE

sed -i "/<\/configuration>/i \
 <property>\
  <name>yarn.app.mapreduce.am.resource.mb</name>\
  <value>$YARN_APP_MAPREDUCE_AM_RESOURCE_MB</value>\
</property>" $MAPRED_SITE

sed -i "/<\/configuration>/i \
 <property>\
  <name>mapreduce.map.memory.mb</name>\
  <value>$MAPREDUCE_MAP_MEMORY_MB</value>\
</property>" $MAPRED_SITE

sed -i "/<\/configuration>/i \
  <property>\
   <name>mapreduce.reduce.memory.mb</name>\
   <value>$MAPREDUCE_REDUCE_MEMORY_MB</value>\
 </property>" $MAPRED_SITE

xmllint --format $MAPRED_SITE > $MAPRED_SITE.tmp
mv $MAPRED_SITE.tmp $MAPRED_SITE
