package com.xiyan.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

/**
 * @Author 刘明
 * @Date 2020/7/22 22:15
 * Describe: 微博发送 Http请求
 */
public class WeiBoHttpUtil {

    /**
     * 发送POST请求
     *
     * @param url    请求的接口路径
     * @param params 参数
     * @return
     * @throws IOException
     */
    public static String post(String url, Map<String, String> params) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        StringBuilder stringBuilder = new StringBuilder(url);
        //第一个参数
        stringBuilder.append("?client_id=");
        stringBuilder.append(params.get("client_id"));
        //第二个参数
        stringBuilder.append("&client_secret=");
        stringBuilder.append(params.get("client_secret"));
        //第三个参数
        stringBuilder.append("&grant_type=");
        stringBuilder.append(params.get("grant_type"));
        //第四个参数
        stringBuilder.append("&code=");
        stringBuilder.append(params.get("code"));
        //第五个参数
        stringBuilder.append("&redirect_uri=");
        stringBuilder.append(params.get("redirect_uri"));
        HttpPost httpPost = new HttpPost(stringBuilder.toString());
        //发送请求返回响应的信息
        CloseableHttpResponse response = client.execute(httpPost);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            String result = EntityUtils.toString(entity, "UTF-8");
            return result;
        }
        return null;
    }

    /**
     * GET请求
     *
     * @param url          请求的接口路径
     * @param access_token 授权码
     * @param uid          用户ID
     * @return
     * @throws IOException
     */
    public static String get(String url, Object access_token, Object uid) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        StringBuilder stringBuilder = new StringBuilder(url);
        //第一个参数
        stringBuilder.append("?access_token=");
        stringBuilder.append(access_token);
        //第二个参数
        stringBuilder.append("&uid=");
        stringBuilder.append(uid);
        HttpGet httpGet = new HttpGet(stringBuilder.toString());
        //发送请求返回响应的信息
        CloseableHttpResponse response = client.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            String result = EntityUtils.toString(entity, "UTF-8");
            return result;
        }
        return null;
    }
}