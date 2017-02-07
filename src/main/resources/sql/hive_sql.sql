create table apps(
id String,
user String,
name String,
queue String,
state String,
finalStatus String,
progress String,
trackingUI String,
trackingUrl String,
diagnostics String,
clusterId String,
applicationType String,
applicationTags String,
startedTime String,
finishedTime String,
elapsedTime String,
amContainerLogs String,
amHostHttpAddress String,
allocatedMB String,
allocatedVCores String,
runningContainers String,
memorySeconds String,
vcoreSeconds String,
preemptedResourceMB String,
preemptedResourceVCores String,
numNonAMContainerPreempted String,
numAMContainerPreempted String,
logAggregationStatus String
) partitioned by(dt String)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '\001';

create table jobs(
submitTime BIGINT ,
startTime BIGINT ,
finishTime BIGINT,
id String,
name String,
queue String,
user String,
state String,
mapsTotal BIGINT,
mapsCompleted BIGINT,
reducesTotal BIGINT,
reducesCompleted BIGINT
) partitioned by(dt String)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '\001';

create table jobconf(
id String,
param map<string,string>
) partitioned by(dt String)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '\001'
COLLECTION ITEMS TERMINATED BY '\003'
MAP KEYS TERMINATED BY '\004';


select id from tmp.apps where dt=2017020711 limit 10;


alter table apps drop partition (dt=2017020711);


load data local inpath '/tmp/yarnjobs/apps/2017020711' overwrite into table tmp.apps partition(dt=2017020711);


load data local inpath '/tmp/yarnjobs/conf/2017020711' overwrite into table tmp.jobconf partition(dt=2017020711);


select * from tmp.jobconf where dt=2017020711 limit 10;
alter table jobconf drop partition (dt=2017020711);