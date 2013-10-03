//
// Copyright XinLab Inc (blueapple.mobi) 2006-2008
// Proprietary and confidential
// All rights reserved
//
// $Id: DBResult2.java,v 1.2 2012/02/22 23:25:10 lakshmis Exp $
//
package com.xinlab.blueapple.contenttool.db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DBResult2 implements DBResult{

    private static final Logger  vulogger = Logger.getLogger(DBResult2.class);
    
    //
    // wrapping of JSONObject Object
    //

    JSONObject j;
    JSONArray a;
    JSONArray b;
    int currentRowIdx = -1;
    
    public DBResult2(JSONObject json) {

        try {
            if (json!=null && json.has("RESULT")){
                j = json.getJSONObject("RESULT");
                if (j!=null && j.has("ROWS"))
                    a = j.getJSONArray("ROWS");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        for (int i=0; i<a.length();i++){
//            JSONArray b = a.getJSONArray(i);
//            for (int k=0; k<b.length(); k++)
//                vulogger.debug(b.get(k)+",");
//            vulogger.info();
//        }

    }
    public boolean next() {
        currentRowIdx++;
        if (a!=null&&currentRowIdx<a.length()){
            try {
                b = a.getJSONArray(currentRowIdx);
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    //Lakshmi added this getRowCount function on 02/16/2012
    public int getRowCount() {
        
        return a.length();
    }
    
    public int getColumnCount(){
        if (b!=null)
            return b.length();
        return 0;
    }
    public String getString(int columnIndex){
        if (b!=null)
            try {
                return b.getString(columnIndex-1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        return null;
    }
    public int getInt(int columnIndex){
        if (b!=null)
            try {
                return b.getInt(columnIndex-1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        return 0;
    }
    public long getLong(int columnIndex){
        if (b!=null)
            try {
                return b.getLong(columnIndex-1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        return 0;
    }
    public double getDouble(int columnIndex){
        if (b!=null)
            try {
                return b.getDouble(columnIndex-1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        return 0;
    }
    static final private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//  static {
//      dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
//  }
  synchronized static final String dateFormat(java.util.Date date){
      return dateFormat.format(date);
  }
  public synchronized static final String dateFormat(long time){
      return dateFormat.format(new java.util.Date(time));
  }
  synchronized static final private long dateParse(String date)throws ParseException{
      return dateFormat.parse(date).getTime();
  }

    public long getTimestamp(int columnIndex){
        if (b!=null)
            try {
                String time = b.getString(columnIndex-1);
                if (time!=null)
                    return dateParse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        return 0;
    }
    public void close(){
        j=null;
        a=null;
        b=null;
    }
    protected void finalize(){// throws Throwable {
        close();
    }
	public byte[] getBytes(int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
