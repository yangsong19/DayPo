//
// Copyright XinLab Inc (blueapple.mobi) 2006-2008
// Proprietary and confidential
// All rights reserved
//
// $Id: XinDBUtilBase.java,v 1.6 2011/12/20 10:08:05 yumiao Exp $
//
package com.xinlab.blueapple.contenttool.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;

public abstract class XinDBUtilBase {
    static final private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//  static {
//      dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
//  }
  public synchronized static final String dateFormat(java.util.Date date){
      return dateFormat.format(date);
  }
  public synchronized static final String dateFormat(long time){
      return dateFormat.format(new java.util.Date(time));
  }
  public synchronized static final long dateParse(String date)throws ParseException{
      return dateFormat.parse(date).getTime();
  }

  static final private SimpleDateFormat monthDateFormat = new SimpleDateFormat("yyyy-MM-dd");
  public synchronized static final String monthDateFormat(long time){
      return monthDateFormat.format(new java.util.Date(time));
  }

  static final private SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");
  public synchronized static final String monthFormat(long time){
      return monthFormat.format(new java.util.Date(time));
  }
  static private String ESC = "\\\\";
  static private String APOS = "\\\'";
  
  public static void main(String[] args) {
	String s = "'sss'";
	System.out.println(validate(s));
}
  
  static public String validate(String s){
      if (s==null)
          return "";
      
      //as simple as .replace("\\", "\\\\").replace("\'", "\\\'")
       int len = s.length();
       int i=0;
       for (; i<len; i++)
       {
          char c = s.charAt(i);
           if (c=='\''||c=='\\')
               break;
       }
       if (i==len)
           return s;
       
      StringBuffer sb = new StringBuffer(s.substring(0, i));
      for (; i<len; i++)
      {
          char c = s.charAt(i);
          if (c=='\''){
              sb.append(APOS);
          }else if(c=='\\'){
              sb.append(ESC);
          }else{
              sb.append(c);
          }
      }
      return sb.toString();
  }

  static public int validateInt(String s){
      int ret = -1;
      try {
          ret = Integer.parseInt(StringUtils.trimToEmpty(s));
      } catch (NumberFormatException e) {
          e.printStackTrace();
      }
      return ret;
  }
  static public long validateLong(String s){
      long ret = -1;
      try {
          ret = Long.parseLong(StringUtils.trimToEmpty(s));
      } catch (NumberFormatException e) {
          e.printStackTrace();
      }
      return ret;
  }
  
  static public String filter(String s){
      if (s!=null) {
          int len = s.length();
          StringBuffer sb = new StringBuffer();
          for (int i=0; i<len; i++) {
             char c = s.charAt(i);
              if (c >= '0' && c <= '9') {
                  sb.append(c);
              }
          }
          s = sb.toString();
      }
      return s;
  }
}
