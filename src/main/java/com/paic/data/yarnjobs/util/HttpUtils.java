package com.paic.data.yarnjobs.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class HttpUtils {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    private static CloseableHttpClient httpClient;

    private static final int timeout = 60 * 1000;
    private static PoolingHttpClientConnectionManager cm;

    static {
        init();
    }

    private HttpUtils() {

    }

    public static void init() {
        cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(500);
        final RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout)
                .setConnectionRequestTimeout(timeout).build();
        httpClient = HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(requestConfig).build();
    }

    public static String post(String url, String json) throws Exception {
        HttpPost httppost = new HttpPost(url);

        httppost.setHeader("Content-Type", "application/json; charset=utf-8");
        HttpEntity requestEntity = new StringEntity(json, "utf-8");
        httppost.setEntity(requestEntity);
        HttpResponse response = httpClient.execute(httppost);

        return handleResult(response);

    }

    private static String handleResult(HttpResponse response) throws Exception {
        if (response != null) {
            HttpEntity entity = response.getEntity();
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 200)
                return EntityUtils.toString(entity);
            else
                logger.error("status code : {}, result :{}", statusCode, EntityUtils.toString(entity));
        }
        return null;
    }

    public static String put(String url, String json) throws Exception {
        HttpPut httppost = new HttpPut(url);

        httppost.setHeader("Content-Type", "application/json; charset=utf-8");
        HttpEntity requestEntity = new StringEntity(json, "utf-8");
        httppost.setEntity(requestEntity);
        HttpResponse response = httpClient.execute(httppost);
        return handleResult(response);
    }

    public static String post(String url, List<NameValuePair> nvps, Map<String, String> headparameters)
            throws Exception {
        HttpPost httppost = new HttpPost(url);

        if (null != nvps)
            httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF8"));
        httppost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        if (headparameters != null) {
            for (Entry<String, String> headparameter : headparameters.entrySet()) {
                httppost.setHeader(headparameter.getKey(), headparameter.getValue());
            }
        }

        HttpResponse response = httpClient.execute(httppost);
        return handleResult(response);
    }

    public static String get(String url, Map<String, String> headparameters) throws Exception {
        return get(url, headparameters, null);
    }

    public static String get(String url, Map<String, String> headparameters, Map<String, String> parameters) throws Exception {
        if (parameters != null && parameters.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> en : parameters.entrySet())
                sb.append(en.getKey() + "=" + en.getValue() + "&");

            sb.deleteCharAt(sb.length() - 1);
            if (url.indexOf("?") < 0)
                url = url + "?" + sb.toString();
            else
                url = url + "&" + sb.toString();
        }
        HttpGet httpget = new HttpGet(url);
        httpget.setHeader("Content-Type", "application/json; charset=UTF-8");
        if (headparameters != null) {
            for (Entry<String, String> headparameter : headparameters.entrySet()) {
                httpget.setHeader(headparameter.getKey(), headparameter.getValue());
            }
        }

        HttpResponse response = httpClient.execute(httpget);
        return handleResult(response);
    }
}