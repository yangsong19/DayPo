package com.xinlab.blueapple.contenttool.perf;

import java.util.Arrays;
import java.util.Date;

import org.apache.log4j.Logger;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
import com.xinlab.blueapple.contenttool.common.Xin007Property;

public class MC007 {
    private static final Logger  vulogger = Logger.getLogger(MC007.class);
    
    private static final String MEMCACHED_SERVERS_KEY = "007_memcached_servers";
    private static final String MEMCACHED_SERVER_WEIGHTS_KEY = "007_memcached_server_weights";
    private static final String MC007_POOL_NAME = "007";
    
    // create a static client as most installs only need
    // a single instance
    protected static MemCachedClient mcc = new MemCachedClient();

    // set up connection pool once at class load
    static {
        
        // initialize memcached
        init();
        mcc.setPoolName(MC007_POOL_NAME);
        // initialize counters
        
        // figure out hostname to use
        String hostname = System.getenv("HOSTNAME");
        if (hostname == null) hostname = System.getenv("COMPUTERNAME");
        if (hostname == null) hostname = "vuclip";
        vulogger.info("HOSTNAME: " + hostname);
        String counterKey = "p_" + hostname + "_" + Dumper007.WAP;
        if (mcc.getCounter(counterKey) == -1) {
            mcc.storeCounter(counterKey, 0l);
        }
        counterKey = "p_" + hostname + "_" + Dumper007.BASE;
        if (mcc.getCounter(counterKey) == -1) {
            mcc.storeCounter(counterKey, 0l);
        }
    }

    public static Object get(String key) {
        return mcc.get(key);
    }
    
    public static void set(String key, Object value) {
        mcc.set(key, value);
    }
    
    public static void set(String key, Object value, Date expiration) {
        mcc.set(key, value, expiration);
    }
    
    public static Object delete(String key) {
        return mcc.delete(key);
    }

    public static void init() {
        vulogger.info("Initialize 007 memcached ...");

        //memcached_servers=memcached1.blueapple.mobi:1624,memcached1.blueapple.mobi:1624,memcached1.blueapple.mobi:1624
        //memcached_server_weights=3,3,3
        String mcServers = Xin007Property.getProperty(MEMCACHED_SERVERS_KEY);
        vulogger.info("mcServers string from properties:" + mcServers);
        String mcServerWeights = Xin007Property.getProperty(MEMCACHED_SERVER_WEIGHTS_KEY);
        vulogger.info("mcServerWeights=" + mcServerWeights);

        // server list and weights
        String[] servers = mcServers.split(",");
        vulogger.info("MCServers=" + Arrays.toString(servers));
        Integer[] weights = null;
        if (mcServerWeights != null && mcServerWeights.trim().length() > 0) {
            String [] weightsString =  mcServerWeights.split(",");
            if (weightsString != null && weightsString.length > 0) {
                weights = new Integer[weightsString.length];
                for (int i=0; i<weightsString.length; i++) {
                    try {
                        weights[i] = new Integer(weightsString[i]);
                    } catch (NumberFormatException e) {
                        // defaults to 3 on bad entry
                        weights[i] = 3;
                    }
                }
            }
        }
        vulogger.info("weights=" + Arrays.toString(weights));
        // grab an instance of our connection pool
        SockIOPool pool = SockIOPool.getInstance(MC007_POOL_NAME);

        // set the servers and the weights
        pool.setServers( servers );
        if (weights != null) pool.setWeights( weights );

        // TODO: put all the following parameters in ba.properties file
        
        int initialConnections  = 2;
        int minSpareConnections = 2;
        int maxSpareConnections = 2;   
        long maxIdleTime        = 1000 * 60 * 30;       // 30 minutes
        long maxBusyTime        = 1000 * 60 * 5;        // 5 minutes
        long maintThreadSleep   = 1000 * 5;                     // 5 seconds
        int     socketTimeOut       = 1000 * 3;                 // 3 seconds to block on reads
        int     socketConnectTO     = 1000 * 3;                 // 3 seconds to block on initial connections.  If 0, then will use blocking connect (default)
        boolean failover        = true;                        // turn off auto-failover in event of server down       
        boolean nagleAlg        = false;                        // turn off Nagle's algorithm on all sockets in pool    
        boolean aliveCheck      = false;                        // disable health check of socket on checkout

        pool.setInitConn( initialConnections );
        pool.setMinConn( minSpareConnections );
        pool.setMaxConn( maxSpareConnections );
        pool.setMaxIdle( maxIdleTime );
        pool.setMaxBusyTime( maxBusyTime );
        pool.setMaintSleep( maintThreadSleep );
        pool.setSocketTO( socketTimeOut );
        pool.setSocketConnectTO( socketConnectTO );
        pool.setNagle( nagleAlg ); 
        pool.setFailover(failover);
        pool.setAliveCheck(aliveCheck);
        pool.setHashingAlg( SockIOPool.NEW_COMPAT_HASH );
        
        // initialize the connection pool
        pool.initialize();

        // lets set some compression on for the client
        // compress anything larger than 64k
        mcc.setCompressEnable( true );
        mcc.setCompressThreshold( 10 * 1024 );
    }
}