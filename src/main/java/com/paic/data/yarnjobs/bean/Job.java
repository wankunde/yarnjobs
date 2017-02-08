package com.paic.data.yarnjobs.bean;

import com.google.common.base.Joiner;
import com.paic.data.yarnjobs.Constant;

/**
 * Created by WANKUN603 on 2017-02-07.
 */
public class Job {

    private long submitTime;
    private long startTime;
    private long finishTime;
    private String id;
    private String name;
    private String queue;
    private String user;
    private String state;
    private long mapsTotal;
    private long mapsCompleted;
    private long reducesTotal;
    private long reducesCompleted;

    // AM properties
    private long elapsedTime;
    private double mapProgress;
    private double reduceProgress;
    private long mapsPending;
    private long mapsRunning;
    private long reducesPending;
    private long reducesRunning;
    private boolean uberized;
    private String diagnostics;
    private long newReduceAttempts;
    private long runningReduceAttempts;
    private long failedReduceAttempts;
    private long killedReduceAttempts;
    private long successfulReduceAttempts;
    private long newMapAttempts;
    private long runningMapAttempts;
    private long failedMapAttempts;
    private long killedMapAttempts;
    private long successfulMapAttempts;

    public String toHiveString() {
        return Joiner.on(Constant.HIVE_FIELD_SEPARATOR)
                .useForNull("NULL")
                .join(
                        submitTime,
                        startTime,
                        finishTime,
                        id,
                        name == null ? "NULL" : name.replaceAll("\n", " ").replaceAll("\r", " "),
                        queue,
                        user,
                        state,
                        mapsTotal,
                        mapsCompleted,
                        reducesTotal,
                        reducesCompleted
                ).toString();
    }

    public static Job parseJob(String line) {
        String[] cols = line.split(String.valueOf(Constant.HIVE_FIELD_SEPARATOR));
        Job job = new Job();
        if (cols != null) {
            if (cols.length >= 1) job.setSubmitTime(Long.parseLong(cols[0]));
            if (cols.length >= 2) job.setStartTime(Long.parseLong(cols[1]));
            if (cols.length >= 3) job.setFinishTime(Long.parseLong(cols[2]));
            if (cols.length >= 4) job.setId(cols[3]);
            if (cols.length >= 5) job.setName(cols[4]);
            if (cols.length >= 6) job.setQueue(cols[5]);
            if (cols.length >= 7) job.setUser(cols[6]);
            if (cols.length >= 8) job.setState(cols[7]);
            if (cols.length >= 9) job.setMapsTotal(Long.parseLong(cols[8]));
            if (cols.length >= 10) job.setMapsCompleted(Long.parseLong(cols[9]));
            if (cols.length >= 11) job.setReducesTotal(Long.parseLong(cols[10]));
            if (cols.length >= 12) job.setReducesCompleted(Long.parseLong(cols[11]));
        }
        return job;
    }

    public long getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(long submitTime) {
        this.submitTime = submitTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getMapsTotal() {
        return mapsTotal;
    }

    public void setMapsTotal(long mapsTotal) {
        this.mapsTotal = mapsTotal;
    }

    public long getMapsCompleted() {
        return mapsCompleted;
    }

    public void setMapsCompleted(long mapsCompleted) {
        this.mapsCompleted = mapsCompleted;
    }

    public long getReducesTotal() {
        return reducesTotal;
    }

    public void setReducesTotal(long reducesTotal) {
        this.reducesTotal = reducesTotal;
    }

    public long getReducesCompleted() {
        return reducesCompleted;
    }

    public void setReducesCompleted(long reducesCompleted) {
        this.reducesCompleted = reducesCompleted;
    }

    // ---------------------------------

    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public double getMapProgress() {
        return mapProgress;
    }

    public void setMapProgress(double mapProgress) {
        this.mapProgress = mapProgress;
    }

    public double getReduceProgress() {
        return reduceProgress;
    }

    public void setReduceProgress(double reduceProgress) {
        this.reduceProgress = reduceProgress;
    }

    public long getMapsPending() {
        return mapsPending;
    }

    public void setMapsPending(long mapsPending) {
        this.mapsPending = mapsPending;
    }

    public long getMapsRunning() {
        return mapsRunning;
    }

    public void setMapsRunning(long mapsRunning) {
        this.mapsRunning = mapsRunning;
    }

    public long getReducesPending() {
        return reducesPending;
    }

    public void setReducesPending(long reducesPending) {
        this.reducesPending = reducesPending;
    }

    public long getReducesRunning() {
        return reducesRunning;
    }

    public void setReducesRunning(long reducesRunning) {
        this.reducesRunning = reducesRunning;
    }

    public boolean isUberized() {
        return uberized;
    }

    public void setUberized(boolean uberized) {
        this.uberized = uberized;
    }

    public String getDiagnostics() {
        return diagnostics;
    }

    public void setDiagnostics(String diagnostics) {
        this.diagnostics = diagnostics;
    }

    public long getNewReduceAttempts() {
        return newReduceAttempts;
    }

    public void setNewReduceAttempts(long newReduceAttempts) {
        this.newReduceAttempts = newReduceAttempts;
    }

    public long getRunningReduceAttempts() {
        return runningReduceAttempts;
    }

    public void setRunningReduceAttempts(long runningReduceAttempts) {
        this.runningReduceAttempts = runningReduceAttempts;
    }

    public long getFailedReduceAttempts() {
        return failedReduceAttempts;
    }

    public void setFailedReduceAttempts(long failedReduceAttempts) {
        this.failedReduceAttempts = failedReduceAttempts;
    }

    public long getKilledReduceAttempts() {
        return killedReduceAttempts;
    }

    public void setKilledReduceAttempts(long killedReduceAttempts) {
        this.killedReduceAttempts = killedReduceAttempts;
    }

    public long getSuccessfulReduceAttempts() {
        return successfulReduceAttempts;
    }

    public void setSuccessfulReduceAttempts(long successfulReduceAttempts) {
        this.successfulReduceAttempts = successfulReduceAttempts;
    }

    public long getNewMapAttempts() {
        return newMapAttempts;
    }

    public void setNewMapAttempts(long newMapAttempts) {
        this.newMapAttempts = newMapAttempts;
    }

    public long getRunningMapAttempts() {
        return runningMapAttempts;
    }

    public void setRunningMapAttempts(long runningMapAttempts) {
        this.runningMapAttempts = runningMapAttempts;
    }

    public long getFailedMapAttempts() {
        return failedMapAttempts;
    }

    public void setFailedMapAttempts(long failedMapAttempts) {
        this.failedMapAttempts = failedMapAttempts;
    }

    public long getKilledMapAttempts() {
        return killedMapAttempts;
    }

    public void setKilledMapAttempts(long killedMapAttempts) {
        this.killedMapAttempts = killedMapAttempts;
    }

    public long getSuccessfulMapAttempts() {
        return successfulMapAttempts;
    }

    public void setSuccessfulMapAttempts(long successfulMapAttempts) {
        this.successfulMapAttempts = successfulMapAttempts;
    }
}
