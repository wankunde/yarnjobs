package com.paic.data.yarnjobs.bean;

/**
 * Created by WANKUN603 on 2017-02-08.
 */
public enum MonitorType {

    LONGRUNNING, BIGJOB;

    public static MonitorType getMonitorType(String type) {
        if ("LONGRUNNING".equals(type))
            return LONGRUNNING;
        else if ("BIGJOB".equals(type))
            return BIGJOB;
        else
            return null;
    }
}
