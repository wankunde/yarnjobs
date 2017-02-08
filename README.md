# 程序说明

* 启动正在运行的Job监控程序  ` nohup ./runningapp.sh  & >/dev/null 2>&1 `
* 启动job信息收集程序  ` nohup ./daemon.sh  & >/dev/null 2>&1 `
* 程序收集结果表 

> apps 
> jobs
> jobconf
> running

# Restful API

* [ResourceManager REST API’s.](http://hadoop.apache.org/docs/current/hadoop-yarn/hadoop-yarn-site/ResourceManagerRest.html)
* [MapReduce History Server REST API’s.](http://hadoop.apache.org/docs/current/hadoop-mapreduce-client/hadoop-mapreduce-client-hs/HistoryServerRest.html#Job_API)

