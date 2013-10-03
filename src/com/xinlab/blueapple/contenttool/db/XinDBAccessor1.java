//
// Copyright XinLab Inc (blueapple.mobi) 2006-2008
// Proprietary and confidential
// All rights reserved
//
// $Id: XinDBAccessor1.java,v 1.5 2012/09/27 22:07:26 sbho Exp $
//
package com.xinlab.blueapple.contenttool.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import com.xinlab.blueapple.contenttool.perf.Log007;
import com.xinlab.blueapple.contenttool.perf.PerfMon;

public abstract class XinDBAccessor1 extends XinDBAccessor{

    private static final Logger  vulogger = Logger.getLogger(XinDBAccessor1.class);
    public static AtomicInteger THREAD_COUNT = new AtomicInteger(0);
    
    private Connection con = null;
    private Statement stmt = null;
    private PreparedStatement pstmt = null;
    protected DBConPool mypool = null;

    static private boolean ALLOWPRINT = false;

//    protected int MAXPOOLSIZE = 10;
//    protected String name = "BA";

    private static int MIN_REPORT_DUR = 300;
    

    public void close(boolean commit) {
       if (stmt != null)
        try {
            stmt.close();
            stmt=null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (pstmt != null)
            try {
                pstmt.close();
                pstmt=null;
            } catch (SQLException e) {
                e.printStackTrace();
            }

        if (con != null) {
            //if (commit) con.commit();
            mypool.releaseConnection(con);
        }
    }

    //
    // basic db accessor api
    //
    //
    // acccording to faq, mysql will close the connection after 8 hours
    // and it seems that the con.isClosed() returns true in this case
    // we have to catch the exception throw by executeQuery(), executeUpdate(), excute()
    // and check the "error code" (sqlState), 08S01 for connection error and 41000 for deadlock
    // we might want to catch the exception in a loop instead of just do one more time
    // in the catch block
    // see http://dev.mysql.com/doc/connector/j/en/index.html#id2488718
    //
    public DBResult executeQuery(String sql) throws SQLException{
        return executeQuery(sql, true);
    }

    public DBResult executeQuery(String sql, boolean printSQL) throws SQLException{
        if (printSQL && ALLOWPRINT) vulogger.info(sql);
        long starttime = System.currentTimeMillis();
        try{
        	THREAD_COUNT.incrementAndGet();
            con = mypool.getConnection(500);
            Log007.logWapDc("con", starttime, (int)(System.currentTimeMillis()-starttime));
            stmt = con.createStatement();
            ResultSet result = stmt.executeQuery(sql);
            return new DBResult1(result);

        } catch (SQLException e){

            String sqlState = e.getSQLState();
            if ("08S01".equals(sqlState) || "41000".equals(sqlState)) {
                vulogger.error("sql exception caught, sqlstate="+sqlState);
                try {
                    con = mypool.getConnection(true);
                    stmt = con.createStatement();
                    ResultSet result = stmt.executeQuery(sql);
                    return new DBResult1(result);
                } catch (SQLException e1) {
                    //e1.printStackTrace();
                    throw e1;
                }
            }
            else
                throw e;
        }finally{
            THREAD_COUNT.decrementAndGet();
            long querytime = System.currentTimeMillis()-starttime;
            PerfMon.update(PerfMon.DB, querytime);
            if (querytime>MIN_REPORT_DUR)
                vulogger.warn("dbquery "+ sql+" finished in:"+querytime+"ms");
        }
        
    }

    public int executeUpdate(String sql) throws SQLException {
        vulogger.info(sql);
        long starttime = System.currentTimeMillis();
        try
        {
        	THREAD_COUNT.incrementAndGet();
            con = mypool.getConnection(500);
            Log007.logWapDc("con", starttime, (int)(System.currentTimeMillis()-starttime));
            stmt = con.createStatement();
            int result = stmt.executeUpdate(sql);
            return result;
        } catch (SQLException e){
            String sqlState = e.getSQLState();
            if ("08S01".equals(sqlState) || "41000".equals(sqlState)) {
                vulogger.error("sql exception caught, sqlstate="+sqlState);
                try {
                    con = mypool.getConnection(true);
                    stmt = con.createStatement();
                    int result = stmt.executeUpdate(sql);
                    return result;
                } catch (SQLException e1) {
                    //e1.printStackTrace();
                    throw e1;
                }
            }
            else
                throw e;
        }finally{
            THREAD_COUNT.decrementAndGet();
            long querytime = System.currentTimeMillis()-starttime;
            PerfMon.update(PerfMon.DB, querytime);
            if (querytime>MIN_REPORT_DUR)
                vulogger.warn("dbquery "+ sql+" finished in:"+querytime+"ms");
        }
    }

    public DBResult doPreparedQuery(String sql,String ...bindValues) throws SQLException{
        return doPreparedQuery(sql, true,bindValues);
    }  
    
    public DBResult doPreparedQuery(String sql, boolean printSQL ,String... bindValues ) throws SQLException{
        if (printSQL && ALLOWPRINT) vulogger.info(sql);
        long starttime = System.currentTimeMillis();
        try{
        	THREAD_COUNT.incrementAndGet();
            con = mypool.getConnection(500);
            Log007.logWapDc("con", starttime, (int)(System.currentTimeMillis()-starttime));
            pstmt = con.prepareStatement(sql);            
            if (bindValues != null) {                
                for (int count = 0; count < bindValues.length; count++) {                	
                    pstmt.setString(count + 1, bindValues[count]);                    
                }
            }          
            ResultSet result = pstmt.executeQuery();
            return new DBResult1(result);

        } catch (SQLException e){

            String sqlState = e.getSQLState();
            if ("08S01".equals(sqlState) || "41000".equals(sqlState)) {
                vulogger.error("sql exception caught, sqlstate="+sqlState);
                try {
                    con = mypool.getConnection(true);
                    pstmt = con.prepareStatement(sql);
                    if (bindValues != null) {                
                        for (int count = 0; count < bindValues.length; count++) {
                            pstmt.setString(count + 1, bindValues[count]);                    
                        }
                    }                  
                    ResultSet result = pstmt.executeQuery();
                    return new DBResult1(result);
                } catch (SQLException e1) {
                    //e1.printStackTrace();
                    throw e1;
                }
            }
            else
                throw e;
        }finally{
            THREAD_COUNT.decrementAndGet();
            long querytime = System.currentTimeMillis()-starttime;
            PerfMon.update(PerfMon.DB, querytime);
            if (querytime>MIN_REPORT_DUR)
                vulogger.warn("dbquery "+ sql+" finished in:"+querytime+"ms");
        }        
    }
    
    
    
    public int executePreparedUpdate(String sql, String... bindValues) throws SQLException {
            vulogger.info(sql);
            long starttime = System.currentTimeMillis();
            try {
                THREAD_COUNT.incrementAndGet();
                con = mypool.getConnection(500);
                Log007.logWapDc("con", starttime, (int) (System.currentTimeMillis() - starttime));
                //Instantiate prepared statement object
                pstmt = con.prepareStatement(sql);
                if (bindValues != null) {
                    //Set/bind the objects
                    for (int count = 0; count < bindValues.length; count++) {
                        pstmt.setString(count + 1, bindValues[count]);
                        //consider setObject(int, Object value)
                    }
                }
                //Call the sql
                int result = pstmt.executeUpdate();
                return result;
            } catch (SQLException e) {
                String sqlState = e.getSQLState();
                if ("08S01".equals(sqlState) || "41000".equals(sqlState)) {
                    vulogger.error("sql exception caught, sqlstate=" + sqlState);
                    try {
                        con = mypool.getConnection(true);
                        pstmt = con.prepareStatement(sql);
                        if (bindValues != null) {
                            for (int count = 0; count < bindValues.length; count++) {
                                pstmt.setString(count + 1, bindValues[count]);
                            }
                        }
                        int result = pstmt.executeUpdate();
                        return result;
                    } catch (SQLException e1) {
                        // e1.printStackTrace();
                        throw e1;
                    }
                } else
                    throw e;
            } finally {
                THREAD_COUNT.decrementAndGet();
                long querytime = System.currentTimeMillis() - starttime;
                PerfMon.update(PerfMon.DB, querytime);
                if (querytime > MIN_REPORT_DUR)
                    vulogger.warn("dbquery " + sql + " finished in:" + querytime + "ms");
            }
        }
   
    public int executePreparedUpdateAutoIncrement(String sql, String... bindValues)throws Exception {
        vulogger.info(sql);
        long starttime = System.currentTimeMillis();
        try {
            THREAD_COUNT.incrementAndGet();
            con = mypool.getConnection(500);
            Log007.logWapDc("con", starttime, (int) (System.currentTimeMillis() - starttime));
            //Instantiate prepared statement object
            pstmt = con.prepareStatement(sql);
            if (bindValues != null) {
                //Set/bind the objects
                for (int count = 0; count < bindValues.length; count++) {
                    pstmt.setString(count + 1, bindValues[count]);
                    //consider setObject(int, Object value)
                }
            }
            //Call the sql
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next())
                return rs.getInt(1);
            return -1;
        } catch (SQLException e) {
            String sqlState = e.getSQLState();
            if ("08S01".equals(sqlState) || "41000".equals(sqlState)) {
                vulogger.error("sql exception caught, sqlstate=" + sqlState);
                try {
                    con = mypool.getConnection(true);
                    pstmt = con.prepareStatement(sql);
                    if (bindValues != null) {
                        for (int count = 0; count < bindValues.length; count++) {
                            pstmt.setString(count + 1, bindValues[count]);
                        }
                    }
                    pstmt.executeUpdate();
                    ResultSet rs = pstmt.getGeneratedKeys();
                    if (rs.next())
                        return rs.getInt(1);
                    return -1;
                } catch (SQLException e1) {
                    // e1.printStackTrace();
                    throw e1;
                }
            } else
                throw e;
        } finally {
            THREAD_COUNT.decrementAndGet();
            long querytime = System.currentTimeMillis() - starttime;
            PerfMon.update(PerfMon.DB, querytime);
            if (querytime > MIN_REPORT_DUR)
                vulogger.warn("dbquery " + sql + " finished in:" + querytime + "ms");
        }
    }
    public long executePreparedUpdateAutoIncrementLong(String sql, String... bindValues)throws Exception {
        vulogger.info(sql);
        long starttime = System.currentTimeMillis();
        try {
            THREAD_COUNT.incrementAndGet();
            con = mypool.getConnection(500);
            Log007.logWapDc("con", starttime, (int) (System.currentTimeMillis() - starttime));
            //Instantiate prepared statement object
            pstmt = con.prepareStatement(sql);
            if (bindValues != null) {
                //Set/bind the objects
                for (int count = 0; count < bindValues.length; count++) {
                    pstmt.setString(count + 1, bindValues[count]);
                    //consider setObject(int, Object value)
                }
            }
            //Call the sql
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next())
                return rs.getLong(1);
            return -1;
        } catch (SQLException e) {
            String sqlState = e.getSQLState();
            if ("08S01".equals(sqlState) || "41000".equals(sqlState)) {
                vulogger.error("sql exception caught, sqlstate=" + sqlState);
                try {
                    con = mypool.getConnection(true);
                    pstmt = con.prepareStatement(sql);
                    if (bindValues != null) {
                        for (int count = 0; count < bindValues.length; count++) {
                            pstmt.setString(count + 1, bindValues[count]);
                        }
                    }
                    pstmt.executeUpdate();
                    ResultSet rs = pstmt.getGeneratedKeys();
                    if (rs.next())
                        return rs.getLong(1);
                    return -1;
                } catch (SQLException e1) {
                    // e1.printStackTrace();
                    throw e1;
                }
            } else
                throw e;
        } finally {
            THREAD_COUNT.decrementAndGet();
            long querytime = System.currentTimeMillis() - starttime;
            PerfMon.update(PerfMon.DB, querytime);
            if (querytime > MIN_REPORT_DUR)
                vulogger.warn("dbquery " + sql + " finished in:" + querytime + "ms");
        }
    }       
    public boolean preparedExecute(String sql, String... bindValues) throws SQLException
    {
        vulogger.info(sql);
        long starttime = System.currentTimeMillis();
        try
        {
        	THREAD_COUNT.incrementAndGet();
            con = mypool.getConnection(500);
            Log007.logWapDc("con", starttime, (int)(System.currentTimeMillis()-starttime));
            pstmt = con.prepareStatement(sql);
            if (bindValues != null) {
                for (int count = 0; count < bindValues.length; count++) {
                    pstmt.setString(count + 1, bindValues[count]);
                }
            }
            boolean result = pstmt.execute(sql);
            return result;
        } catch (SQLException e)
        {
            String sqlState = e.getSQLState();
            if ("08S01".equals(sqlState) || "41000".equals(sqlState)) {
                vulogger.error("sql exception caught, sqlstate="+sqlState);
                try {
                    con = mypool.getConnection(true);
                    pstmt = con.prepareStatement(sql);
                    if (bindValues != null) {
                        for (int count = 0; count < bindValues.length; count++) {
                            pstmt.setString(count + 1, bindValues[count]);
                        }
                    }
                    boolean result = pstmt.execute(sql);
                    return result;
                } catch (SQLException e1) {
                    //e1.printStackTrace();
                    throw e1;
                }
            }
            else
                throw e;
        }finally{
            THREAD_COUNT.decrementAndGet();
            long querytime = System.currentTimeMillis()-starttime;
            PerfMon.update(PerfMon.DB, querytime);
            if (querytime>MIN_REPORT_DUR)
                vulogger.warn("dbquery "+ sql+" finished in:"+querytime+"ms");
        }
    }   
    
    public int executeUpdateAutoIncrement(String sql) throws SQLException
    {
        vulogger.info(sql);
        long starttime = System.currentTimeMillis();
        try
        {
        	THREAD_COUNT.incrementAndGet();
            con = mypool.getConnection(500);
            Log007.logWapDc("con", starttime, (int)(System.currentTimeMillis()-starttime));
            stmt = con.createStatement();
            stmt.executeUpdate(sql);
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next())
                return rs.getInt(1);
            return -1;
        } catch (SQLException e)
        {
            String sqlState = e.getSQLState();
            if ("08S01".equals(sqlState) || "41000".equals(sqlState)) {
                vulogger.error("sql exception caught, sqlstate="+sqlState+", "+sql);
                try {
                    con = mypool.getConnection(false);
                    stmt = con.createStatement();
                    stmt.executeUpdate(sql);
                    ResultSet rs = stmt.getGeneratedKeys();
                    if (rs.next())
                        return rs.getInt(1);
                    return -1;
                } catch (SQLException e1) {
                    //e1.printStackTrace();
                    throw e1;
                }
            }
            else
                throw e;
        }finally{
            THREAD_COUNT.decrementAndGet();
            long querytime = System.currentTimeMillis()-starttime;
            PerfMon.update(PerfMon.DB, querytime);
            if (querytime>MIN_REPORT_DUR)
                vulogger.warn("dbquery "+ sql+" finished in:"+querytime+"ms");
        }
    }    
    public long executeUpdateAutoIncrementLong(String sql) throws SQLException
    {
        vulogger.info(sql);
        long starttime = System.currentTimeMillis();
        try
        {
            THREAD_COUNT.incrementAndGet();
            con = mypool.getConnection(500);
            Log007.logWapDc("con", starttime, (int)(System.currentTimeMillis()-starttime));
            stmt = con.createStatement();
            stmt.executeUpdate(sql);
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next())
                return rs.getLong(1);
            return -1;
        } catch (SQLException e)
        {
            String sqlState = e.getSQLState();
            if ("08S01".equals(sqlState) || "41000".equals(sqlState)) {
                vulogger.error("sql exception caught, sqlstate="+sqlState+", "+sql);
                try {
                    con = mypool.getConnection(false);
                    stmt = con.createStatement();
                    stmt.executeUpdate(sql);
                    ResultSet rs = stmt.getGeneratedKeys();
                    if (rs.next())
                        return rs.getLong(1);
                    return -1;
                } catch (SQLException e1) {
                    //e1.printStackTrace();
                    throw e1;
                }
            }
            else
                throw e;
        }finally{
            THREAD_COUNT.decrementAndGet();
            long querytime = System.currentTimeMillis()-starttime;
            PerfMon.update(PerfMon.DB, querytime);
            if (querytime>MIN_REPORT_DUR)
                vulogger.warn("dbquery "+ sql+" finished in:"+querytime+"ms");
        }
    }        
    public boolean execute(String sql) throws SQLException
    {
        vulogger.info(sql);
        long starttime = System.currentTimeMillis();
        try
        {
        	THREAD_COUNT.incrementAndGet();
            con = mypool.getConnection(500);
            Log007.logWapDc("con", starttime, (int)(System.currentTimeMillis()-starttime));
            stmt = con.createStatement();
            boolean result = stmt.execute(sql);
            return result;
        } catch (SQLException e)
        {
            String sqlState = e.getSQLState();
            if ("08S01".equals(sqlState) || "41000".equals(sqlState)) {
                vulogger.error("sql exception caught, sqlstate="+sqlState);
                try {
                    con = mypool.getConnection(true);
                    stmt = con.createStatement();
                    boolean result = stmt.execute(sql);
                    return result;
                } catch (SQLException e1) {
                    //e1.printStackTrace();
                    throw e1;
                }
            }
            else
                throw e;
        }finally{
            THREAD_COUNT.decrementAndGet();
            long querytime = System.currentTimeMillis()-starttime;
            PerfMon.update(PerfMon.DB, querytime);
            if (querytime>MIN_REPORT_DUR)
                vulogger.warn("dbquery "+ sql+" finished in:"+querytime+"ms");
        }
    }
    
}



