package com.paic.data.yarnjobs.bean;

import com.google.common.base.Joiner;
import com.paic.data.yarnjobs.Constant;

/**
 * Created by WANKUN603 on 2017-02-06.
 */
public class App {
    private String id;
    private String user;
    private String name;
    private String queue;
    private String state;
    private String finalStatus;
    private String progress;
    private String trackingUI;
    private String trackingUrl;
    private String diagnostics;
    private String clusterId;
    private String applicationType;
    private String applicationTags;
    private String startedTime;
    private String finishedTime;
    private String elapsedTime;
    private String amContainerLogs;
    private String amHostHttpAddress;
    private String allocatedMB;
    private String allocatedVCores;
    private String runningContainers;
    private String memorySeconds;
    private String vcoreSeconds;
    private String preemptedResourceMB;
    private String preemptedResourceVCores;
    private String numNonAMContainerPreempted;
    private String numAMContainerPreempted;
    private String logAggregationStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getFinalStatus() {
        return finalStatus;
    }

    public void setFinalStatus(String finalStatus) {
        this.finalStatus = finalStatus;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getTrackingUI() {
        return trackingUI;
    }

    public void setTrackingUI(String trackingUI) {
        this.trackingUI = trackingUI;
    }

    public String getTrackingUrl() {
        return trackingUrl;
    }

    public void setTrackingUrl(String trackingUrl) {
        this.trackingUrl = trackingUrl;
    }

    public String getDiagnostics() {
        return diagnostics;
    }

    public void setDiagnostics(String diagnostics) {
        this.diagnostics = diagnostics;
    }

    public String getClusterId() {
        return clusterId;
    }

    public void setClusterId(String clusterId) {
        this.clusterId = clusterId;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    public String getApplicationTags() {
        return applicationTags;
    }

    public void setApplicationTags(String applicationTags) {
        this.applicationTags = applicationTags;
    }

    public String getStartedTime() {
        return startedTime;
    }

    public void setStartedTime(String startedTime) {
        this.startedTime = startedTime;
    }

    public String getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(String finishedTime) {
        this.finishedTime = finishedTime;
    }

    public String getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(String elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public String getAmContainerLogs() {
        return amContainerLogs;
    }

    public void setAmContainerLogs(String amContainerLogs) {
        this.amContainerLogs = amContainerLogs;
    }

    public String getAmHostHttpAddress() {
        return amHostHttpAddress;
    }

    public void setAmHostHttpAddress(String amHostHttpAddress) {
        this.amHostHttpAddress = amHostHttpAddress;
    }

    public String getAllocatedMB() {
        return allocatedMB;
    }

    public void setAllocatedMB(String allocatedMB) {
        this.allocatedMB = allocatedMB;
    }

    public String getAllocatedVCores() {
        return allocatedVCores;
    }

    public void setAllocatedVCores(String allocatedVCores) {
        this.allocatedVCores = allocatedVCores;
    }

    public String getRunningContainers() {
        return runningContainers;
    }

    public void setRunningContainers(String runningContainers) {
        this.runningContainers = runningContainers;
    }

    public String getMemorySeconds() {
        return memorySeconds;
    }

    public void setMemorySeconds(String memorySeconds) {
        this.memorySeconds = memorySeconds;
    }

    public String getVcoreSeconds() {
        return vcoreSeconds;
    }

    public void setVcoreSeconds(String vcoreSeconds) {
        this.vcoreSeconds = vcoreSeconds;
    }

    public String getPreemptedResourceMB() {
        return preemptedResourceMB;
    }

    public void setPreemptedResourceMB(String preemptedResourceMB) {
        this.preemptedResourceMB = preemptedResourceMB;
    }

    public String getPreemptedResourceVCores() {
        return preemptedResourceVCores;
    }

    public void setPreemptedResourceVCores(String preemptedResourceVCores) {
        this.preemptedResourceVCores = preemptedResourceVCores;
    }

    public String getNumNonAMContainerPreempted() {
        return numNonAMContainerPreempted;
    }

    public void setNumNonAMContainerPreempted(String numNonAMContainerPreempted) {
        this.numNonAMContainerPreempted = numNonAMContainerPreempted;
    }

    public String getNumAMContainerPreempted() {
        return numAMContainerPreempted;
    }

    public void setNumAMContainerPreempted(String numAMContainerPreempted) {
        this.numAMContainerPreempted = numAMContainerPreempted;
    }

    public String getLogAggregationStatus() {
        return logAggregationStatus;
    }

    public void setLogAggregationStatus(String logAggregationStatus) {
        this.logAggregationStatus = logAggregationStatus;
    }

    @Override
    public String toString() {
        return Joiner.on(Constant.HIVE_FIELD_SEPARATOR)
                .useForNull("NULL")
                .join(
                        id,
                        user,
                        name == null ? "NULL" : name.replaceAll("\n", " ").replaceAll("\r", " "),
                        queue,
                        state,
                        finalStatus,
                        progress,
                        trackingUI,
                        trackingUrl,
                        diagnostics == null ? "NULL" :diagnostics.replaceAll("\n", " ").replaceAll("\r", " "),
                        clusterId,
                        applicationType,
                        applicationTags,
                        startedTime,
                        finishedTime,
                        elapsedTime,
                        amContainerLogs == null ? "NULL" :amContainerLogs.replaceAll("\n", " ").replaceAll("\r", " "),
                        amHostHttpAddress,
                        allocatedMB,
                        allocatedVCores,
                        runningContainers,
                        memorySeconds,
                        vcoreSeconds,
                        preemptedResourceMB,
                        preemptedResourceVCores,
                        numNonAMContainerPreempted,
                        numAMContainerPreempted,
                        logAggregationStatus == null ? "NULL" :logAggregationStatus.replaceAll("\n", " ").replaceAll("\r", " ")
                ).toString();
    }
}
