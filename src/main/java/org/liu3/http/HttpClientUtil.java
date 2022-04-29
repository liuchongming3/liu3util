package org.liu3.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.liu3.function.FunctionEx;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Function;

/**
 * @Author: liutianshuo
 * @Date: 2021/2/23
 */
public class HttpClientUtil {

    private static CloseableHttpClient client;
    private static RequestConfig default_config;
    static {
        client = HttpClientBuilder.create().build();
        default_config = RequestConfig.custom()
                .setSocketTimeout(30*1000)
                .setConnectTimeout(10*1000)
                .build();

    }
    
    public static void main(String[] args) throws Exception {
        String s = HttpClientUtil.get("http://localhost:8080/cmdbDiscover/resources/tui/components/tuiValidate/tui_validator.js");
        System.out.println(s);
    }
    public static String get(String url) throws Exception {
        HttpGet get = new HttpGet(url);
        get.setConfig(default_config);
        String data = request(get, HttpClientUtil::getString);
        return data;
    }

    public static <T> T request(HttpRequestBase request, FunctionEx<HttpEntity, T> fun) throws Exception {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        CloseableHttpResponse resp = client.execute(request);
        byte[] bs = null;

        if(200 != resp.getStatusLine().getStatusCode()){
            //成功
            System.out.println("响应失败:"+resp.getStatusLine().getStatusCode());
        }
        return fun.apply(resp.getEntity());
    }



//    public static String requestData(HttpRequestBase request) throws IOException {
//        return getString(request(request));
//    }
//    public static byte[] requestByte(HttpRequestBase request) throws IOException {
//        return getBytes(request(request));
//    }

    private static String getString(HttpEntity entity) throws Exception {
        return EntityUtils.toString(entity);
    }

    public static byte[] getBytes(HttpEntity entity) throws Exception {
        byte[] bs = null;
        try(InputStream input = entity.getContent()){
            if(input == null){
                return bs;
            }
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buff = new byte[1024];
            int i = 0;
            while ((i = input.read(buff, 0, 1024)) > 0) {
                output.write(buff, 0, i);
            }
            bs = output.toByteArray();
        }
        return bs;
    }


}
