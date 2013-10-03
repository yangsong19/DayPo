//
// Copyright XinLab Inc (blueapple.mobi) 2006-2008
// Proprietary and confidential
// All rights reserved
//
// $Id: DBResult.java,v 1.2 2012/02/22 23:25:10 lakshmis Exp $
//
package com.xinlab.blueapple.contenttool.db;

public interface DBResult {

    public boolean next();
    public int getColumnCount();
    public int getRowCount();
    public String getString(int columnIndex);
    public byte[] getBytes(int columnIndex);
    public int getInt(int columnIndex);
    public long getLong(int columnIndex);
    public long getTimestamp(int columnIndex);
    public double getDouble(int columnIndex);
    public void close();
}
