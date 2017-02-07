package com.paic.data.yarnjobs.util;

import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;

/**
 * Created by wankun603 on 2016-11-29.
 */
public class ConstantUtil {

    private ConstantUtil() {

    }

    public static String CHARSET = "utf-8";

    private static ResourceBundle resourceBundle;

    static {
        resourceBundle = ResourceBundle.getBundle("constant");
    }

    public static int getPropertiesInt(String keyName) {
        String s = resourceBundle.getString(keyName);
        return StringUtil.getSafeInt(s);
    }

    public static long getPropertiesLong(String keyName) {
        String s = resourceBundle.getString(keyName);
        return StringUtil.getSafeLong(s);
    }

    public static double getPropertiesDouble(String keyName) {
        String s = resourceBundle.getString(keyName);
        return StringUtil.getSafeDouble(s);
    }

    public static boolean getPropertiesBoolean(String keyName) {
        String s = resourceBundle.getString(keyName);
        return StringUtil.getSafeBoolean(s);
    }

    public static String getPropertiesStr(String keyName) {
        return resourceBundle.getString(keyName);
    }

    public static String getPropertiesChStr(String keyName) {
        try {
            return new String(resourceBundle.getString(keyName).getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        return "";
    }
}
