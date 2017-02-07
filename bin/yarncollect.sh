#!/bin/bash

if [ $# != 1 ] ; then
    echo "Usage: $0 <hour>          which hour to collect." 1>&2;
    exit 1;
fi

java -jar yarnjobs-1.0-assembly.jar $1
if [[ $? -eq 0 ]];then
hive -e "load data local inpath '/tmp/yarnjobs/apps/$1' overwrite into table tmp.apps partition(dt=$1);"
hive -e "load data local inpath '/tmp/yarnjobs/conf/$1' overwrite into table tmp.jobconf partition(dt=$1);"
fi
rm -rf /tmp/yarnjobs/apps/$1 /tmp/yarnjobs/conf/$1