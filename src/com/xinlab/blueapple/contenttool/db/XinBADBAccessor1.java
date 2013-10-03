package com.xinlab.blueapple.contenttool.db;

import com.xinlab.blueapple.contenttool.common.XinConfig;

public class XinBADBAccessor1  extends XinDBAccessor1{

    private static DBConPool bapool = null;
    
    private static DBConPool getPool(){
        try {
            if (bapool==null)
                bapool = new DBConPool(XinConfig.dbUrl,XinConfig.dbUser,XinConfig.dbPassword,XinConfig.dbDriver,XinConfig.maxBaDBConnection);
            return bapool;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public XinBADBAccessor1(){
    
       mypool = getPool();
    }
    
}
