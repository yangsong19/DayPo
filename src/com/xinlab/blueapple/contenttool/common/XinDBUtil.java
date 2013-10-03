//
// Copyright XinLab Inc (blueapple.mobi) 2006-2008
// Proprietary and confidential
// All rights reserved
//
package com.xinlab.blueapple.contenttool.common;

import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import com.xinlab.blueapple.contenttool.db.XinBADBAccessor1;
import com.xinlab.blueapple.contenttool.db.XinBADBAccessor2;
import com.xinlab.blueapple.contenttool.db.XinDBAccessor;
import com.xinlab.blueapple.contenttool.perf.Log007;
public class XinDBUtil extends XinDBUtilBase
{
    static final int UPDATE_INTERVAL=100;
    
    private static final int Hour = XinCommonUtil.getIntFromString(BAProperty.getProperty("BASESSIONCACHE_HARMONIZED_SEARCH_SESSION_TIMEOUT"), 24);
    private static final Logger  vulogger = Logger.getLogger(XinDBUtil.class);
	static final private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    

	
	private static final boolean userDBMode = ("true".equals(BAProperty.getProperty("user.db.standalone")))?true:false;
	
	private static int CMCD_V1=7;
	private static int CMCD_V2=9;
	private static int CMCD_V3=10;
	private static int CMCD_V4=12;
	

    //just save some typing...
    public static final XinDBAccessor getAccessor(){
        if (XinConfig.useDBSlayer)
            return new XinBADBAccessor2();
        else
            return new XinBADBAccessor1();
    }
	
	public static String [] [] getFeedtitle(){
		   String [] []  feedtitle=null;
		   String sql="SELECT feed_id,feed_title FROM feed_msg";
		   long stime = System.currentTimeMillis();
		   feedtitle=getAccessor().getTable(sql);
		   Log007.logWapDb("584", stime, (int)(System.currentTimeMillis() - stime));
		   return feedtitle;
	   }  
	
	public static int instertFeeditem(String feed_id,String item_title,String item_link_url,String item_description,String item_pubdate,String item_media_content_url,String item_media_title,String item_media_thumbnail,String item_media_keywords){
		String sql="insert into media_msg(feed_id,item_title,item_link_url,item_description,item_pubdate,item_media_content_url,item_media_title,item_media_thumbnail,item_media_keywords) values("+validate(feed_id)+",'"+validate(item_title)+"', '"+validate(item_link_url) + "','"+validate(item_description)+"','"+validate(item_pubdate)+"','"+validate(item_media_content_url)+"','"+validate(item_media_title)+"','"+validate(item_media_thumbnail)+"','"+validate(item_media_keywords)+"')";
		long stime=System.currentTimeMillis();
		int result=getAccessor().insertAutoIncrement(sql);
		Log007.logWapDb("585", stime,(int) (System.currentTimeMillis() - stime));
		return result;
	}       
             
}
