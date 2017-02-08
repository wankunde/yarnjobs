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
    private Double progress;
    private String trackingUI;
    private String trackingUrl;
    private String diagnostics;
    private String clusterId;
    private String applicationType;
    private String applicationTags;
    private Long startedTime;
    private Long finishedTime;
    private Long elapsedTime;
    private String amContainerLogs;
    private String amHostHttpAddress;
    private Long allocatedMB;
    private Long allocatedVCores;
    private Long runningContainers;
    private Long memorySeconds;
    private Long vcoreSeconds;
    private Long preemptedResourceMB;
    private Long preemptedResourceVCores;
    private Long numNonAMContainerPreempted;
    private Long numAMContainerPreempted;
    private String logAggregationStatus;

    public String toHiveString() {
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

    public Double getProgress() {
        return progress;
    }

    public void setProgress(Double progress) {
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

    public Long getStartedTime() {
        return startedTime;
    }

    public void setStartedTime(Long startedTime) {
        this.startedTime = startedTime;
    }

    public Long getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(Long finishedTime) {
        this.finishedTime = finishedTime;
    }

    public Long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(Long elapsedTime) {
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

    public Long getAllocatedMB() {
        return allocatedMB;
    }

    public void setAllocatedMB(Long allocatedMB) {
        this.allocatedMB = allocatedMB;
    }

    public Long getAllocatedVCores() {
        return allocatedVCores;
    }

    public void setAllocatedVCores(Long allocatedVCores) {
        this.allocatedVCores = allocatedVCores;
    }

    public Long getRunningContainers() {
        return runningContainers;
    }

    public void setRunningContainers(Long runningContainers) {
        this.runningContainers = runningContainers;
    }

    public Long getMemorySeconds() {
        return memorySeconds;
    }

    public void setMemorySeconds(Long memorySeconds) {
        this.memorySeconds = memorySeconds;
    }

    public Long getVcoreSeconds() {
        return vcoreSeconds;
    }

    public void setVcoreSeconds(Long vcoreSeconds) {
        this.vcoreSeconds = vcoreSeconds;
    }

    public Long getPreemptedResourceMB() {
        return preemptedResourceMB;
    }

    public void setPreemptedResourceMB(Long preemptedResourceMB) {
        this.preemptedResourceMB = preemptedResourceMB;
    }

    public Long getPreemptedResourceVCores() {
        return preemptedResourceVCores;
    }

    public void setPreemptedResourceVCores(Long preemptedResourceVCores) {
        this.preemptedResourceVCores = preemptedResourceVCores;
    }

    public Long getNumNonAMContainerPreempted() {
        return numNonAMContainerPreempted;
    }

    public void setNumNonAMContainerPreempted(Long numNonAMContainerPreempted) {
        this.numNonAMContainerPreempted = numNonAMContainerPreempted;
    }

    public Long getNumAMContainerPreempted() {
        return numAMContainerPreempted;
    }

    public void setNumAMContainerPreempted(Long numAMContainerPreempted) {
        this.numAMContainerPreempted = numAMContainerPreempted;
    }

    public String getLogAggregationStatus() {
        return logAggregationStatus;
    }

    public void setLogAggregationStatus(String logAggregationStatus) {
        this.logAggregationStatus = logAggregationStatus;
    }
}
