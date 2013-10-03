package com.xinlab.blueapple.contenttool.util;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;


/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2011-3-28
 * Time: 14:11:49
 * To change this template use File | Settings | File Templates.
 */
public class ContentToolUtil {
    private static HttpClient httpclient;

    static {
        HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(params, 10000);
        httpclient = new DefaultHttpClient();
    }

    public static String getWatchUrl(String videoUrl, String title, String keywords,String pcc,String channel) {
        String ret = "";
        long start = System.currentTimeMillis();
        HttpResponse resp = null;
        try {
            System.out.println("start getWatchUrl...");
            System.out.println("videoUrl:" + videoUrl);
            System.out.println("title:" + title);
            System.out.println("keywords:" + keywords);
            System.out.println("pcc:"+pcc);
            System.out.println("channel:"+channel);
            if(StringUtils.trimToNull(videoUrl)==null)
                return "";
            String url = "http://qa.blueapple.mobi:8080/pit?url=" + URLEncoder.encode(videoUrl, "utf-8");
            if(StringUtils.trimToNull(title)!=null)
                url += "&t=" + URLEncoder.encode(title, "utf-8");
            ret = url;
            System.out.println("url:" + url);
            HttpGet httpget = new HttpGet(url);
            HttpContext localContext = new BasicHttpContext();
            resp = httpclient.execute(httpget, localContext);
            HttpHost target = (HttpHost) localContext.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
            HttpUriRequest req = (HttpUriRequest) localContext.getAttribute(ExecutionContext.HTTP_REQUEST);
            System.out.println("getWatchUrl('" + videoUrl + "','" + title + "') Cost " + (System.currentTimeMillis() - start));
            ret = target.toString() + req.getURI();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClientProtocolException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                resp.getEntity().getContent().close(); //!!!IMPORTANT
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return ret;
    }

    public static void main(String[] args) throws IOException {
//        String url = null;
//        url = URLDecoder.decode(url,"utf-8");
//        System.out.println(url);

        try {
            httpclient = new DefaultHttpClient();
            System.out.println("start getWatchUrl...");
            String url = "http://qa.blueapple.mobi:8080/pit?url=http%3A%2F%2F184.105.232.208%3A8080%2Fupload%2F8511_w_2.asf&t=test";
            System.out.println("url:" + url);
            HttpContext context = new BasicHttpContext();
            HttpGet httpget = new HttpGet(url);

            HttpResponse response = httpclient.execute(httpget, context);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
                throw new IOException(response.getStatusLine().toString());
            HttpUriRequest currentReq = (HttpUriRequest) context.getAttribute(
                    ExecutionContext.HTTP_REQUEST);
            HttpHost currentHost = (HttpHost) context.getAttribute(
                    ExecutionContext.HTTP_TARGET_HOST);
            String currentUrl = currentHost.toURI() + currentReq.getURI();
            System.out.println(currentUrl);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClientProtocolException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (Exception e) {
            e.printStackTrace();
        }


        String url1 = "http://m.vuclip.com/pit?url=" + URLEncoder.encode("http://www.youtube.com/watch?v=CD2LRROpph0", "utf-8")+"&t=aaaaaaaaaaaaaaaa";
        System.out.println(url1);
        HttpGet httpget = new HttpGet(url1);
        HttpContext localContext = new BasicHttpContext();

        HttpResponse res = httpclient.execute(httpget, localContext);
        System.out.println(res.getStatusLine());

        HttpHost target = (HttpHost) localContext.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
        HttpUriRequest req = (HttpUriRequest) localContext.getAttribute(ExecutionContext.HTTP_REQUEST);
        System.out.println(target);
        System.out.println(req.getURI());
        System.out.println(req.getMethod());

        System.out.println(target.toString()+req.getURI());


//        HttpUriRequest uri = new HttpUriRequest();
        HttpResponse resp = httpclient.execute(httpget);
        System.out.println(resp.getClass().getName());
        System.out.println(resp.getLocale().toString());
        System.out.println(resp.getStatusLine());
        System.out.println(resp.getStatusLine().getStatusCode());

        Header headers[] = resp.getAllHeaders();
        for(Header h:headers)
            System.out.println(h.getName()+"|"+h.getValue());
    }
}
