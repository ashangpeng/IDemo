package me.duzhi.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import sun.net.www.http.HttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class JsonTest {
    public static void main1(String[] args) {

    }
    public static void main(String[] args) throws IOException {
        InputStream stream = JsonTest.class.getResourceAsStream("/test1.txt");
        String buffer = inputStream2String(stream);
        JSONObject object = (JSONObject) JSON.parse(buffer);
        JSONArray results = object.getJSONArray("result");
        for (Object result : results) {
        //    HttpClient httpClient =  HttpClient.New(new URL("http://112.25.233.117:58080/OnlineServer/FrameAction/FrameAction_Server.action"));
      /*      Map map = new HashMap();
            map.put("class","PlanTreeAction");
            map.put("method","planArrange");*/
            String id =((JSONObject)result).getString("id");
            System.out.println(HttpUtil.doPost("http://112.25.233.117:58080/OnlineServer/FrameAction/FrameAction_Server.action?class=PlanTreeAction&method=planArrange&params=%5B%7B%22taskId%22%3A%22"+id+"%22%2C%22fieldId%22%3A%22300120%22%2C%22fieldValue%22%3A%2238190695%22%7D%5D",new HashMap<String, String>(),null));
            /*map.put("param","[{\"taskId\":\""+id+"\",\"fieldId\":\"300120\",\"fieldValue\":\"37210073\"}]");*/
        //    String httpresult = HttpUtil.doPost("http://112.25.233.117:58080/OnlineServer/FrameAction/FrameAction_Server.action",map,null);
          //  System.out.println(httpresult);
        }
    }

    public static String inputStream2String(InputStream in) throws IOException {
        StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1; ) {
            out.append(new String(b, 0, n));
        }
        return out.toString();
    }



}
