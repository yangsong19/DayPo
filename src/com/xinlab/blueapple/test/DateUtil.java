package com.xinlab.blueapple.test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    private static final long ONE_MINUTE = 60;

    private static final long ONE_HOUR = 3600;

    private static final long ONE_DAY = 86400;

    private static final long ONE_MONTH = 2592000;

    private static final long ONE_YEAR = 31104000;

    public static final Calendar calendar = Calendar.getInstance();

    public static Date getTime() {
        return calendar.getTime();
    }

    /**
     * @param pattern
     * @return 当前时间格式化后的字符串
     */
    public static String formatDate(String pattern) {
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(getTime());
    }

    /**
     * @param time
     * @param pattern
     * @return把指定格式的时间字符串解析为Date类型
     */
    public static Date parseDate(String time, String pattern) {
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = dateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 
     * @param date指定要计算的时间字符串
     * @param pattern指定的时间格式
     * @return指定的某个时刻距离现在多久
     * 
     */
    public static String fromToday(String date, String pattern) {
        Date target = parseDate(date, pattern);
        calendar.setTime(target);

        long time = target.getTime() / 1000;
        long now = new Date().getTime() / 1000;
        long ago = now - time;
        if (ago >= 0) {
            if (ago <= ONE_HOUR)
                return ago / ONE_MINUTE + "分钟前";
            else if (ago <= ONE_DAY)
                return ago / ONE_HOUR + "小时" + (ago % ONE_HOUR / ONE_MINUTE)
                        + "分钟前";
            else if (ago <= ONE_DAY * 2)
                return "昨天" + calendar.get(Calendar.HOUR_OF_DAY) + "点"
                        + calendar.get(Calendar.MINUTE) + "分";
            else if (ago <= ONE_DAY * 3)
                return "前天" + calendar.get(Calendar.HOUR_OF_DAY) + "点"
                        + calendar.get(Calendar.MINUTE) + "分";
            else if (ago <= ONE_MONTH) {
                long day = ago / ONE_DAY;
                return day + "天前" + calendar.get(Calendar.HOUR_OF_DAY) + "点"
                        + calendar.get(Calendar.MINUTE) + "分";
            } else if (ago <= ONE_YEAR) {
                long month = ago / ONE_MONTH;
                long day = ago % ONE_MONTH / ONE_DAY;
                return month + "个月" + day + "天前"
                        + calendar.get(Calendar.HOUR_OF_DAY) + "点"
                        + calendar.get(Calendar.MINUTE) + "分";
            } else {
                long year = ago / ONE_YEAR;
                int month = calendar.get(Calendar.MONTH) + 1;// JANUARY which is
                                                             // 0 so month+1
                return year + "年前" + month + "月" + calendar.get(Calendar.DATE)
                        + "日";
            }
        } else {
            long remain = Math.abs(ago);
            if (remain <= ONE_HOUR)
                return "只剩下" + remain / ONE_MINUTE + "分钟";
            else if (remain <= ONE_DAY)
                return "只剩下" + remain / ONE_HOUR + "小时"
                        + (remain % ONE_HOUR / ONE_MINUTE) + "分钟";
            else {
                long day = remain / ONE_DAY;
                long hour = remain % ONE_DAY / ONE_HOUR;
                long minute = remain % ONE_DAY % ONE_HOUR / ONE_MINUTE;
                return "只剩下" + day + "天" + hour + "小时" + minute + "分钟";
            }
        }
    }

    /**
     * 距离今天的绝对时间
     * 
     * @param date
     * @return
     */
    public static String toToday(String date, String pattern) {
        long time = parseDate(date, pattern).getTime() / 1000;
        long now = (new Date().getTime()) / 1000;
        long ago = now - time;
        if (ago <= ONE_HOUR)
            return ago / ONE_MINUTE + "分钟";
        else if (ago <= ONE_DAY)
            return ago / ONE_HOUR + "小时" + (ago % ONE_HOUR / ONE_MINUTE) + "分钟";
        else if (ago <= ONE_DAY * 2)
            return "昨天" + (ago - ONE_DAY) / ONE_HOUR + "点" + (ago - ONE_DAY)
                    % ONE_HOUR / ONE_MINUTE + "分";
        else if (ago <= ONE_DAY * 3) {
            long hour = ago - ONE_DAY * 2;
            return "前天" + hour / ONE_HOUR + "点" + hour % ONE_HOUR / ONE_MINUTE
                    + "分";
        } else if (ago <= ONE_MONTH) {
            long day = ago / ONE_DAY;
            long hour = ago % ONE_DAY / ONE_HOUR;
            long minute = ago % ONE_DAY % ONE_HOUR / ONE_MINUTE;
            return day + "天前" + hour + "点" + minute + "分";
        } else if (ago <= ONE_YEAR) {
            long month = ago / ONE_MONTH;
            long day = ago % ONE_MONTH / ONE_DAY;
            long hour = ago % ONE_MONTH % ONE_DAY / ONE_HOUR;
            long minute = ago % ONE_MONTH % ONE_DAY % ONE_HOUR / ONE_MINUTE;
            return month + "个月" + day + "天" + hour + "点" + minute + "分前";
        } else {
            long year = ago / ONE_YEAR;
            long month = ago % ONE_YEAR / ONE_MONTH;
            long day = ago % ONE_YEAR % ONE_MONTH / ONE_DAY;
            return year + "年前" + month + "月" + day + "天";
        }

    }

    public static void main(String[] args) {
//         System.out.println(calendar.getMinimalDaysInFirstWeek());
        // System.out.println(formatDate("yyyy:MM:dd HH:mm:ss"));
        // System.out.println(parseDate("2年12月29日 14时27分28秒","yyyy年MM月dd日 HH时mm分ss秒"));
        // System.out.println(fromToday("2013:12:29 16:0:0",
        // "yyyy:MM:dd HH:mm:ss"));
//         System.out.println(fromToday("2012:12:28 15:0:0","yyyy:MM:dd HH:mm:ss"));
//         Integer times = 0;
//         test(times);
//         System.out.println(times);
        ArrayList<String> msgs = new ArrayList<String>();
        msgTest(msgs);
        System.out.println(msgs.get(0));
    }
    public static void test(int count){
        count ++;
        System.out.println("test-count:"+count);
    }
    public static void msgTest(ArrayList<String> msg) {
        msg.add("test msg");
    }
}
