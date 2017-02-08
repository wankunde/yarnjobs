#!/bin/bash

while true;
do
  dt=$(date +%Y%m%d)
  yesterday=$(date -d "-1 days" +%Y%m%d)

  java -cp yarnjobs-1.0-assembly.jar com.paic.data.yarnjobs.RunningApp
  if [[ $? -eq 0 ]];then
  hive -e "load data local inpath '/tmp/yarnjobs/running/${dt}' overwrite into table tmp.running partition(dt=${dt});"
  fi
  rm -rf /tmp/yarnjobs/running/${yesterday}


  dt=$(date -d "-1 hours" +%Y%m%d%H)
  nohup /home/dvpusr/wankun/yarncollect.sh $dt &
  sleep 10m
done