//
// Copyright XinLab Inc (blueapple.mobi) 2006-2008
// Proprietary and confidential
// All rights reserved
//
// $Id: XinDBAccessor.java,v 1.13 2012/09/27 22:38:30 sbho Exp $
//
package com.xinlab.blueapple.contenttool.db;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.log4j.Logger;

public abstract class XinDBAccessor {

	private static final Logger  vulogger = Logger.getLogger(XinDBAccessor.class);

    public static final int BLUEAPPLE = 0;
    public static final int CONTENT = 1;
    public static final int DEVICEDB = 2;
    public static final int IPLOOKUP = 3;
    public static final int BASTATS = 4;
    public static final int RELATEDVIDEO = 5;

    public abstract DBResult executeQuery(String sql) throws Exception;
    public abstract DBResult executeQuery(String sql, boolean printSQL) throws Exception;   
    public abstract int executeUpdate(String sql) throws Exception;
    public abstract int executeUpdateAutoIncrement(String sql) throws Exception;
    public abstract boolean execute(String sql) throws Exception;
    public abstract void close(boolean commit) throws Exception;



    //
    // helper function
    //
    public final String[][] getTable(String sql){
        return getTable(sql, true);
    }
    public  DBResult doPreparedQuery(String sql,String... bindValues )throws Exception {throw new UnsupportedOperationException();}
    
    public  DBResult doPreparedQuery(String sql, boolean printSQL ,String... bindValues) throws Exception  {throw new UnsupportedOperationException();}
    
    public int executePreparedUpdate(String sql, String... bindValues) throws Exception {throw new UnsupportedOperationException();}

    public boolean doPreparedUpdate(String sql, String... bindValues) {
        int value = 0;
        try {
            value = executePreparedUpdate(sql, bindValues);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                close(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return (value > 0);// for "on duplicate key update" case, it will return 2, so can not do "value==1"
    }
    
    public int executePreparedUpdateAutoIncrement(String sql, String... bindValues) throws Exception {throw new UnsupportedOperationException();}
    
    public int doPreparedInsertAutoIncrement(String sql,String... bindValues){
    	int result=0;
    	try {
			result=executePreparedUpdateAutoIncrement(sql,bindValues);
		} catch (Exception e) {			
			e.printStackTrace();
		}finally{
			try{
				close(true);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
    	return result;
    }
    
    public long executePreparedUpdateAutoIncrementLong(String sql, String... bindValues) throws Exception {throw new UnsupportedOperationException();}
    
    public long doPreparedInsertAutoIncrementLong(String sql,String... bindValues){
        long result=0;
        try {
            result=executePreparedUpdateAutoIncrementLong(sql,bindValues);
        } catch (Exception e) {         
            e.printStackTrace();
        }finally{
            try{
                close(true);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return result;
    }
    
    
    public final String[][] getTable(String sql, boolean printSQL){
        DBResult rs = null;
        try {
            ArrayList<String[]> list = new ArrayList<String[]>();
            rs = executeQuery(sql, printSQL);
            int nCol = rs.getColumnCount();
            while ( rs.next() ) {
                String[] array = new String[nCol];
                for (int i=0; i<array.length; i++)
                    array[i]=rs.getString(i+1);
                list.add(array);
//                vulogger.error("Array: #:"+Arrays.toString(array));
            }

            return list.toArray(new String[0][]);
        } catch (Exception e) {
        	vulogger.error("SQL Exception: #1" + e.getMessage()+", sql:"+sql);
            e.printStackTrace();
        } finally {
            if (rs != null)
                rs.close();
            try {
                close(true);
            } catch (Exception e) {
            	vulogger.error("SQL Exception: #2" + e.getMessage()+", sql:"+sql);
                e.printStackTrace();
            }
        }
        return null;
    }
    public final String[] getColumn(String sql){
        return getColumn(sql, true);
    }
    public final String[] getColumn(String sql,boolean printSQL){
        DBResult rs = null;
        ArrayList<String> list = new ArrayList<String>();
        try {
            rs = executeQuery(sql, printSQL);
            while (rs.next())
                list.add(rs.getString(1));
            return list.toArray(new String[0]);
        } catch (Exception e) {
        	vulogger.error("SQL Exception: #3" + e.getMessage()+", sql:"+sql);
            e.printStackTrace();
        } finally {
            if (rs != null)
                rs.close();
            try {
                close(true);
            } catch (Exception e) {
            	vulogger.error("SQL Exception: #4" + e.getMessage()+", sql:"+sql);
                e.printStackTrace();
            }
        }
        return null;
    }
    public final String[] getRow(String sql){
        return getRow(sql, true);
    }
    public final String[] getRow(String sql,boolean printSQL){
        DBResult rs = null;
        try {
            rs = executeQuery(sql, printSQL);
            int nCol = rs.getColumnCount();
            if (rs.next()) {
                String[] array = new String[nCol];
                for (int i=0; i<array.length; i++)
                    array[i]=rs.getString(i+1);
                return array;
            }
        } catch (Exception e) {
        	vulogger.error("SQL Exception: #5" + e.getMessage()+", sql:"+sql);
            e.printStackTrace();
        } finally {
            if (rs != null)
                rs.close();
            try {
                close(true);
            } catch (Exception e) {
            	vulogger.error("SQL Exception: #6" + e.getMessage()+", sql:"+sql);
                e.printStackTrace();
            }
        }
        return null;
    }
    public final String getString(String sql){
        return getString(sql, true);
    }
    public final String getString(String sql,boolean printSQL){
        DBResult rs = null;
        try {
            rs = executeQuery(sql, printSQL);
            if (rs.next())
                return rs.getString(1);
        } catch (Exception e) {
        	vulogger.error("SQL Exception: #7" + e.getMessage()+", sql:"+sql);
            e.printStackTrace();
        } finally {
            if (rs != null)
                rs.close();
            try {
                close(true);
            } catch (Exception e) {
            	vulogger.error("SQL Exception: #8" + e.getMessage()+", sql:"+sql);
                e.printStackTrace();
            }
        }
        return null;
    }
    public final int getInt(String sql){
        return getInt(sql, true);
    }
    public final int getInt(String sql,boolean printSQL){
        DBResult rs = null;
        try {
            rs = executeQuery(sql, printSQL);
            if (rs.next())
                return rs.getInt(1);
        } catch (Exception e) {
        	vulogger.error("SQL Exception: #9" + e.getMessage()+", sql:"+sql);
            e.printStackTrace();
        } finally {
            if (rs != null)
                rs.close();
            try {
                close(true);
            } catch (Exception e) {
            	vulogger.error("SQL Exception: #10" + e.getMessage()+", sql:"+sql);
                e.printStackTrace();
            }
        }
        return -1;
    }
    public final boolean exists(String sql){
        return exists(sql,true);
    }
    public final boolean exists(String sql,boolean printSQL){
        DBResult rs = null;
        try {
            rs = executeQuery(sql, printSQL);
            return rs.next();
        } catch (Exception e) {
        	vulogger.error("SQL Exception: #11" + e.getMessage()+", sql:"+sql);
            e.printStackTrace();
        } finally {
            if (rs != null)
                rs.close();
            try {
                close(true);
            } catch (Exception e) {
            	vulogger.error("SQL Exception: #12" + e.getMessage()+", sql:"+sql);
                e.printStackTrace();
            }
        }
        return false;
    }
    public final boolean doUpdate(String sql)
    {
        int value = 0;
        try {
            value = executeUpdate(sql);
        } catch (Exception e) {
        	vulogger.error("SQL Exception: #13" + e.getMessage()+", sql:"+sql);
            e.printStackTrace();
        }finally{
            try {
                close(true);
            } catch (Exception e) {
            	vulogger.error("SQL Exception: #14" + e.getMessage()+", sql:"+sql);
                e.printStackTrace();
            }
        }
        return (value>0);// for "on duplicate key update" case, it will return 2, so can not do "value==1"
    }
    public final int doUpdateMultiple(String sql)
    {
        int value = -1;
        try {
            value = executeUpdate(sql);
        } catch (Exception e) {
        	vulogger.error("SQL Exception: #15" + e.getMessage()+", sql:"+sql);
            e.printStackTrace();
        }finally{
            try {
                close(true);
            } catch (Exception e) {
            	vulogger.error("SQL Exception: #16" + e.getMessage()+", sql:"+sql);
                e.printStackTrace();
            }
        }
        return value;
    }
    public final int insertAutoIncrement(String sql)
    {
        int value = -1;
        try {
            value = executeUpdateAutoIncrement(sql);
        } catch (Exception e) {
        	vulogger.error("SQL Exception: #17" + e.getMessage()+", sql:"+sql);
            e.printStackTrace();
        }finally{
            try {
                close(true);
            } catch (Exception e) {
            	vulogger.error("SQL Exception: #18" + e.getMessage()+", sql:"+sql);
                e.printStackTrace();
            }
        }
        return value;
    }

    public final int insertAutoIncr(String sql) throws Exception
    {
        int value = -1;
        try {
            value = executeUpdateAutoIncrement(sql);
        } catch (Exception e) {
        	vulogger.error("SQL Exception: #19" + e.getMessage()+", sql:"+sql);
            e.printStackTrace();
            // cleanup the db resource here
            // throw the exception back to the thread
            // basically defer the exception handling to upper applicaton
            throw e;
        }finally{
            try {
                close(true);
            } catch (Exception e) {
            	vulogger.error("SQL Exception: #20" + e.getMessage()+", sql:"+sql);
                e.printStackTrace();
            }
        }
        return value;
    }

    public final boolean check(){
        DBResult rs = null;
        String sql = "SELECT 1";
        try {
            rs = executeQuery(sql, false);
            if (rs.next())
                return 1==rs.getInt(1);
        } catch (Exception e) {
        	vulogger.error("SQL Exception: #21" + e.getMessage()+", sql:"+sql);
            e.printStackTrace();
        } finally {
            if (rs != null)
                rs.close();
            try {
                close(true);
            } catch (Exception e) {
            	vulogger.error("SQL Exception: #22" + e.getMessage()+", sql:"+sql);
                e.printStackTrace();
            }
        }
        return false;
    }


    
    public final String[][] getPreparedTable(String sql,String... bindValues){
        return getPreparedTable(sql, true,bindValues);
    }    
    public final String[][] getPreparedTable(String sql, boolean printSQL,String... bindValues){
        DBResult rs = null;
        try {
            ArrayList<String[]> list = new ArrayList<String[]>();
            rs = doPreparedQuery(sql, printSQL,bindValues);
            int nCol = rs.getColumnCount();
            while ( rs.next() ) {
                String[] array = new String[nCol];
                for (int i=0; i<array.length; i++)
                    array[i]=rs.getString(i+1);
                list.add(array);
                vulogger.error("Array: #:"+Arrays.toString(array));
            }

            return list.toArray(new String[0][]);
        } catch (Exception e) {
        	vulogger.error("SQL Exception: #1" + e.getMessage()+", sql:"+sql);
            e.printStackTrace();
        } finally {
            if (rs != null)
                rs.close();
            try {
                close(true);
            } catch (Exception e) {
            	vulogger.error("SQL Exception: #2" + e.getMessage()+", sql:"+sql);
                e.printStackTrace();
            }
        }
        return null;
    }
    public final String[] getPreparedColumn(String sql,String... bindValues){
        return getPreparedColumn(sql, true,bindValues);
    }
    public final String[] getPreparedColumn(String sql,boolean printSQL,String... bindValues ){
        DBResult rs = null;
        ArrayList<String> list = new ArrayList<String>();
        try {
            rs = doPreparedQuery(sql, printSQL,bindValues);
            while (rs.next())
                list.add(rs.getString(1));
            return list.toArray(new String[0]);
        } catch (Exception e) {
        	vulogger.error("SQL Exception: #3" + e.getMessage()+", sql:"+sql);
            e.printStackTrace();
        } finally {
            if (rs != null)
                rs.close();
            try {
                close(true);
            } catch (Exception e) {
            	vulogger.error("SQL Exception: #4" + e.getMessage()+", sql:"+sql);
                e.printStackTrace();
            }
        }
        return null;
    }
    public final String[] getPreparedRow(String sql,String... bindValues ){
        return getPreparedRow(sql, true,bindValues);
    }
    public final String[] getPreparedRow(String sql,boolean printSQL,String... bindValues){
        DBResult rs = null;
        try {
            rs = doPreparedQuery(sql, printSQL,bindValues);
            int nCol = rs.getColumnCount();
            if (rs.next()) {
                String[] array = new String[nCol];
                for (int i=0; i<array.length; i++)
                    array[i]=rs.getString(i+1);
                return array;
            }
        } catch (Exception e) {
        	vulogger.error("SQL Exception: #5" + e.getMessage()+", sql:"+sql);
            e.printStackTrace();
        } finally {
            if (rs != null)
                rs.close();
            try {
                close(true);
            } catch (Exception e) {
            	vulogger.error("SQL Exception: #6" + e.getMessage()+", sql:"+sql);
                e.printStackTrace();
            }
        }
        return null;
    }
    
    public final String getPreparedString(String sql,String... bindValues){
        return getPreparedString(sql, true,bindValues);
    }
    public final String getPreparedString(String sql,boolean printSQL,String... bindValues){
        DBResult rs = null;
        try {
            rs = doPreparedQuery(sql, printSQL,bindValues);
            if (rs.next())
                return rs.getString(1);
        } catch (Exception e) {
        	vulogger.error("SQL Exception: #7" + e.getMessage()+", sql:"+sql);
            e.printStackTrace();
        } finally {
            if (rs != null)
                rs.close();
            try {
                close(true);
            } catch (Exception e) {
            	vulogger.error("SQL Exception: #8" + e.getMessage()+", sql:"+sql);
                e.printStackTrace();
            }
        }
        return null;
    }
    public final int getPreparedInt(String sql,String... bindValues){
        return getPreparedInt(sql, true,bindValues);
    }
    public final int getPreparedInt(String sql,boolean printSQL,String... bindValues){
        DBResult rs = null;
        try {
            rs = doPreparedQuery(sql, printSQL,bindValues);
            if (rs.next())
                return rs.getInt(1);
        } catch (Exception e) {
        	vulogger.error("SQL Exception: #9" + e.getMessage()+", sql:"+sql);
            e.printStackTrace();
        } finally {
            if (rs != null)
                rs.close();
            try {
                close(true);
            } catch (Exception e) {
            	vulogger.error("SQL Exception: #10" + e.getMessage()+", sql:"+sql);
                e.printStackTrace();
            }
        }
        return -1;
    }
    public final boolean existsPrepared(String sql,String... bindValues){
        return existsPrepared(sql,true,bindValues);
    }
    public final boolean existsPrepared(String sql,boolean printSQL,String... bindValues){
        DBResult rs = null;
        try {
            rs = doPreparedQuery(sql, printSQL,bindValues);
            return rs.next();
        } catch (Exception e) {
        	vulogger.error("SQL Exception: #11" + e.getMessage()+", sql:"+sql);
            e.printStackTrace();
        } finally {
            if (rs != null)
                rs.close();
            try {
                close(true);
            } catch (Exception e) {
            	vulogger.error("SQL Exception: #12" + e.getMessage()+", sql:"+sql);
                e.printStackTrace();
            }
        }
        return false;
    }
    
    
    
    
    
    
    

   /* public final String[][] getTableMap(String sql, boolean printSQL){
    	DBResult rs = null;
        try {

            rs = executeQuery(sql, printSQL);
            int nCol = rs.getColumnCount();
            int nRow = rs.getRowCount();
            String[][] list = new String[nRow][nCol];
            while ( rs.next() ) {
                for (int i=0; i< nCol; i++)
                    list[rs.getInt(3)][i]=rs.getString(i+1);

                vulogger.error("Array: #:"+Arrays.toString(list));
            }

            return list;

        } catch (Exception e) {
        	vulogger.error("SQL Exception: #1" + e.getMessage()+", sql:"+sql);
            e.printStackTrace();
        } finally {
            if (rs != null)
                rs.close();
            try {
                close(true);
            } catch (Exception e) {
            	vulogger.error("SQL Exception: #2" + e.getMessage()+", sql:"+sql);
                e.printStackTrace();
            }
        }
        return null;
    }*/

    /*
    public final SiteCobrand getSiteCobrandRow(String sql,boolean printSQL){
        SiteCobrand sc = null;
        DBResult rs = null;
        try {
            rs = executeQuery(sql, printSQL);

            if (rs.next()) {
                sc = new SiteCobrand();
                sc.setLogo(rs.getBytes(1));
                sc.setImageTypeId(rs.getLong(2));
                return sc;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null)
                rs.close();
            try {
                close(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public XinSiteConfig getSiteConfig(String sql, boolean printSQL) {
        DBResult rs = null;
        try {
            rs = executeQuery(sql, printSQL);
            String str = null;
            if (rs.next()) {
                XinSiteConfig xsc = new XinSiteConfig();
                //xsc.setLogo(rs.getString(1));
                str = rs.getString(1);
                if (str != null && str.length() > 0) {
                    xsc.setBacklink(str);
                }
                xsc.setBacklinktext(rs.getString(2));
                str = rs.getString(3);
                if (str != null && str.length() > 0) {
                    xsc.setBackHomeUrl(str);
                }
                xsc.setBackHomeText(rs.getString(4));
                xsc.setShowMostview(rs.getInt(5) == 1);
                xsc.setShowSendToFriend(rs.getInt(6) == 1);
                xsc.setUsePersonalize(rs.getInt(7) == 1);
                xsc.setShowVideoFront(rs.getInt(8) == 1);
                xsc.setShowPoweredBy(rs.getInt(9) == 1);
                xsc.setLinkPoweredBy(rs.getInt(10) == 1);
                xsc.setDoStreaming(rs.getInt(11) == 1);
                xsc.setDoChunk(rs.getInt(12) == 1);
                xsc.setDoHires(rs.getInt(13) == 1);
                xsc.setAllowAdult(rs.getInt(14) == 1);
                xsc.setShowSearchBox(rs.getInt(15) == 1);
                xsc.setShowRecentlyViewed(rs.getInt(16) == 1);
                xsc.setShowReportProblem(rs.getInt(17) == 1);
                xsc.setShowHelp(rs.getInt(18) == 1);
                xsc.setShowSettings(rs.getInt(19) == 1);
                xsc.setShowPostRollLogo(rs.getInt(20) == 1);
                xsc.setAllowAdOnHome(rs.getInt(21) == 1);
                xsc.setAllowBlackBorder(rs.getInt(22) == 1);
                xsc.setEnableLogin(rs.getInt(23) == 1);
                xsc.setAllowAd(rs.getInt(24) == 1);
                xsc.setShowAd(rs.getInt(24) == 1 ? "am" : "");  // for backwards compatibility, need to be removed once ad engine is redesigned.
                //xsc.setCssText(rs.getString(25));
                xsc.setSearchDisplay(rs.getString(25));
                xsc.setAllowDownload(rs.getInt(26) == 1);
                xsc.setAllowVote(rs.getInt(27) == 1);
                xsc.setAllowRelated(rs.getInt(28) == 1);
                xsc.setAllowAddClip(rs.getInt(29) == 1);
                xsc.setAllowAddComment(rs.getInt(30) == 1);
                xsc.setAllowFlagVideo(rs.getInt(31) == 1);
                xsc.setAllowTwitter(rs.getInt(32) == 1);
                xsc.setEnableCopyright(rs.getInt(33) == 1);
                xsc.setShowAdsSponsor(rs.getInt(34) == 1);
                xsc.setShowVideoSource(rs.getInt(35) == 1);
                str = rs.getString(36);
                if (str == null) str = "";
                xsc.setSearchKeyword(str);
                xsc.setAllowVuLite(rs.getInt(37) == 1);
                xsc.setPostRollLogoName(rs.getInt(38));
                xsc.setLocale(LocaleService.getLocaleFromId(rs.getString(39)));
                return xsc;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null)
                rs.close();
            try {
                close(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }
    public List<SiteCobrand> getSiteCobrandRows(String sql, boolean printSQL) {
        ArrayList<SiteCobrand> sclist = new ArrayList<SiteCobrand>();
        SiteCobrand sc = null;
        DBResult rs = null;
        try {
            rs = executeQuery(sql, printSQL);
            // SELECT site_cobrand_id, logo, logo_image_type_id, style_sheet_text, screen_type_id FROM site_cobrand inner join partner_site_cobrand using (site_cobrand_id) where partner_site_id=
            while (rs.next()) {
                sc = new SiteCobrand();
                sc.setSiteCobrandId(rs.getLong(1));
                sc.setLogo(rs.getBytes(2));
                sc.setImageTypeId(rs.getLong(3));
                sc.setStyleSheetText(rs.getString(4));
                sc.setScreenType(ScreenType.fromInt(rs.getInt(5)));
                sclist.add(sc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null)
                rs.close();
            try {
                close(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sclist;
    }

    public List<SiteCobrand> getSiteCobrandIdRows(String sql, boolean printSQL) {
        ArrayList<SiteCobrand> sclist = new ArrayList<SiteCobrand>();
        SiteCobrand sc = null;
        DBResult rs = null;
        try {
            rs = executeQuery(sql, printSQL);
            // SELECT site_cobrand_id, screen_type_id FROM site_cobrand inner join partner_site_cobrand using (site_cobrand_id) where partner_site_id=
            while (rs.next()) {
                sc = new SiteCobrand();
                sc.setSiteCobrandId(rs.getLong(1));
                sc.setScreenType(ScreenType.fromInt(rs.getInt(2)));
                sc.setLogoWidth(rs.getString(3));
                sc.setLogoHeight(rs.getString(4));
                sc.setImageTypeId(rs.getLong(5));
                ImageType it = ImageType.fromInt((int)sc.getImageTypeId());
                sc.setLogoExt(it.getExt());
                sclist.add(sc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null)
                rs.close();
            try {
                close(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sclist;
    }
*/
}



