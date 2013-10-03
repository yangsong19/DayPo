//
// Copyright XinLab Inc (blueapple.mobi) 2006-2008
// Proprietary and confidential
// All rights reserved
//
// $Id: XinConfigFile.java,v 1.18 2012/10/17 10:37:53 qingyaoting Exp $
//
package com.xinlab.blueapple.contenttool.common;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

public class XinConfigFile {

    private static XinConfigFile config;
    
    private static Properties properties;
    private static boolean isParsed = false;
    
    //
    // database
    //
    private String dbDriver = "com.mysql.jdbc.Driver";
    private String dbUrl = "jdbc:mysql://207.7.137.4/blueapple";
    private String dbUser = "root";
    private String dbPassword = "";

    private String deviceDBDriver = "com.mysql.jdbc.Driver";
    private String deviceDBUrl = "jdbc:mysql://207.7.137.4/devicedb";
    private String deviceDBUser = "root";
    private String deviceDBPassword = "";
    
    private String ipLookupDBUrl = "jdbc:mysql://207.7.137.4/ip2location";
    private String statsDBUrl = "jdbc:mysql://207.7.137.30/bastats";
    private String uploadDBUrl = "jdbc:mysql://184.105.234.194/upload";
    private String cnDBUrl = null;
    
    private String alertDBUrl = "jdbc:mysql://66.160.159.79/alert?requireSSL=false&useUnicode=true&characterEncoding=UTF-8";
    
    private String airtelalertDBUrl = "jdbc:mysql://66.160.159.79/alert?requireSSL=false&useUnicode=true&characterEncoding=UTF-8";
    private String vodafonealertDBUrl = "jdbc:mysql://66.160.159.79/alert?requireSSL=false&useUnicode=true&characterEncoding=UTF-8";
    private String vodafoneDBUrl = "jdbc:mysql://66.160.159.79/alert?requireSSL=false&useUnicode=true&characterEncoding=UTF-8";
    
    private String  tataDBUrl="jdbc:mysql://66.160.159.79/alert?requireSSL=false&useUnicode=true&characterEncoding=UTF-8";
    private String  ideaDBUrl="jdbc:mysql://66.160.159.79/alert?requireSSL=false&useUnicode=true&characterEncoding=UTF-8";
    
    private String rvDBDriver = "com.mysql.jdbc.Driver";
    private String relatedVideoDBUrl = "jdbc:mysql://65.49.33.62/related_videos";
    private String rvDBUser = "root";
    private String rvDBPasswd="";
    
    //load balancer db table name
    private String clusterDBTable = "cluster";
    private String clusterIpDBTable = "clusterIp";
    private String clusterContentDBTable = "cluster_content";
    
    private boolean useDBSlayer = false;
    
    //
    // ba servlet stuff
    //
    private String localIp = null;
    private String logFileRoot = (System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") == -1) ? "/home/admin/log/blueapple/" : "c:\\home\\admin\\log\\blueapple\\";
                   
    private int bagetcontentRefreshInterval = 8;
    
    //
    // es stuff
    //
    private String logger=null;
    private String thumbnail="";
    private long thumbnailRange=Long.MAX_VALUE;
    private String thumbnail2="";
    private boolean sendAlert=true;
    
    private String thumbnailProxy="";
    
    private int recentViewLimit = 100;
    private int popularSearchItem = 20;

    private String baseServers = "";
    private String mediaUrlResolver = "";
    private String mscClusterId = "";
    
    private int maxBaDBConnection = 150;
    private int maxIpLookupDBConnection = 60;
    private int maxStatsDBConnection = 100;
    private int maxDeviceDBConnection = 60;
    private int maxRVDBConnection = 60;
    private String webPlayUrl = "www.vuclip.com";
    
    private int recentViewUpdateInterval = 100;
    private int translateTitleUpdateInterval = 100;

    private String memcachedServers = null;
    private String memcachedWeights = null;

    private String imageMemcached = null;
    private String imageMemcachedLoad = null;

    //
    // cache control
    // 
    private boolean useImageCache=true;
//    private boolean doPromotion=false;
    
 // talkies
    private String userDBUrl = "jdbc:mysql://test1.blueapple.mobi/Blueapple?requireSSL=false&useUnicode=true&characterEncoding=UTF-8";
    private int maxUserDBConnection = 500;

// device pedia db
    private String devicePediaDBUrl = "jdbc:mysql://65.49.33.78/devicepedia?requireSSL=false&useUnicode=true&characterEncoding=UTF-8";
    private String devicePediaDBUser = "root";
    private String devicePediaDBPassword = "";
    private String devicePediaDBDriver = "com.mysql.jdbc.Driver";
    private int maxDevicePediaDBConnection = 10;

//Ad flow control valve
    private int adValve = 100;
    private boolean adNewFlag = false;
    
    private XinConfigFile()
    {
        File configFile = new File(XinConfig.configFilename);
        if (configFile.exists())
        {
            if (!isParsed)
            {
                loadProperties();
                parseConfigFile();
            }
        }
    }
    static final synchronized XinConfigFile getInstance()
    {
        if (config == null)
            config = new XinConfigFile();
        return config;
    }
    final void loadProperties(){
        try {
            InputStream in = null;
            boolean useCipher = false;
            if (useCipher)
            {
                byte[] key = new BigInteger(XinConfig.magic).modPow(new BigInteger(XinConfig.magic1), new BigInteger(XinConfig.magic2)).toByteArray();
                SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.DECRYPT_MODE, skeySpec);
                
                in = new CipherInputStream(new FileInputStream(XinConfig.configFilename), cipher);
            }
            else
            {
                in = new BufferedInputStream(new FileInputStream(XinConfig.configFilename));
            }
            properties = new Properties();
            properties.load(in);
            isParsed = true;
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    final void parseConfigFile()
    {
        dbUser = getValue(properties.getProperty("dbUser"), dbUser);
        dbPassword = getValue(properties.getProperty("dbPassword"), dbPassword);
        dbDriver = getValue(properties.getProperty("dbDriver"), dbDriver);
        dbUrl = getValue(properties.getProperty("dbUrl"), dbUrl);
        alertDBUrl = getValue(properties.getProperty("alertDBUrl"), alertDBUrl);
        tataDBUrl=getValue(properties.getProperty("tataDBUrl"), tataDBUrl);
        ideaDBUrl=getValue(properties.getProperty("ideaDBUrl"), ideaDBUrl);
        deviceDBUser = getValue(properties.getProperty("deviceDBUser"), deviceDBUser);
        deviceDBPassword = getValue(properties.getProperty("deviceDBPassword"), deviceDBPassword);
        deviceDBDriver = getValue(properties.getProperty("deviceDBDriver"), deviceDBDriver);
        deviceDBUrl = getValue(properties.getProperty("deviceDBUrl"), deviceDBUrl);
        ipLookupDBUrl = getValue(properties.getProperty("ipLookupDBUrl"), ipLookupDBUrl);
        statsDBUrl = getValue(properties.getProperty("statsDBUrl"), statsDBUrl);
        uploadDBUrl = getValue(properties.getProperty("uploadDBUrl"), uploadDBUrl);
        cnDBUrl = getValue(properties.getProperty("cnDBUrl"), cnDBUrl);
        useDBSlayer = getValue(properties.getProperty("useDBSlayer"), useDBSlayer);
        localIp = getValue(properties.getProperty("localIp"), localIp);
        logFileRoot = getValue(properties.getProperty("logFileRoot"), logFileRoot);
        bagetcontentRefreshInterval = getValue(properties.getProperty("bagetcontentRefreshInterval"), bagetcontentRefreshInterval);
        logger = getValue(properties.getProperty("logger"), logger);
        thumbnail = getValue(properties.getProperty("thumbnail"), thumbnail);
        thumbnailRange = getValue(properties.getProperty("thumbnailRange"), thumbnailRange);
        thumbnail2 = getValue(properties.getProperty("thumbnail2"), thumbnail2);
        thumbnailProxy = getValue(properties.getProperty("thumbnailProxy"), thumbnailProxy);
        sendAlert = getValue(properties.getProperty("sendAlert"), sendAlert);
        recentViewLimit = getValue(properties.getProperty("recentViewLimit"), recentViewLimit);
        popularSearchItem = getValue(properties.getProperty("popularSearchItem"), popularSearchItem);
        baseServers = getValue(properties.getProperty("baseServers"), baseServers);
        mediaUrlResolver = getValue(properties.getProperty("mediaUrlResolver"), mediaUrlResolver);
        mscClusterId = getValue(properties.getProperty("mscClusterId"), mscClusterId);
        maxBaDBConnection = getValue(properties.getProperty("maxBaDBConnection"), maxBaDBConnection);
        maxIpLookupDBConnection = getValue(properties.getProperty("maxIpLookupDBConnection"), maxIpLookupDBConnection);
        maxStatsDBConnection = getValue(properties.getProperty("maxStatsDBConnection"), maxStatsDBConnection);
        maxDeviceDBConnection = getValue(properties.getProperty("maxDeviceDBConnection"), maxDeviceDBConnection);
        useImageCache = getValue(properties.getProperty("useImageCache"), useImageCache);
//        doPromotion = getValue(properties.getProperty("doPromotion"), doPromotion);
        webPlayUrl = getValue(properties.getProperty("webPlayUrl",""), webPlayUrl);
        recentViewUpdateInterval = getValue(properties.getProperty("recentViewUpdateInterval",""), recentViewUpdateInterval);
        translateTitleUpdateInterval = getValue(properties.getProperty("translateTitleUpdateInterval"), translateTitleUpdateInterval);
        memcachedServers = getValue(properties.getProperty("memcachedServers"), memcachedServers);
        memcachedWeights = getValue(properties.getProperty("memcachedWeights"), memcachedWeights);
        maxRVDBConnection = getValue(properties.getProperty("maxRVDBConnection"), maxRVDBConnection);
        rvDBUser = getValue(properties.getProperty("rvDBUser"), rvDBUser);
        rvDBPasswd = getValue(properties.getProperty("rvDBPasswd"), rvDBPasswd);
        rvDBDriver = getValue(properties.getProperty("rvDBDriver"), rvDBDriver);
        relatedVideoDBUrl = getValue(properties.getProperty("relatedVideoDBUrl"), relatedVideoDBUrl);
        clusterDBTable = getValue(properties.getProperty("clusterDBTable"), clusterDBTable);
        clusterIpDBTable = getValue(properties.getProperty("clusterIpDBTable"), clusterIpDBTable);
        clusterContentDBTable = getValue(properties.getProperty("clusterContentDBTable"), clusterContentDBTable);
        imageMemcached = getValue(properties.getProperty("imageMemcached"), imageMemcached);
        imageMemcachedLoad = getValue(properties.getProperty("imageMemcachedLoad"), imageMemcachedLoad);
        userDBUrl = getValue(properties.getProperty("userDBUrl"), userDBUrl);
        maxUserDBConnection = getValue(properties.getProperty("maxUserDBConnection"), maxUserDBConnection);
        devicePediaDBUrl = getValue(properties.getProperty("devicePediaDBUrl"), devicePediaDBUrl);
        devicePediaDBUser = getValue(properties.getProperty("devicePediaDBUser"),devicePediaDBUser);
        devicePediaDBPassword = getValue(properties.getProperty("devicePediaDBPassword"),devicePediaDBPassword);
        devicePediaDBDriver = getValue(properties.getProperty("devicePediaDBDriver"),devicePediaDBDriver);
        maxDevicePediaDBConnection = getValue(properties.getProperty("maxDevicePediaDBConnection"), maxDevicePediaDBConnection);
        airtelalertDBUrl= getValue(properties.getProperty("airtelalertDBUrl"), airtelalertDBUrl);
        vodafonealertDBUrl= getValue(properties.getProperty("vodafonealertDBUrl"), vodafonealertDBUrl);
        vodafoneDBUrl= getValue(properties.getProperty("vodafoneDBUrl"), vodafoneDBUrl);
        adValve = getValue(properties.getProperty("adValve"), adValve);
        adNewFlag = getValue(properties.getProperty("adNewFlag"), adNewFlag);
        
    }
    final String getValue(String s, String v)
    {
        return s!=null?s.trim():v;
    }
    final int getValue(String s, int i)
    {
        if (s!=null)
            try {
                return Integer.parseInt(s.trim()); 
            } catch (Exception e) { } //not a number
        return i;
    }
    final long getValue(String s, long i)
    {
        if (s!=null)
            try {
                return Long.parseLong(s.trim()); 
            } catch (Exception e) { } //not a number
        return i;
    }
    final boolean getValue(String s, boolean b)
    {
        return (s!=null)?Boolean.valueOf(s.trim()).booleanValue():b;
    }

    final String getDBUser() { return dbUser; }
    final String getDBPassword() { return dbPassword; }
    final String getDBDriver() { return dbDriver; }
    final String getDBUrl() { return dbUrl; }
    final String getDeviceDBUser() { return deviceDBUser; }
    final String getDeviceDBPassword() { return deviceDBPassword; }
    final String getDeviceDBDriver() { return deviceDBDriver; }
    final String getDeviceDBUrl() { return deviceDBUrl; }
    final String getIPLookupDBUrl() { return ipLookupDBUrl; }
    final String getStatsDBUrl() { return statsDBUrl; }   
    final String getUploadDBUrl() { return uploadDBUrl; }   
    final String getAlertDBUrl() { return alertDBUrl; }
    final String getAirtelAlertDBUrl() { return airtelalertDBUrl; }
    final String getVoDaFoneAlertDBUrl() { return vodafonealertDBUrl; }
    final String getVoDaFoneDBUrl() { return vodafoneDBUrl; }
    final String getTataDBUrl() { return tataDBUrl; }
    final String getIDEADBUrl() { return ideaDBUrl; }
    final String getCnDBUrl() { return cnDBUrl; }
    final boolean getUseDBSlayer() { return useDBSlayer; }
    final String getLocalIp() { return localIp; }
    final String getLogFileRoot() { return logFileRoot; }
    final int getBagetcontentRefreshInterval() { return bagetcontentRefreshInterval; }
    final String getLogger() { return logger; }
    final String[] getThumbnail() { return VuclipUtil.splitString(thumbnail, ",",true); }
    final long getThumbnailRange() { return thumbnailRange; }
    final String[] getThumbnail2() { return VuclipUtil.splitString(thumbnail2, ",",true); }
    final String getThumbnailProxy() { return thumbnailProxy; }
    final boolean getSendAlert() { return sendAlert; }
    public int getRecentViewLimit() { return recentViewLimit; }
    public int getPopularSearchItem() { return popularSearchItem; }
    public final String[] getBaseServers() { return VuclipUtil.splitString(baseServers, ",", true); }
    public String[] getMediaUrlResolver() { return VuclipUtil.splitString(mediaUrlResolver, ",", true); } 
    public int getMaxBaDBConnection() { return maxBaDBConnection; }
    public int getMaxIpLookupDBConnection() { return maxIpLookupDBConnection; }
    public int getMaxStatsDBConnection() { return maxStatsDBConnection; }
    public int getMaxDeviceDBConnection() { return maxDeviceDBConnection; }
    public boolean getUseImageCache() { return useImageCache; }
//    public boolean getDoPromotion() { return doPromotion; }
    public String getWebPlayUrl() {return webPlayUrl;}
    public int getRecentViewUpdateInterval() {return recentViewUpdateInterval;}
    public int getTranslateTitleUpdateInterval() {return translateTitleUpdateInterval;}
    public String getMemcachedServers() { return memcachedServers; }
    public String getMemcachedWeights() { return memcachedWeights; }
    public String getRVDBUrl() { return relatedVideoDBUrl;}
    public String getRVDBUser() { return rvDBUser;}
    public String getRVDBDriver() { return rvDBDriver;}
    public String getRVDBPasswd() { return rvDBPasswd;}
    public int getMaxRVDBConnection(){ return maxRVDBConnection;}
    public String getClusterDBTable() { return clusterDBTable; }
    public String getClusterIpDBTable() { return clusterIpDBTable; }
    public String getClusterContentDBTable() { return clusterContentDBTable; }
    public String getImageMemcached() { return imageMemcached; }
    public String getImageMemcachedLoad() { return imageMemcachedLoad; }
    
    public int getMaxUserDBConnection() { return maxUserDBConnection; }
    final String getUserDBUrl() { return userDBUrl; }

    final String getDevicePediaDBUrl() { return devicePediaDBUrl; }
    final String getDevicePediaDBUser() { return devicePediaDBUser; }
    final String getDevicePediaDBPassword() { return devicePediaDBPassword; }
    final String getDevicePediaDBDriver() { return devicePediaDBDriver; }
    public int getMaxDevicePediaDBConnection() { return maxDevicePediaDBConnection; }


    public String[] getMscClusterId() { return VuclipUtil.splitString(mscClusterId, ",", true); } 
    
    final int getAdValve() { return adValve; }
    final boolean getAdNewFlag() { return adNewFlag; }

}
