package com.paic.data.yarnjobs;

import com.alibaba.fastjson.JSONObject;
import com.paic.data.yarnjobs.bean.*;
import com.paic.data.yarnjobs.util.HttpUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.paic.data.yarnjobs.bean.MonitorType.*;

/**
 * Created by WANKUN603 on 2017-02-08.
 */
public class RunningApp {
    private static final Logger logger = LoggerFactory.getLogger(RunningApp.class);

    public static void main(String[] args) {
        if (!Constant.checkRM()) {
            logger.error("not find active resource manager!");
            System.exit(1);
        }
        Map<String, String> params = new HashMap<>();
        params.put("states", "RUNNING");
        String appsUrl = Constant.RESOURCE_MANAGER_URL + "/ws/v1/cluster/apps/";
        logger.info("appsUrl : " + appsUrl + "  params:" + params);

        String resp = null;
        try {
            resp = HttpUtils.get(appsUrl, null, params);
        } catch (Exception e) {
            logger.error("get running app error!", e);
            System.exit(1);
        }
        if (StringUtils.isBlank(resp))
            return;

        AppsBean apps = JSONObject.parseObject(resp, AppsBean.class);
        if (apps == null || apps.getApps() == null || apps.getApps().getApp() == null)
            return;


        Map<String, Job> longRunnings = new HashMap<>();
        Map<String, Job> bigjobs = new HashMap<>();
        for (App app : apps.getApps().getApp()) {
            String state = app.getState();
            String applicationType = app.getApplicationType();
            String trackingUI = app.getTrackingUI();
            String trackingUrl = app.getTrackingUrl();
            if ("RUNNING".equals(state) && "MAPREDUCE".equals(applicationType)
                    && "ApplicationMaster".equals(trackingUI)) {
                try {
                    resp = HttpUtils.get(trackingUrl + "ws/v1/mapreduce/jobs/", null, null);
                    if (StringUtils.isNotBlank(resp)) {
                        JobsBean jobs = JSONObject.parseObject(resp, JobsBean.class);
                        if (jobs != null && jobs.getJobs() != null || jobs.getJobs().getJob() != null) {
                            for (Job job : jobs.getJobs().getJob()) {
                                String id = job.getId();
                                Long elapsedTime = job.getElapsedTime();
                                long mapsTotal = job.getMapsTotal();
                                long reducesTotal = job.getReducesTotal();

                                if (elapsedTime > 30 * 60 * 1000)
                                    longRunnings.put(id, job);

                                if (mapsTotal + reducesTotal > 1000)
                                    bigjobs.put(id, job);
                            }
                        }
                    }
                } catch (Exception e) {
                    logger.error("get job info error!", e);
                }
            }
        }

        // merge to today result
        logger.info("merge today monitor jobs.");
        File dir = new File(Constant.TMP_DIR + File.separator + "running");
        if (!dir.isDirectory())
            dir.mkdirs();

        String hivePartition = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String tmpDataFile = Constant.TMP_DIR + File.separator + "running" + File.separator + hivePartition;
        BufferedReader jobsReader = null;
        try {
            jobsReader = new BufferedReader(new FileReader(tmpDataFile));
            String line;
            while ((line = jobsReader.readLine()) != null) {
                Job job = Job.parseJob(line);
                String[] cols = line.split(String.valueOf(Constant.HIVE_FIELD_SEPARATOR));
                if (cols != null && cols.length >= 13) {
                    String type = cols[12];
                    if (LONGRUNNING == getMonitorType(type) && !longRunnings.containsKey(job.getId()))
                        longRunnings.put(job.getId(), job);
                    else if (BIGJOB == getMonitorType(type) && !bigjobs.containsKey(job.getId()))
                        bigjobs.put(job.getId(), job);
                }
            }
        } catch (FileNotFoundException e) {
            logger.warn("tmp data file not file! tmpDataFile :" + tmpDataFile);
        } catch (IOException e) {
            logger.error("read tmp data file error!", e);
        } finally {
            if (jobsReader != null)
                try {
                    jobsReader.close();
                } catch (IOException e) {
                    logger.error("close tmp data file error!", e);
                }
        }

        // write result
        logger.info("save result to tmp directory.");
        BufferedWriter jobsWriter = null;
        try {
            jobsWriter = new BufferedWriter(new FileWriter(tmpDataFile));
            for (Job job : longRunnings.values()) {
                jobsWriter.append(job.toHiveString() + Constant.HIVE_FIELD_SEPARATOR + LONGRUNNING + '\n');
            }
            for (Job job : bigjobs.values()) {
                jobsWriter.append(job.toHiveString() + Constant.HIVE_FIELD_SEPARATOR + BIGJOB + '\n');
            }
        } catch (IOException e) {
            logger.error("write tmp data file error!", e);
        } finally {
            if (jobsWriter != null)
                try {
                    jobsWriter.close();
                } catch (IOException e) {
                    logger.error("close tmp data file error!", e);
                }
        }
    }
}
