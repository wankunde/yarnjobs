package com.paic.data.yarnjobs;

import com.alibaba.fastjson.JSONObject;
import com.paic.data.yarnjobs.bean.App;
import com.paic.data.yarnjobs.bean.AppsBean;
import com.paic.data.yarnjobs.bean.Job;
import com.paic.data.yarnjobs.bean.JobsBean;
import com.paic.data.yarnjobs.util.HttpUtils;
import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * java -jar yarnjobs-1.0-assembly.jar
 */
public class YarnCollect {
    private static final Logger logger = LoggerFactory.getLogger(YarnCollect.class);

    private static String hivePartition;

    private void queryFinished(String confUrl, String jobId, Map<String, String> params, BufferedWriter confWriter) {
        StringBuilder buf = new StringBuilder();
        buf.append(jobId + Constant.HIVE_FIELD_SEPARATOR);
        try {
//            logger.info("try get job conf. URL : " + confUrl);
            String resp = HttpUtils.get(confUrl, null, params);

            JSONObject confBean = JSONObject.parseObject(resp);
            Iterator<Object> confIter = confBean.getJSONObject("conf").getJSONArray("property").iterator();

            while (confIter.hasNext()) {
                Map<String, String> propertyBean = (Map<String, String>) confIter.next();
                buf.append(propertyBean.get("name") + Constant.HIVE_KEYVALUE_SEPARATOR + propertyBean.get("value").replaceAll("\n", " ").replaceAll("\r", " ") + Constant.HIVE_ITERMS_SEPARATOR);
            }
            buf.deleteCharAt(buf.length() - 1);
        } catch (Exception e2) {
            logger.error("get job conf error!", e2);
        }
        buf.append('\n');
        try {
            confWriter.append(buf);
        } catch (IOException e) {
            logger.error("confWriter append error!", e);
        }
    }

    public void jobs(Map<String, String> params) throws Exception {
        String jobsUrl = Constant.JOB_HISTORY_URL + "/ws/v1/history/mapreduce/jobs";
        logger.info("jobsUrl : " + jobsUrl + "  params:" + params);
        String resp = HttpUtils.get(jobsUrl, null, params);
        if (StringUtils.isBlank(resp))
            return;

        JobsBean jobs = JSONObject.parseObject(resp, JobsBean.class);
        if (jobs == null || jobs.getJobs() == null || jobs.getJobs().getJob() == null)
            return;

        File dir = new File(Constant.TMP_DIR + File.separator + "jobs");
        if (!dir.isDirectory())
            dir.mkdirs();

        BufferedWriter jobsWriter = new BufferedWriter(new FileWriter(Constant.TMP_DIR + File.separator + "jobs" + File.separator + hivePartition));
        for (Job job : jobs.getJobs().getJob()) {
            jobsWriter.append(job.toHiveString());
            jobsWriter.append('\n');
        }
        jobsWriter.close();

        // get conf
        dir = new File(Constant.TMP_DIR + File.separator + "conf");
        if (!dir.isDirectory())
            dir.mkdirs();

        logger.info("try get job conf from job history.");
        BufferedWriter confWriter = new BufferedWriter(new FileWriter(Constant.TMP_DIR + File.separator + "conf" + File.separator + hivePartition, true));
        for (Job job : jobs.getJobs().getJob()) {
            String jobId = job.getId();
            String confUrl = String.format(Constant.JOB_HISTORY_URL + "/ws/v1/history/mapreduce/jobs/%s/conf", jobId);
            queryFinished(confUrl, jobId, params, confWriter);
        }
        confWriter.close();
    }


    public void rmApps(Map<String, String> params) {
        String appsUrl = Constant.RESOURCE_MANAGER_URL + "/ws/v1/cluster/apps/";
        logger.info("appsUrl : " + appsUrl + "  params:" + params);

        String resp = null;
        try {
            resp = HttpUtils.get(appsUrl, null, params);
        } catch (Exception e) {
            logger.error("get job info error!", e);
            return;
        }

        if (StringUtils.isBlank(resp))
            return;

        AppsBean apps = JSONObject.parseObject(resp, AppsBean.class);
        if (apps == null || apps.getApps() == null || apps.getApps().getApp() == null)
            return;

        File dir = new File(Constant.TMP_DIR + File.separator + "apps");
        if (!dir.isDirectory())
            dir.mkdirs();

        BufferedWriter appsWriter = null;
        try {
            appsWriter = new BufferedWriter(new FileWriter(Constant.TMP_DIR + File.separator + "apps" + File.separator + hivePartition));
            for (App app : apps.getApps().getApp()) {
                appsWriter.append(app.toHiveString());
                appsWriter.append('\n');
            }
        } catch (IOException e) {
            logger.error("appsWriter error!", e);
        } finally {
            if (appsWriter != null)
                try {
                    appsWriter.close();
                } catch (IOException e) {
                    logger.error("appsWriter error!", e);
                }
        }


        // get conf
        dir = new File(Constant.TMP_DIR + File.separator + "conf");
        if (!dir.isDirectory())
            dir.mkdirs();

        BufferedWriter confWriter = null;
        try {
            confWriter = new BufferedWriter(new FileWriter(Constant.TMP_DIR + File.separator + "conf" + File.separator + hivePartition, false));
            for (App app : apps.getApps().getApp()) {
                String trackingUI = app.getTrackingUI();
                String applicationId = app.getId();
                String jobId = applicationId.replaceAll("application", "job");
                String applicationType = app.getApplicationType();
                if ("MAPREDUCE".equals(applicationType) && "ApplicationMaster".equals(trackingUI)) {
                    String confUrl = String.format(Constant.RESOURCE_MANAGER_URL + "/proxy/%s/ws/v1/mapreduce/jobs/%s/conf", applicationId, jobId);
                    queryFinished(confUrl, jobId, params, confWriter);
                }
            }
        } catch (IOException e) {
            logger.error("confWriter error!", e);
        } finally {
            if(confWriter!=null)
                try {
                    confWriter.close();
                } catch (IOException e) {
                    logger.error("confWriter error!", e);
                }
        }



    }

    public static void main(String[] args) throws Exception {
        if (!Constant.checkRM()) {
            logger.error("not find active resource manager!");
            System.exit(1);
        }

        Map<String, String> params = parseParam(args);
        DateFormat df = new SimpleDateFormat("yyyyMMddHH");
        Calendar c = Calendar.getInstance();
        c.setTime(df.parse(args[0]));

        params.put("startedTimeBegin", String.valueOf(c.getTimeInMillis()));
        c.add(Calendar.HOUR, 1);
        params.put("startedTimeEnd", String.valueOf(c.getTimeInMillis()));

        hivePartition = args[0];
        YarnCollect collect = new YarnCollect();
        collect.rmApps(params);
        collect.jobs(params);
        /*
        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR, -2);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        hivePartition = new SimpleDateFormat("yyyyMMddHH").format(c);

        params.put("startedTimeBegin", String.valueOf(c.getTimeInMillis()));
        c.add(Calendar.HOUR, 1);
        params.put("startedTimeEnd", String.valueOf(c.getTimeInMillis()));

        String startDay = params.get("startDay");
        if (StringUtils.isBlank(startDay)) {
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, -1);
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            params.put("startedTimeBegin", String.valueOf(c.getTimeInMillis()));

            startDay = sdf.format(c.getTime());
            logger.info(" use default start day : " + startDay);
        } else {
            params.put("startedTimeBegin", String.valueOf(sdf.parse(startDay).getTime()));
            logger.info(" use input start day : " + startDay);
        }

        if (StringUtils.isBlank(params.get("states"))) {
            logger.info(" use default states : RUNNING");
//            params.put("states", "RUNNING");
        }*/
    }

    private static Map<String, String> parseParam(String[] args) {
        Options options = new Options();

        Option states = new Option("states", "states", true, "applications matching the given application states, specified as a comma-separated list.");
        states.setRequired(false);
        options.addOption(states);

        Option finalStatus = new Option("finalStatus", "finalStatus", true, "the final status of the application - reported by the application itself.");
        finalStatus.setRequired(false);
        options.addOption(finalStatus);

        Option user = new Option("user", "user", true, "user name");
        user.setRequired(false);
        options.addOption(user);

        Option queue = new Option("queue", "queue", true, "queue name.");
        queue.setRequired(false);
        options.addOption(queue);

        Option limit = new Option("limit", "limit", true, "total number of app objects to be returned");
        limit.setRequired(false);
        options.addOption(limit);

        Option startedTimeBegin = new Option("startedTimeBegin", "startedTimeBegin", true, "applications with start time beginning with this time, specified in ms since epoch");
        startedTimeBegin.setRequired(false);
        options.addOption(startedTimeBegin);

        Option startedTimeEnd = new Option("startedTimeEnd", "startedTimeEnd", true, "applications with start time ending with this time, specified in ms since epoch.");
        startedTimeEnd.setRequired(false);
        options.addOption(startedTimeEnd);

        Option finishedTimeBegin = new Option("finishedTimeBegin", "finishedTimeBegin", true, "applications with finish time beginning with this time, specified in ms since epoch.");
        finishedTimeBegin.setRequired(false);
        options.addOption(finishedTimeBegin);

        Option finishedTimeEnd = new Option("finishedTimeEnd", "finishedTimeEnd", true, "applications with finish time ending with this time, specified in ms since epoch.");
        finishedTimeEnd.setRequired(false);
        options.addOption(finishedTimeEnd);

        Option applicationTypes = new Option("applicationTypes", "applicationTypes", true, "applications matching the given application types, specified as a comma-separated list.");
        applicationTypes.setRequired(false);
        options.addOption(applicationTypes);

        Option applicationTags = new Option("applicationTags", "applicationTags", true, "applicationTags");
        applicationTags.setRequired(false);
        options.addOption(applicationTags);

        Option startDay = new Option("startDay", "startDay", true, "startDay");
        startDay.setRequired(false);
        options.addOption(startDay);

        CommandLineParser parser = new PosixParser();
        HelpFormatter formatter = new HelpFormatter();

        try {
            CommandLine cmd = parser.parse(options, args);
            return parseCmd(cmd,
                    "startDay",
                    "states",
                    "finalStatus",
                    "user",
                    "queue",
                    "limit",
                    "startedTimeBegin",
                    "startedTimeEnd",
                    "finishedTimeBegin",
                    "finishedTimeEnd",
                    "applicationTypes",
                    "applicationTags");
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("command:", options);

            System.exit(1);
            return null;
        }
    }

    private static Map<String, String> parseCmd(CommandLine cmd, String... keys) {
        Map<String, String> params = new HashMap<>();
        for (String key : keys) {
            String value = cmd.getOptionValue(key);
            if (StringUtils.isNotBlank(value))
                params.put(key, value);
        }
        return params;
    }
}
