package com.alan.handsome.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * 类说明：
 * 作者：qiujialiu
 * 时间：2018/12/12
 */

public class CircleUtil {
    public static String getOnlineTime(long lastTime) {
        long currentTime = System.currentTimeMillis();
        if (currentTime < lastTime+(60000*10)) {
            return "5分钟前在线";
        }else if (currentTime < lastTime+(60000*60)) {
            return "1小时前在线";
        }else if (currentTime < lastTime+(60000*60*4)) {
            return "4小时前在线";
        }else if (currentTime < lastTime+(60000*60*24)) {
            return "1天前在线";
        }
        return "近期在线";
    }

    public static String getChatShowTime(long time) {
        long current = System.currentTimeMillis();
        if (time > current+(60000)) {
            return "";
        }else {
            int day = getBetweenDay(current, time);
            if (day == 0) {
                return UtilCodeEx.format(time, "HH:mm");
            } else if (day == 1) {
                return "昨天 " + UtilCodeEx.format(time, "HH:mm");
            } else if (day < 7) {
                return getDayWeek(new Date(time)) + " " + UtilCodeEx.format(time, "HH:mm");
            } else {
                return UtilCodeEx.format(time, "yyyy年MM月dd日 HH:mm");
            }
        }
    }

    public static String getDynamicShowTime(long time) {
        long current = System.currentTimeMillis();
        if (time > current+(60000)) {
            return "";
        }else {
            int day = getBetweenDay(current, time);
            if (day == 0) {
                long currentTime = System.currentTimeMillis();
                if (currentTime < time+(60000*5)) {
                    return "刚刚";
                }else if (currentTime < time+(60000*10)) {
                    return "5分钟前";
                }else if (currentTime < time+(60000*20)) {
                    return "10分钟前";
                }else if (currentTime < time+(60000*30)) {
                    return "20分钟前";
                }else if (currentTime < time+(60000*60)) {
                    return "半个小时前";
                }else if (currentTime < time+(60000*120)) {
                    return "1小时前";
                }
                return "今天 "+UtilCodeEx.format(time, "HH:mm");
            } else if (day == 1) {
                return "昨天 " + UtilCodeEx.format(time, "HH:mm");
            } else {
                return UtilCodeEx.format(time, "yyyy年MM月dd日 HH:mm");
            }
        }
    }

    /**
     * 得到两个日期相差的天数
     */
    public static int getBetweenDay(long date1, long date2) {

        Calendar d1 = Calendar.getInstance();
        Calendar d2 = Calendar.getInstance();
        if (date1>date2){
            d1.setTimeInMillis(date2);
            d2.setTimeInMillis(date1);
        }else {
            d1.setTimeInMillis(date1);
            d2.setTimeInMillis(date2);
        }
        int day1 = d1.get(Calendar.DAY_OF_YEAR);
        int day2 = d2.get(Calendar.DAY_OF_YEAR);
        int y1 = d1.get(Calendar.YEAR);
        int y2 = d2.get(Calendar.YEAR);
        if (y1 == y2){
            return day2 - day1;
        }else {
            return (int) Math.ceil((Math.abs(date2-date1)/(1000*60*60*24)));
        }
    }

    /**
     * 获取当前日期是星期几
     *
     * @return
     */
    public static String getDayWeek(Date date) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];

    }

    public static String getMsgKey(int fromId, int toId) {
        return (fromId>toId?(toId+"_"+fromId):(fromId+"_"+toId));
    }
}
