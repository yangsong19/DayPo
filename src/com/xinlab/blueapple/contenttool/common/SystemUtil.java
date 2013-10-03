package com.xinlab.blueapple.contenttool.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import com.xinlab.blueapple.contenttool.db.XinEmailUtil;

public class SystemUtil {
    
    private static final Logger  vulogger = Logger.getLogger(SystemUtil.class);

    private static final String SENDER_EMAIL_ADDR = "maintenance@xinlab.com";
    static final private String[] MSISDN = {}; 
    static final int MIN_PERIOD=5*60*1000;
    
    static final private String ALERT_URL = "http://72.52.126.154/sendalert";
    
    /*
Cingular     msisdn@mobile.mycingular.com
Sprint PCS     msisdn@messaging.sprintpcs.com
T-Mobile     msisdn@tmomail.net
Verizon     msisdn@vtext.com
     */
    static long lastSend=0;
    static public synchronized void sendAlert(String msg){
        if (System.currentTimeMillis()-lastSend>MIN_PERIOD && XinConfig.sendAlert)
        {
            String host;
            try {
                host = InetAddress.getLocalHost().getHostName();
            } catch (UnknownHostException e) {
                e.printStackTrace();
                host = "";
            }
            String _msg;
            try {
                _msg = URLEncoder.encode(msg, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                _msg = msg;
            }
            //String url = ALERT_URL+"?h="+host+"&m="+_msg;
            String url = ALERT_URL+"?h=&m="+_msg;//there is host in msg
            try {
                XinUtil.getUrlStringE(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            XinEmailUtil.sendEmail(SENDER_EMAIL_ADDR,SENDER_EMAIL_ADDR,null,"blueapple alert",msg);
            lastSend=System.currentTimeMillis();
        }
    }

}
