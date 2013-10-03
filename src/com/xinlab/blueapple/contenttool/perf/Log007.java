package com.xinlab.blueapple.contenttool.perf;

import java.util.concurrent.ConcurrentLinkedQueue;

import com.xinlab.blueapple.contenttool.common.Xin007Property;
import com.xinlab.blueapple.contenttool.common.XinCommonUtil;

public class Log007 {
    
    static ConcurrentLinkedQueue <Node007> oo7Queue = new ConcurrentLinkedQueue<Node007>();
    static boolean enable = false;
    
    private static long last_start_time_db=-1;
    private static int cur_number_db=0;
    private static int cur_sum_db=0;
    private static int cur_max_db = 0;
    private static final int FLUSH_INTERVAL_DB = 5000;
    private static final int DB_SLOWNESS_THRESHOLD = XinCommonUtil.getIntFromString(Xin007Property.getProperty("007_db_slowness_threshold"),100);

    private static long last_start_time_mc=-1;
    private static int cur_number_mc=0;
    private static int cur_sum_mc=0;
    private static int cur_max_mc = 0;
    private static final int FLUSH_INTERVAL_MC = 5000;
    private static final int MC_SLOWNESS_THRESHOLD = XinCommonUtil.getIntFromString(Xin007Property.getProperty("007_mc_slowness_threshold"),50);

    private static long last_start_time_dc=-1;
    private static int cur_number_dc=0;
    private static int cur_sum_dc=0;
    private static int cur_max_dc = 0;
    private static final int FLUSH_INTERVAL_DC = 5000;
    private static final int DC_SLOWNESS_THRESHOLD = 20;

    public static long[] sqlCount = new long[1000];
    private static final boolean counterFlag = true;
    private static int total = 0;
    
    static {
        
        int isEnabled = XinCommonUtil.getIntFromString(Xin007Property.getProperty("007_enable"),0);
        if (isEnabled == 1) enable = true;
        
        if (enable) {
            new Thread(new Dumper007()).start();
        }
        
        if(counterFlag)
    		for(int i=1; i<1000; i++)
    			sqlCount[i]=0;
    }
    
    public static void logWapServlet(String servletId, long startTime, int duration) {
        if (enable) {
            Node007 node = new WapNode007("sv",servletId,startTime,duration);
            oo7Queue.offer(node);
        }
    }
    public static void logWapAd(String adId, long startTime, int duration) {
        if (enable) {
            Node007 node = new WapNode007("ad",adId,startTime,duration);
            oo7Queue.offer(node);
        }
    }
    public static void logWapBase(String searchId, long startTime, int duration) {
        if (enable) {
            Node007 node = new WapNode007("bs",searchId,startTime,duration);
            oo7Queue.offer(node);
        }
    }
    public static void logWapDc(String queryId, long startTime, int duration) {
        if (enable) {

            // only log slow queries
            if (duration > DC_SLOWNESS_THRESHOLD) {
                Node007 node = new WapNode007("DC",queryId,startTime,duration);
                oo7Queue.offer(node);
            }
            
            // now collect and log average db performance accordingly
            if (last_start_time_dc == -1) {
                // reset everything to begin
                resetDcPerf();
            }

            // calculate aggregated info for each interval
            cur_number_dc++;
            cur_sum_dc += duration;
            if (duration > cur_max_dc) cur_max_dc = duration;

            // log once FLUSH_INTERVAL_DB elapsed
            if (System.currentTimeMillis() - last_start_time_dc > FLUSH_INTERVAL_DC) {
                // flush out
                Node007 node = new AggregateWapNode007("dc","avg",last_start_time_dc,cur_number_dc,cur_sum_dc,cur_max_dc);
                oo7Queue.offer(node);
                resetDcPerf();
            }
            
        }
    }
    public static void logWapDb(String queryId, long startTime, int duration) {
        if (enable) {
        	if(counterFlag){
        		total++;
        		int i = queryId.indexOf('_');
        		if(i>0)
        			queryId = queryId.substring(0,i);
        		try{
            		int index = Integer.parseInt(queryId);
        			sqlCount[index]++;
        		}
        		catch(Exception e){
        			e.printStackTrace();
        		}
            }
            // only log slow queries
            if (duration > DB_SLOWNESS_THRESHOLD) {
                Node007 node = new WapNode007("ds",queryId,startTime,duration);
                oo7Queue.offer(node);
            }
            
            // now collect and log average db performance accordingly
            if (last_start_time_db == -1) {
                // reset everything to begin
                resetDbPerf();
            }

            // calculate aggregated info for each interval
            cur_number_db++;
            cur_sum_db += duration;
            if (duration > cur_max_db) cur_max_db = duration;

            // log once FLUSH_INTERVAL_DB elapsed
            if (System.currentTimeMillis() - last_start_time_db > FLUSH_INTERVAL_DB) {
                // flush out
                Node007 node = new AggregateWapNode007("db","avg",last_start_time_db,cur_number_db,cur_sum_db,cur_max_db);
                oo7Queue.offer(node);
                resetDbPerf();
            }
            
            if(total%10000==0){
            	StringBuffer s = new StringBuffer();
            	for(int i=1; i<430;i++)
            		s.append(i+":"+sqlCount[i]+" - ");
            	Node007 node = new WapNode007("sqlcount",s.toString(),startTime,duration);
                oo7Queue.offer(node);
            }
            	
            
        }
    }
    public static void logWapMemcached(String key, long startTime, int duration) {
        
        // only log slow mc operations
        if (duration > MC_SLOWNESS_THRESHOLD) {
            Node007 node = new WapNode007("ms",key,startTime,duration);
            oo7Queue.offer(node);
        }
        
        
        // now collect and log average mc performance accordingly
        if (last_start_time_mc == -1) {
            // reset everything to begin
            resetMcPerf();
        }

        // calculate aggregated info for each interval
        cur_number_mc++;
        cur_sum_mc += duration;
        if (duration > cur_max_mc) cur_max_mc = duration;

        // log once FLUSH_INTERVAL_MC elapsed
        if (System.currentTimeMillis() - last_start_time_mc > FLUSH_INTERVAL_MC) {
            // flush out
            Node007 node = new AggregateWapNode007("mc","avg",last_start_time_mc,cur_number_mc,cur_sum_mc,cur_max_mc);
            oo7Queue.offer(node);
            resetMcPerf();
        }
    }
    public static void logWapRecrawl(String pageurl, long startTime, int duration) {
        if (enable) {
            Node007 node = new WapNode007("rc",pageurl,startTime,duration);
            oo7Queue.offer(node);
        }
    }
    public static void logWapEs(String cid, long startTime, int duration) {
        if (enable) {
            Node007 node = new WapNode007("es",cid,startTime,duration);
            oo7Queue.offer(node);
        }
    }

    //
    // base
    //
    public static void logBaseList(String url, long startTime, int duration, int entries) {
        if (enable) {
            Node007 node = new BaseNode007("list",url,startTime,duration,String.valueOf(entries));
            oo7Queue.offer(node);
        }
    }
    public static void logBasePage(String url, long startTime, int duration, boolean success) {
        if (enable) {
            Node007 node = new BaseNode007("page",url,startTime,duration,String.valueOf(success));
            oo7Queue.offer(node);
        }
    }
    public static void logBaseOverall(String key, long startTime, int duration, int entries) {
        if (enable) {
            Node007 node = new BaseNode007("overall",key,startTime,duration,String.valueOf(entries));
            oo7Queue.offer(node);
        }
    }
    public static void logBaseHttp(String url, long startTime, int duration, int statusCode, int length) {
        if (enable) {
            Node007 node = new BaseNode007("http",url,startTime,duration,statusCode+","+length);
            oo7Queue.offer(node);
        }
    }

    public static void logWapRv(String cid, long startTime, int duration) {
        if (enable) {
            Node007 node = new WapNode007("rv",cid,startTime,duration);
            oo7Queue.offer(node);
        }
    }
    
    public static void logWapCf(String queryString, long startTime, int duration) {
        if (enable) {
            Node007 node = new WapNode007("cf",queryString,startTime,duration);
            oo7Queue.offer(node);
        }
    }
    
    private static void resetDcPerf() {
        last_start_time_dc = System.currentTimeMillis();
        cur_number_dc = 0;
        cur_sum_dc = 0;
        cur_max_dc = 0;
    }

    private static void resetDbPerf() {
        last_start_time_db = System.currentTimeMillis();
        cur_number_db = 0;
        cur_sum_db = 0;
        cur_max_db = 0;
    }

    private static void resetMcPerf() {
        last_start_time_mc = System.currentTimeMillis();
        cur_number_mc = 0;
        cur_sum_mc = 0;
        cur_max_mc = 0;
    }

    /***************************************Recommendation engine logger**********************************************/
    
    
    public static void logReRelatedVideos(String youtubeId, long startTime, int duration) {
        if (enable) {
            Node007 node = new RENode007("rv", youtubeId, startTime, duration);
            oo7Queue.offer(node);
        }
    }

    public static void logReContentSearch(String term, long startTime, int duration) {
        if (enable) {
            Node007 node = new RENode007("cs", term, startTime, duration);
            oo7Queue.offer(node);
        }
    }

    public static void logReClassifier(String term, long startTime, int duration) {
        if (enable) {
            Node007 node = new RENode007("cl", term, startTime, duration);
            oo7Queue.offer(node);
        }
    }

    public static void logReContentManagerCid(String cid, long startTime, int duration) {
        if (enable) {
            Node007 node = new RENode007("cmcid", cid, startTime, duration);
            oo7Queue.offer(node);
        }
    }

    public static void logReContentManagerUrl(String pageurl, long startTime, int duration) {
        if (enable) {
            Node007 node = new RENode007("cmurl", pageurl, startTime, duration);
            oo7Queue.offer(node);
        }
    }

    public static void logReMemcached(String key, long startTime, int duration) {

        // only log slow mc operations
        if (duration > MC_SLOWNESS_THRESHOLD) {
            Node007 node = new RENode007("ms", key, startTime, duration);
            oo7Queue.offer(node);
        }


        // now collect and log average mc performance accordingly
        if (last_start_time_mc == -1) {
            // reset everything to begin
            resetMcPerf();
        }

        // calculate aggregated info for each interval
        cur_number_mc++;
        cur_sum_mc += duration;
        if (duration > cur_max_mc) cur_max_mc = duration;

        // log once FLUSH_INTERVAL_MC elapsed
        if (System.currentTimeMillis() - last_start_time_mc > FLUSH_INTERVAL_MC) {
            // flush out
            Node007 node = new AggregateRENode007("mc", "avg", last_start_time_mc, cur_number_mc, cur_sum_mc, cur_max_mc);
            oo7Queue.offer(node);
            resetMcPerf();
        }
    } 
    
}
