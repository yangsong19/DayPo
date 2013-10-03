//
// Copyright XinLab Inc (blueapple.mobi) 2006-2008
// Proprietary and confidential
// All rights reserved
//
// $Id: DBConPool.java,v 1.3 2012/07/26 19:05:16 dhuang Exp $
//
package com.xinlab.blueapple.contenttool.db;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import com.xinlab.blueapple.contenttool.common.SystemUtil;

public class DBConPool {

    private static final Logger  vulogger = Logger.getLogger(DBConPool.class);

    private ConcurrentLinkedQueue<Connection> conpool = new ConcurrentLinkedQueue<Connection>();
    private AtomicInteger checkedOut = new AtomicInteger(0);

    protected String url;// = "jdbc:mysql://localhost/blueapple";
    protected String user;// = "root";
    protected String password;// = "11202004ok7lip";
    protected String dbdriver;// = "com.mysql.jdbc.Driver";

    private int MAXPOOLSIZE = 10;
    private String name = "BA";

    private Object mutex = new Object();

    //private AtomicInteger conCount = new AtomicInteger(0);

    public DBConPool (String url, String user, String pw, String driver, int max) throws InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        this.url = url;
        this.user = user;
        this.password = pw;
        this.dbdriver = driver;
        MAXPOOLSIZE = max;
        name = url;
        if (name!=null){
            name = name.substring(13);//"skip jdbc:mysql://
            int idx = name.indexOf('?');
            if (idx>0)
                name = name.substring(0,idx);
        }

        Class.forName(dbdriver).newInstance();
    }

    private Connection newConnection() {

        try {
        	//vulogger.info("dbpool-"+name+ " creating new connection using:"+user+" #con created:"+(++conCount));
            /*
        	System.out.println("dbUrl:"+user);
        	System.out.println("dbUser:"+user);
        	System.out.println("dbPwd:"+password);*/
            return DriverManager.getConnection(url, user, password);
        }catch (SQLException e) {
            vulogger.warn("dbpool-"+name+ " can not create new connection for "+url);
            e.printStackTrace();
            return null;
        }
    }

    public Connection getConnection(boolean onError) {

        Connection con = null;

        if (conpool.size() > 0) {
            con = conpool.poll();

            try {
                if (con.isClosed()) {
                    try{
                        con.close();
                    }catch(SQLException se){
                        se.printStackTrace();
                    }
                    con=null;//for gc
                    vulogger.info("dbpool-"+name+ " con closed get a new one");
                    con = getConnection(true);
                }
            }
            catch (SQLException e) {
                try{
                    con.close();
                }catch(SQLException se){
                    se.printStackTrace();
                }
                vulogger.info("dbpool-"+name+ " db exception get a new one");
                e.printStackTrace();
                con = getConnection(true);
            }
        }else if (MAXPOOLSIZE == 0 || checkedOut.get() < MAXPOOLSIZE) {
            con = newConnection();
        }
        if (con != null) {
            if (!onError) incrCheckedOut();
                //checkedOut++;
            //vulogger.info("dbpool-"+name+ " con#"+checkedOut);
            return con;
        }else{
            vulogger.error("dbpool-"+name+ " no avail db con");
            String host="";
            try {
                host = InetAddress.getLocalHost().getHostName();
            } catch (UnknownHostException e) {}
            SystemUtil.sendAlert(host+" "+name+" no avail db con");
            return null;
        }
    }

    public Connection getConnection(long timeout) {
        long startTime = System.currentTimeMillis();
        Connection con;
        while ((con = getConnection(false)) == null) {
            try {
                synchronized (mutex) {
                    mutex.wait(timeout);
                }
            }
            catch (InterruptedException e) {}
            if ((System.currentTimeMillis() - startTime) >= timeout) {
                // Timeout has expired
                vulogger.warn("dbpool-"+name+ " timeout");
                return null;
            }
        }
        return con;
    }

    public void releaseConnection(Connection con) {
        // return connection to pool
        //vulogger.info("dbpool-"+name+ " release con");
        conpool.add(con);
        decrCheckedOut();
        //checkedOut--;
        synchronized (mutex) {
            mutex.notifyAll();
        }
    }

    public void releasePool() {
        vulogger.info("dbpool-"+name+ " release pool");
        Iterator it = conpool.iterator();
        while (it.hasNext()) {
            Connection con = (Connection) it.next();
            try {
                con.close();
                vulogger.info(name + "Closed connection for pool");

            }
            catch (SQLException e) {
                vulogger.info(name + "Can't close connection for pool ");
            }
            it.remove();
        }

    }

    @Override
    public void finalize(){
        releasePool();
        try {
            super.finalize();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public int incrCheckedOut() {
        int v;
        do {
            v = checkedOut.get();
        }
         while (!checkedOut.compareAndSet(v, v + 1));
        return v + 1;
    }

    public int decrCheckedOut() {
        int v;
        do {
            v = checkedOut.get();
        }
         while (!checkedOut.compareAndSet(v, v - 1));
        return v - 1;
    }
}
