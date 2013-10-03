//
// Copyright XinLab Inc (blueapple.mobi) 2006-2008
// Proprietary and confidential
// All rights reserved
//
// $Id: XinConfig.java,v 1.13 2012/10/17 10:08:24 qingyaoting Exp $
//
package com.xinlab.blueapple.contenttool.common;

public class XinConfig {
    
    public final static boolean debug = true;

    public static final String configFilename = (System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") == -1) ? "/home/admin/conf/blueapple.cfg" : "c:\\home\\admin\\conf\\blueapple.cfg";

    public static final int maxUserNum = 100000;

    public static final String sendmailServer = "127.0.0.1";

    public static final byte[] magic = {19, -49, -79, 25, -35, 88, -104, -114, -89, 109, -7, -72, 47, 74, 51, 93, -22, -123, -44, 107, -72, -26, 75, -123, -4, -81, -19, 74, 81, 114, 98, -12, -52, 80, -38, 25, -22, 77, 87, -122, -57, -66, 80, 50, 69, 24, 81, -127, 33, 27, -41, -116, 17, -105, 124, 106, -94, -84, 89, -123, 102, -34, 5, 62, -126, -50, 113, 113, 22, 95, 38, 86, 89, 16, -40, -6, 100, 125, 102, -36, 96, -22, -67, 78, -84, -58, 100, -76, 58, -6, 8, 122, 52, 79, -85, -108, -19, 126, -118, -47, 3, 30, -111, 126, 111, 50, 120, -47, 106, 2, -31};
    static public final byte[] magic1 = {87, -3, -18, 76, -58, -33, 67, -72, 46, -38, -5, 124, 31, -103, -78, -99, 51, -67, 19, -102, 103, 96, -61, -47, 76, -12, -99, 0, 28, -25, -103, 55, -38, 101, -105, 6, -22, -33, -24, -16, 22, -127, 88, 55, -39, -10, -8, -8, -110, -39, 112, 4, 107, -79, -77, -52, 91, 49, -128, 37, -48, -64, 123, -46, -1, 89, -109, 26, 113, -117, 56, 97, 48, 99, -1, 54, -66, 16, 51, -121, 9, -11, -73, -72, -94, -39, -38, -89, 111, -37, 25, -105, 86, -60, 13, 125, 2, 58, 97, 79, -30, 59, -32, -125, -5, -116, 59, 88, -14, -9, -83, 93, -119, -74, 48, 18, -86, 80, -19, -91, 112, -110, 57, -77, 116, -90, -2, 23, -70, 98, -119, 120, 116, 41, 7, -85, 6, -5, 73, 78, 46, -76, -126, 57, -83, -23, 124, -36, 16, 90, 49, -61, 54, 106, -69, 105, 53, -55, -37, -125, -127, 34, -83, 34, -62, 125, -62, 126, -59, -93, 49, 95, 123, -98, 114, -114, -53, -79, 3, 90, 40, 22, -14, 12, 62, -38, -57, -44, -33, 5, -86, 91};
    static public final byte[] magic2 = {123, 48, 77, -98, -80, 5, 94, -50, -89, -1, 96, 20, 44, 61, -109, -88, -30, 8, -75, 11, 93, -121, 120, -117, 107, -68, -37, -52, -11, 68, 60, -25, -53, 91, 6, -93, 72, -45, 18, -23, -71, 27, 123, -127, 100, 89, -62, -11, -102, 99, -100, -46, -3, 43, -5, -73, -78, -34, -26, -101, 87, 115, -32, -114, -97, -44, 66, 82, -68, 47, 99, 117, -30, -52, 51, -42, -49, 99, 52, -61, 76, 59, 76, 117, -32, -48, 124, 8, 109, -7, 57, 50, 37, -48, 114, 113, -67, -18, 21, 117, 108, 117, -26, 79, 57, -17, -114, -22, 83, -86, -97, 70, -14, 5, 126, -112, 115, -105, -89, 115, -122, -15, -94, 77, 96, 103, -105, 91, 2, 40, 34, -54, 127, 13, 59, 17, -9, -61, -47, -5, 121, 88, -21, 33, -63, -42, 25, -103, -112, 16, 81, -61, -50, -34, 75, 86, -62, 30, -84, -18, 43, -14, -44, 85, 12, 13, -47, -38, 28, -90, 44, 117, 11, 0, 36, -108, 52, 60, 66, 36, 98, -90, -76, -51, -56, -106, 90, -126, -12, 124, -65, -107}; 

    public static final String tmpPath = "/home/admin/tmp/";//XinConfigFile.getInstance().getTmpPath();
    public static final int maxFileSize = 10485760;//=10M=1024*1024*10;
    
    
    //
    // from config file
    //
    public static final String dbUser = XinConfigFile.getInstance().getDBUser();
    public static final String dbPassword = XinConfigFile.getInstance().getDBPassword();
    public static final String dbDriver = XinConfigFile.getInstance().getDBDriver();
    public static final String dbUrl = XinConfigFile.getInstance().getDBUrl();

    public static final String deviceDBUser = XinConfigFile.getInstance().getDeviceDBUser();
    public static final String deviceDBPassword = XinConfigFile.getInstance().getDeviceDBPassword();
    public static final String deviceDBDriver = XinConfigFile.getInstance().getDeviceDBDriver();
    public static final String deviceDBUrl = XinConfigFile.getInstance().getDeviceDBUrl();

    public static final String ipLookupDBUrl = XinConfigFile.getInstance().getIPLookupDBUrl();
    public static final String statsDBUrl = XinConfigFile.getInstance().getStatsDBUrl();
    public static final String uploadDBUrl = XinConfigFile.getInstance().getUploadDBUrl();
    public static final String cnDBUrl = XinConfigFile.getInstance().getCnDBUrl();
    
    public static final String alertDBUrl = XinConfigFile.getInstance().getAlertDBUrl();
    
    public static final String airtelalertDBUrl = XinConfigFile.getInstance().getAirtelAlertDBUrl();  
    
    public static final String vodafonealertDBUrl = XinConfigFile.getInstance().getVoDaFoneAlertDBUrl();
    
    public static final String vodafoneDBUrl = XinConfigFile.getInstance().getVoDaFoneDBUrl();
    
    public static final String tataAlertDBUrl = XinConfigFile.getInstance().getTataDBUrl();
    
    public static final String ideAlertDBUrl = XinConfigFile.getInstance().getIDEADBUrl();
    
    public static final boolean useDBSlayer = XinConfigFile.getInstance().getUseDBSlayer();
    
    public static final String localIp = XinConfigFile.getInstance().getLocalIp();
    public static final String logFileRoot = XinConfigFile.getInstance().getLogFileRoot();
    
    public static final int bagetcontentRefreshInterval = XinConfigFile.getInstance().getBagetcontentRefreshInterval();
    
    public static final String logger = XinConfigFile.getInstance().getLogger();
    public static final String[] thumbnail = XinConfigFile.getInstance().getThumbnail();
    public static final long thumbnailRange = XinConfigFile.getInstance().getThumbnailRange();
    public static final String[] thumbnail2 = XinConfigFile.getInstance().getThumbnail2();
    public static final String thumbnailProxy = XinConfigFile.getInstance().getThumbnailProxy();
    
    public static final boolean sendAlert = XinConfigFile.getInstance().getSendAlert();
    
    public static final int recentViewLimit = XinConfigFile.getInstance().getRecentViewLimit();
    public static final int popularSearchItem = XinConfigFile.getInstance().getPopularSearchItem();

    public static final String[] baseServers = XinConfigFile.getInstance().getBaseServers();
    public static final String[] mediaUrlResolver = XinConfigFile.getInstance().getMediaUrlResolver();
    public static final String[] mscClusterId = XinConfigFile.getInstance().getMscClusterId();
    
    public static final int maxBaDBConnection = XinConfigFile.getInstance().getMaxBaDBConnection();
    public static final int maxIpLookupDBConnection = XinConfigFile.getInstance().getMaxIpLookupDBConnection();
    public static final int maxStatsDBConnection = XinConfigFile.getInstance().getMaxStatsDBConnection();
    public static final int maxDeviceDBConnection = XinConfigFile.getInstance().getMaxDeviceDBConnection();
    public static final int maxRVDBConnection = XinConfigFile.getInstance().getMaxRVDBConnection();

    public static final boolean useImageCache = XinConfigFile.getInstance().getUseImageCache();
//    public static final boolean doPromotion = XinConfigFile.getInstance().getDoPromotion();
    
    // web redirect
    public static final String webPlayUrl = XinConfigFile.getInstance().getWebPlayUrl();
    
    public static final int recentViewUpdateInterval = XinConfigFile.getInstance().getRecentViewUpdateInterval();
    public static final int translateTitleUpdateInterval = XinConfigFile.getInstance().getTranslateTitleUpdateInterval();

    // cn memcached info
    public static final String memcachedServers = XinConfigFile.getInstance().getMemcachedServers();    
    public static final String memcachedWeights = XinConfigFile.getInstance().getMemcachedWeights(); 
    
    //related videos
    public static final String rvDBUser = XinConfigFile.getInstance().getRVDBUser();
    public static final String rvDBPasswd = XinConfigFile.getInstance().getRVDBPasswd();
    public static final String rvDBDriver = XinConfigFile.getInstance().getRVDBDriver();
    public static final String rvDBUrl = XinConfigFile.getInstance().getRVDBUrl();
    
    //load balancer db table name
    public static final String clusterDBTable = XinConfigFile.getInstance().getClusterDBTable();
    public static final String clusterIpDBTable = XinConfigFile.getInstance().getClusterIpDBTable();
    public static final String clusterContentDBTable = XinConfigFile.getInstance().getClusterContentDBTable();

    public static final String imageMemcached = XinConfigFile.getInstance().getImageMemcached();
    public static final String imageMemcachedLoad = XinConfigFile.getInstance().getImageMemcachedLoad();
    
    public static final String userDBUrl = XinConfigFile.getInstance().getUserDBUrl();
    public static final int maxUserDBConnection = XinConfigFile.getInstance().getMaxUserDBConnection();

    public static final String devicePediaDBUrl = XinConfigFile.getInstance().getDevicePediaDBUrl();
    public static final String devicePediaDBUser = XinConfigFile.getInstance().getDevicePediaDBUser();
    public static final String devicePediaDBPassword = XinConfigFile.getInstance().getDevicePediaDBPassword();
    public static final String devicePediaDBDriver = XinConfigFile.getInstance().getDevicePediaDBDriver();
    public static final int maxDevicePediaDBConnection = XinConfigFile.getInstance().getMaxDevicePediaDBConnection();
    
    public static final int adValve = XinConfigFile.getInstance().getAdValve();
    public static final boolean adNewFlag = XinConfigFile.getInstance().getAdNewFlag();

}
