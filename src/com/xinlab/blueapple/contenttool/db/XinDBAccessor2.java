//
// Copyright XinLab Inc (blueapple.mobi) 2006-2008
// Proprietary and confidential
// All rights reserved
//
// $Id: XinDBAccessor2.java,v 1.1.1.1 2010/07/21 23:37:10 tli Exp $
//
package com.xinlab.blueapple.contenttool.db;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.xinlab.blueapple.contenttool.common.XinUtil;

public abstract class XinDBAccessor2 extends XinDBAccessor{

    private static final Logger  vulogger = Logger.getLogger(XinDBAccessor2.class);
    
    static private boolean ALLOWPRINT = false;
    
    protected String dburl = null;
    
    private static int MIN_REPORT_DUR = 300;

    public void close(boolean commit){
    }

    public DBResult executeQuery(String sql) throws UnsupportedEncodingException, JSONException{
        return executeQuery(sql, true);
    }

    public DBResult executeQuery(String sql, boolean printSQL) throws UnsupportedEncodingException, JSONException{
        if (printSQL && ALLOWPRINT) vulogger.info(sql);

        long starttime = System.currentTimeMillis();
        try {
            String request = sql;
            request = "{\"SQL\":\""+request+"\"}";
            request = URLEncoder.encode(request, "UTF-8");
            request = request.replace("+", "%20").replace("%3A",":");
            request = dburl+request;
            
vulogger.info(request);
            String result = XinUtil.getUrlString(request);
            JSONObject j = new JSONObject(result);

            return new DBResult2(j);
        } finally{
            long querytime = System.currentTimeMillis()-starttime;
            if (querytime>MIN_REPORT_DUR)
                vulogger.info("dbquery "+ sql+" finished in:"+querytime+"ms");
        }
    }
    //{"SERVER" : "127.0.0.1" , "RESULT" : {"SUCCESS" : true}}
    public int executeUpdate(String sql) throws UnsupportedEncodingException, JSONException{
        vulogger.info(sql);
        long starttime = System.currentTimeMillis();

        try {
            String request = sql;
            request = "{\"SQL\":\""+request+"\"}";
            request = URLEncoder.encode(request, "UTF-8");
            request = request.replace("+", "%20").replace("%3A",":");
            request = dburl+request;
vulogger.info(request);            
            String result = XinUtil.getUrlString(request);
            JSONObject j = new JSONObject(result);
            
            if (j.has("RESULT")){
                j = j.getJSONObject("RESULT");
                if (j!=null && j.has("SUCCESS")){
                    boolean b = j.getBoolean("SUCCESS");
                    if (b)
                        return 1;
                }
            }
        }finally{
            long querytime = System.currentTimeMillis()-starttime;
            if (querytime>MIN_REPORT_DUR)
                vulogger.info("dbquery "+ sql+" finished in:"+querytime+"ms");
        }
        return 0;
    }
    //{"SERVER" : "127.0.0.1" , "RESULT" : {"SUCCESS" : true}}
    //{"SERVER" : "127.0.0.1" , "RESULT" : {"TYPES" : ["MYSQL_TYPE_VAR_STRING"] , "HEADER" : ["conv(last_insert_id(),10,10)"] , "ROWS" : [["1096341"]]}}
    //may look like:
    //{"RESULT":[{"SUCCESS" : true},{"TYPES" : ["MYSQL_TYPE_VAR_STRING"] , "HEADER" : ["conv(last_insert_id(),10,10)"] , "ROWS" : [["1096341"]]}]}
    //select conv(last_insert_id(),10,10)
    public int executeUpdateAutoIncrement(String sql) throws UnsupportedEncodingException, JSONException {
        
        DBResult rs=null;
        long starttime = System.currentTimeMillis();
        try {
            //vulogger.info(sql);
            while(sql.endsWith(";"))
                sql = sql.substring(0,sql.length()-1);
            
            
            String request = sql+";SELECT CONV(LAST_INSERT_ID(),10,10)";
            request = "{\"SQL\":\""+request+"\"}";
            request = URLEncoder.encode(request, "UTF-8");
            request = request.replace("+", "%20").replace("%3A",":");
            request = dburl+request;
vulogger.info(request);
            String result = XinUtil.getUrlString(request);
            JSONObject j = new JSONObject(result);
            
            if (j.has("RESULT")){
                JSONArray a = j.getJSONArray("RESULT");
                if (a!=null && a.length()>=2){
                    j = a.getJSONObject(0);
                    if (j!=null && j.has("SUCCESS")){
                        boolean b = j.getBoolean("SUCCESS");
                        if (b){
                            j= a.getJSONObject(1);
                            if (j!=null && j.has("ROWS")){
                                a = j.getJSONArray("ROWS");
                                if (a!=null && a.length()>0){
                                    a = a.getJSONArray(0);
                                    if (a!=null && a.length()>0){
                                        int index = a.getInt(0);
                                        return index;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }finally{
            if (rs!=null)
            rs.close();
            long querytime = System.currentTimeMillis()-starttime;
            if (querytime>MIN_REPORT_DUR)
                vulogger.info("dbquery "+ sql+" finished in:"+querytime+"ms");
        }
        return 0;
    }
    public boolean execute(String sql) throws UnsupportedEncodingException, JSONException{
        return executeUpdate(sql)==1;
    }
    
    
}



