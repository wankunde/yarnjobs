package com.paic.data.yarnjobs.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public final class StringUtil {

    private StringUtil() {
    }

    private static Logger logger = LoggerFactory.getLogger(StringUtil.class);

    public static final String EMPTY = "";

    public static String formatDate(Date date, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    public static boolean equals(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equals(str2);
    }

    public static String urlencodeString(String string) throws Exception {
        return URLEncoder.encode(string, "utf-8").replace("+", "%20");
    }

    public static String urldecodeString(String string) throws Exception {
        return URLDecoder.decode(string, "utf-8").replace(" ", "+");
    }

    /**
     * 判断当前字符是否为纯数字
     *
     * @param str
     * @return
     */
    public static boolean isDigit(String str) {
        String pattern = "-{0,1}\\d+";
        return str.matches(pattern);
    }

    public static boolean isFloat(String str) {
        String pattern = "-{0,1}\\d+(\\.\\d+)?";
        return str.matches(pattern);
    }

    public static int getSafeInt(String value) {
        if (isBlank(value)) {
            return 0;
        }
        if (!isDigit(value)) {
            return 0;
        }
        return Integer.valueOf(value);
    }

    public static long getSafeLong(String value) {
        if (isBlank(value)) {
            return 0;
        }
        if (!isDigit(value)) {
            return 0;
        }
        return Long.valueOf(value);
    }

    public static boolean isBool(String value) {
        if ("true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value)) {
            return Boolean.valueOf(value);
        }
        return false;
    }

    public static boolean getSafeBoolean(String value) {
        if (isBlank(value)) {
            return false;
        }
        if (!isBool(value)) {
            return false;
        }
        return Boolean.valueOf(value);
    }

    public static double getSafeDouble(String value) {
        if (isBlank(value)) {
            return 0;
        }
        if (!isFloat(value)) {
            return 0;
        }
        return Double.valueOf(value);
    }

    public static Integer getSafeInterger(String value) {
        if (isBlank(value)) {
            return 0;
        }
        if (!isDigit(value)) {
            return 0;
        }
        return Integer.valueOf(value);
    }

    public static short getSafeShort(String value) {
        if (isBlank(value)) {
            return 0;
        }
        if (!isDigit(value)) {
            return 0;
        }
        return Short.valueOf(value);
    }

    public static float getSafeFloat(String value) {
        if (isBlank(value)) {
            return 0.0f;
        }
        if (!isFloat(value)) {
            return 0;
        }
        return Float.valueOf(value);
    }

    public static String getSafeString(String value) {
        if (isBlank(value)) {
            return "";
        }
        return value;
    }

    public static Date getSafeDate(String value) {
        if (!isBlank(value)) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sdf.parse(value);
                return date;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return null;
    }

    public static Date getSafeDateTime(String value) {
        if (!isBlank(value)) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = sdf.parse(value);
                return date;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return null;
    }

    public static String getSafeString(String value, String defaultValue) {
        try {
            if (isBlank(value)) {
                return defaultValue;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return defaultValue;
        }
        return value;
    }

    /**
     * 动态替换字符串 以指定字符串进行替换
     *
     * @param str
     * @param obj (可变参数)
     * @return
     */
    public static String formatString(String str, Object... obj) {
        return MessageFormat.format(str, obj);
    }

    /**
     * 去除后缀
     *
     * @param request_url
     * @return
     */
    public static String EraseSuffix(String request_url) {
        if (isNotBlank(request_url) && request_url.lastIndexOf(".") >= 0) {

            return request_url.substring(0, request_url.lastIndexOf("."));
        }
        return request_url;
    }

}
