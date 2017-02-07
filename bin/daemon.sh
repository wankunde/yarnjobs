#!/bin/bash

# nohup /home/dvpusr/wankun/yarncollect.sh $dt & >/dev/null 2>&1 

while true;
do
  dt=$(date -d "-1 hours" +%Y%m%d%H)
  nohup /home/dvpusr/wankun/yarncollect.sh $dt & 
  sleep 1h
done
