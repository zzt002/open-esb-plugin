package com.dongdl.springboot1.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/12 10:04 UTC+8
 * @description
 **/
public class DateUtil {

    public static final String FORMAT_DAY = "yyyyMMdd";
    public static final String FORMAT_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String TIMEZONE = "GMT+8";

    public static String getCurrentDateStr(){
        Date date = Calendar.getInstance().getTime();
        return new SimpleDateFormat(FORMAT_DAY).format(date);
    }

    public static Date getCurrentDate(){
        return  Calendar.getInstance().getTime();
    }

    public static Date getCurrentDate(Integer seconds){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.SECOND, seconds);
        return c.getTime();
    }

    public static String formatDate(Date date){
        return new SimpleDateFormat(FORMAT_DAY).format(date);
    }

    public static String formatTime(Date date){
        return new SimpleDateFormat(FORMAT_TIME).format(date);
    }

    /**
     * 与当前时间比较
     * @param dateStr 格式为 “yyyyMMdd”
     * @return
     */
    public static long subDayFromToday(String dateStr) {
        long currentMillis = System.currentTimeMillis();
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, Integer.parseInt(dateStr.substring(0,4)));
        c.set(Calendar.MONTH, Integer.parseInt(dateStr.substring(4,6)) - 1);
        c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateStr.substring(6,8)));
        long dateMillis = c.getTimeInMillis();
        return (dateMillis - currentMillis) / (1000 * 60 * 60 * 24);
    }

}
