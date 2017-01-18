package com.example.andre_000.mrservice;

/**
 * Created by andre_000 on 11/22/2015.
 */
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class WebRequest {

    public static String host = "192.168.1.105/trabajodegrado/public";
    //public static String host = "finaparty.com/fertorx";
    private String link;

    public WebRequest(String route) {
        this.link = "http://" + host + "/" + route;
    }

    public void setParameters(Map<String, String> params) {
        this.link += "?";

        for(String s : params.keySet()){
            try {
                link += "&" + s + "=" + URLEncoder.encode(params.get(s), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    public String getResponse() throws IOException, URISyntaxException {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(link);

        HttpResponse resp = httpclient.execute(httppost);
        HttpEntity ent = resp.getEntity();

        String response = EntityUtils.toString(ent);

        return response;
    }

}
