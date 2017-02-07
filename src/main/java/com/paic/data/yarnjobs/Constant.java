package com.paic.data.yarnjobs;

import com.paic.data.yarnjobs.util.ConstantUtil;
import com.paic.data.yarnjobs.util.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wankun603 on 2016-11-28.
 */
public class Constant {

    private static final Logger logger = LoggerFactory.getLogger(Constant.class);

    public static final String TMP_DIR = ConstantUtil.getPropertiesStr("tmpdir");
    public static String RESOURCE_MANAGER_URL = null;

    // prd : cnsz033397
    // uat : namenode
    public static final String JOB_HISTORY_URL = "http://" + ConstantUtil.getPropertiesStr("jobhistory") + ":19888";

    public static final char HIVE_FIELD_SEPARATOR = '\001';
    public static final char HIVE_ROW_SEPARATOR = '\002';
    public static final char HIVE_ITERMS_SEPARATOR = '\003';
    public static final char HIVE_KEYVALUE_SEPARATOR = '\004';


    public static boolean checkRM() {
        String rms = ConstantUtil.getPropertiesStr("resourcemanager");
        for (String rm : rms.split(",")) {
            try {
                String resp = HttpUtils.get("http://" + rm + ":8088", null);
                if (!resp.contains("This is standby RM")) {
                    RESOURCE_MANAGER_URL = "http://" + rm + ":8088";
                }
            } catch (Exception e) {
            }
        }
        if (RESOURCE_MANAGER_URL == null) {
            logger.error("RESOURCE_MANAGER check error!");
            return false;
        }
        return true;
    }
}
