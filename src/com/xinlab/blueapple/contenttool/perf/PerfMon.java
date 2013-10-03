//
// Copyright XinLab Inc (blueapple.mobi) 2006-2008
// Proprietary and confidential
// All rights reserved
//
// $Id: PerfMon.java,v 1.1.1.1 2010/07/21 23:37:10 tli Exp $
//
package com.xinlab.blueapple.contenttool.perf;

import java.util.*;
import java.text.*;

public class PerfMon {

    public static final int DB = 0;
    public static final int P = 1;
    public static final int W = 2;
    public static final int S = 3;
    public static final int AD = 4;

    private static long lastmin;
    private static long last5min;
    private static long lasthr;

    private static int[][] data = new int[5][6];

    static {
        lastmin = last5min = lasthr = System.currentTimeMillis();
    }

    public synchronized static void update(int type, long udata){

        long cur = System.currentTimeMillis();
        long elapse_hr = cur - lasthr;
        long elapse_5min = cur - last5min;
        long elapse_min = cur - lastmin;

        if(elapse_hr > 60*60*1000){
            for(int i=0; i<5; i++){
                data[i][0] = data[i][1] = 0;
            }
            lasthr = cur;
        }

        if(elapse_5min > 5*60*1000){
            for(int i=0; i<5; i++){
                data[i][2] = data[i][3] = 0;
            }
            last5min = cur;
        }

        if(elapse_min > 60*1000){
            for(int i=0; i<5; i++){
                data[i][4] = data[i][5] = 0;
            }
            lastmin = cur;
        }

        data[type][0] ++;
        data[type][1] += udata; 
        data[type][2] ++;
        data[type][3] += udata; 
        data[type][4] ++;
        data[type][5] += udata; 
    }

    static void showData(StringBuffer sb, String name, int[] data){

        sb.append("<tr>");
        sb.append("<td>").append(name).append("</td>");
        sb.append("<td>").append(data[0]).append("</td>");
        sb.append("<td>").append(data[1]).append("</td>");
        sb.append("<td>").append((data[0]>0)?(data[1]/data[0]):0).append("</td>");
        sb.append("<td>").append(data[2]).append("</td>");
        sb.append("<td>").append(data[3]).append("</td>");
        sb.append("<td>").append((data[2]>0)?(data[3]/data[2]):0).append("</td>");
        sb.append("<td>").append(data[4]).append("</td>");
        sb.append("<td>").append(data[5]).append("</td>");
        sb.append("<td>").append((data[4]>0)?(data[5]/data[4]):0).append("</td>");
        sb.append("</tr>");
    }

    static String show(){

        StringBuffer sb = new StringBuffer();

        sb.append("<html>");
        sb.append("<table border=1>");
        sb.append("<tr>");
        sb.append("<td>component</td>");
        sb.append("<td>hourly #</td>");
        sb.append("<td>hourly total</td>");
        sb.append("<td>hourly avg</td>");
        sb.append("<td>5min #</td>");
        sb.append("<td>5min total</td>");
        sb.append("<td>5min avg</td>");
        sb.append("<td>1min #</td>");
        sb.append("<td>1min total</td>");
        sb.append("<td>1min avg</td>");
        sb.append("</tr>");

        showData(sb, "db", data[0]);
        showData(sb, "p", data[1]);
        showData(sb, "w", data[2]);
        showData(sb, "s", data[3]);
        showData(sb, "ad", data[4]);
        sb.append("</table>");
        sb.append("</html>");
 
        return sb.toString();
    }
}
