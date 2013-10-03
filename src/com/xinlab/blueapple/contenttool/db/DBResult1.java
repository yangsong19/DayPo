//
// Copyright XinLab Inc (blueapple.mobi) 2006-2008
// Proprietary and confidential
// All rights reserved
//
// $Id: DBResult1.java,v 1.3 2012/02/22 23:25:10 lakshmis Exp $
//
package com.xinlab.blueapple.contenttool.db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;

public class DBResult1 implements DBResult {

    //
    // wrapping of Resultset Object
    //
    ResultSet rs = null;
    
    public DBResult1(ResultSet rs){
        this.rs = rs;
    }
    public boolean next() {
        try {
            if (rs!=null)
                return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public int getRowCount() {
        
        return 1;
    }
    public int getColumnCount(){
        try {
            if (rs!=null){
                ResultSetMetaData meta = rs.getMetaData();
                if (meta!=null)
                    return meta.getColumnCount();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public String getString(int columnIndex){
        try {
            if (rs!=null)
                return rs.getString(columnIndex);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public int getInt(int columnIndex){
        try {
            if (rs!=null)
                return rs.getInt(columnIndex);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public long getLong(int columnIndex){
        try {
            if (rs!=null)
                return rs.getLong(columnIndex);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0L;
    }
    public double getDouble(int columnIndex){
        try {
            if (rs!=null)
                return rs.getDouble(columnIndex);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0L;
    }
    public long getTimestamp(int columnIndex){
        try {
            if (rs!=null)
            	if (null != rs.getTimestamp(columnIndex)) {
            		return rs.getTimestamp(columnIndex).getTime();
            	}
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0L;
    }
    public void close(){
        if (rs!=null)
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        rs=null;
    }
    protected void finalize(){// throws Throwable {
        close();
    }
	public byte[] getBytes(int columnIndex) {
        try {
            if (rs!=null)
                return rs.getBytes(columnIndex);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
	}
}
