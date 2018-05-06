package me.duzhi.demo;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HttpUtil {

    //public static LogUtil log = new LogUtil(HttpclientUtil.class);

    private static PoolingHttpClientConnectionManager connMgr;
    private static RequestConfig requestConfig;
    private static final int MAX_TIMEOUT = 7000;

    static {
        // 设置连接池
        connMgr = new PoolingHttpClientConnectionManager();
        // 设置连接池大小
        connMgr.setMaxTotal(100);
        connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());

        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时
        configBuilder.setConnectTimeout(MAX_TIMEOUT);
        // 设置读取超时
        configBuilder.setSocketTimeout(MAX_TIMEOUT);
        // 设置从连接池获取连接实例的超时
        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
        // 在提交请求之前 测试连接是否可用
        configBuilder.setStaleConnectionCheckEnabled(true);
        requestConfig = configBuilder.build();
    }


    /**
     * 发送GET请求（HTTP），K-V形式
     *
     * @param url
     * @param params
     * @return
     * @author Charlie.chen；
     */
    public static String doGet(String url, Map<String, Object> params) {

        String apiUrl = url;
        StringBuffer param = new StringBuffer();
        int i = 0;
        for (String key : params.keySet()) {
            if (i == 0)
                param.append("?");
            else
                param.append("&");
            param.append(key).append("=").append(params.get(key));
            i++;
        }
        apiUrl += param;

        // 创建默认的HttpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {

            // 定义一个get请求方法
            HttpGet httpget = new HttpGet(apiUrl);

            // 执行get请求，返回response服务器响应对象, 其中包含了状态信息和服务器返回的数据
            CloseableHttpResponse httpResponse = httpclient.execute(httpget);

            // 使用响应对象, 获得状态码, 处理内容
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                // 使用响应对象获取响应实体
                HttpEntity entity = httpResponse.getEntity();
                // 将响应实体转为字符串
                String response = EntityUtils.toString(entity, "utf-8");
                return response;

            } else {
                // log.error("访问失败"+statusCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭连接, 和释放资源
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 发送POST请求（HTTP），K-V形式
     *
     * @param url
     * @param params
     * @return
     * @author Charlie.chen
     */
    public static String doPost(String url, Map<String, String> params, String entry) {


        // 创建默认的HttpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();

        try {

            // 定义一个get请求方法
            HttpPost httppost = new HttpPost(url);
            //设置头部信息
            httppost.setHeader("Referer", "http://oms.hqygou.com/order/temp/new");
            httppost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httppost.setHeader("X-Requested-With", "XMLHttpRequest");
            httppost.setHeader("Cookie", "username=pengqj; pwd=b61a826990d59ffc48f940a449e8c58e; td_cookie=2749597055; JSESSIONID=6A4C159E292B55E35F0A07BFE96FEBC9");
            httppost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
            httppost.setHeader("Referer", "http://112.25.233.117:58080/OnlineServer/FrameAction_edoPackPage4Iframe.action?&&i=2353006907338656278321365&&p=D8Bd*zqgRYLbKcF7nm8U6j2Kys26OsUg*QU*CZ3DS0jalWJHknnmkQ");
            // List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            // parameters.add(new BasicNameValuePair("username", userName));
            // parameters.add(new BasicNameValuePair("password", password));
            httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            // 定义post请求的参数
            // 建立一个NameValuePair数组，用于存储欲传送的参数
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> elem = (Map.Entry<String, String>) iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
            }
            if (list.size() > 0) {
                httppost.setEntity(new UrlEncodedFormEntity(list, "utf-8"));
            }
            if (entry != null) {
                httppost.setEntity(new StringEntity(entry));
            }


            // httppost.setHeader("Content-type","application/json,charset=utf-8");
            // httppost.setHeader("Accept", "application/json");


            // 执行post请求，返回response服务器响应对象, 其中包含了状态信息和服务器返回的数据
            CloseableHttpResponse httpResponse = httpclient.execute(httppost);

            // 使用响应对象, 获得状态码, 处理内容
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                // 使用响应对象获取响应实体
                HttpEntity entity = httpResponse.getEntity();
                // 将响应实体转为字符串
                String response = EntityUtils.toString(entity, "utf-8");
                return response;

            } else {
                // log.error("访问失败"+statusCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭连接, 和释放资源
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 发送 SSL POST请（HTTPS），K-V形式
     *
     * @param url
     * @param params
     * @author Charlie.chen
     */
    public static String doPostSSL(String url, Map<String, Object> params) {
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConn()).setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        String httpStr = null;

        try {
            httpPost.setConfig(requestConfig);
            List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry
                        .getValue().toString());
                pairList.add(pair);
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("utf-8")));
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
            }
            httpStr = EntityUtils.toString(entity, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return httpStr;
    }


    /**
     * 创建SSL安全连接
     *
     * @param url
     * @param params
     * @return
     * @author Charlie.chen
     */
    private static SSLConnectionSocketFactory createSSLConn() {
        SSLConnectionSocketFactory sslsf = null;
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();

            sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }

                public void verify(String host, SSLSocket ssl) throws IOException {
                }

                public void verify(String host, X509Certificate cert) throws SSLException {
                }

                public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
                }
            });
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return sslsf;
    }


}