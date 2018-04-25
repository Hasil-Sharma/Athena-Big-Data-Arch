source $REMOTE_SCRIPT_FOLDER/constants.sh
cp $CORE_SITE $CORE_SITE.bak

sed -i "/<\/configuration>/i \
  <property>\
   <name>fs.defaultFS</name>\
   <value>$NAMENODE_PORT</value>\
 </property>" $CORE_SITE

 sed -i "/<\/configuration>/i \
   <property>\
    <name>io.file.buffer.size</name>\
    <value>$IO_FILE_BUFFER_SIZE</value>\
  </property>" $CORE_SITE

 xmllint --format $CORE_SITE > $CORE_SITE.tmp
 mv $CORE_SITE.tmp $CORE_SITE
