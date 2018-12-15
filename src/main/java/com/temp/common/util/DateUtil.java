package com.temp.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static String getYmd(Date date) {
        return (new SimpleDateFormat("yyyy-MM-dd")).format(date);
    }

    /**
     * 获取年-月-日时间格式
     * @return string
     */
    public static String getYmd() {
        return (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
    }

    /**
     * 获取没有中心横线的年月日格式
     * @return string
     */
    public static String getTYmd() {
        return (new SimpleDateFormat("yyyyMMdd")).format(new Date());
    }

    /**
     * 获取没有中心横线的年月格式
     * @return string
     */
    public static String getTYm() {
        return (new SimpleDateFormat("yyyyMM")).format(new Date());
    }

    public static String getYmd(int num) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, num);
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        return dft.format(cal.getTime());
    }

    public static String nextDay(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        int year = Integer.parseInt(str.substring(0, 4));
        String monthsString = str.substring(5, 7);
        int month;
        if ("0".equals(monthsString.substring(0, 1))) {
            month = Integer.parseInt(monthsString.substring(1, 2)) - 1;
        } else {
            month = Integer.parseInt(monthsString.substring(0, 2)) - 1;
        }
        String dayString = str.substring(8, 10);
        int day;
        if ("0".equals(dayString.substring(0, 1))) {
            day = Integer.parseInt(dayString.substring(1, 2));
        } else {
            day = Integer.parseInt(dayString.substring(0, 2));
        }
        calendar.set(year, month, day);
        calendar.add(Calendar.DATE, 1);
        return sdf.format(calendar.getTime());
    }

    public static String getYm(Date date) {
        return (new SimpleDateFormat("yyyy-MM")).format(date);
    }

    public static String getYm(String str) {
        return str.substring(0, 6);
    }

    public static String getYm() {
        return (new SimpleDateFormat("yyyy-MM")).format(new Date());
    }

    public static String getYm(int num) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, num);
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM");
        return dft.format(cal.getTime());
    }

    public static String nextMonth(String str) {
        String lastMonth;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM");
        int year = Integer.parseInt(str.substring(0, 4));
        String monthsString = str.substring(5, 7);
        int month;
        if ("0".equals(monthsString.substring(0, 1))) {
            month = Integer.parseInt(monthsString.substring(1, 2));
        } else {
            month = Integer.parseInt(monthsString.substring(0, 2));
        }
        cal.set(year, month, Calendar.DATE);
        lastMonth = dft.format(cal.getTime());
        return lastMonth;
    }

    public static String getFormatDate(Date date) {
        return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(date);
    }

    public static String calcDayStart(Date date) {
        if (date == null) return null;

        return (new SimpleDateFormat("yyyy-MM-dd 00:00:00")).format(date);
    }

    public static String calcDayEnd(Date date) {
        if (date == null) return null;

        return (new SimpleDateFormat("yyyy-MM-dd 23:59:59")).format(date);
    }

    public static String calcDayStart(String date) {
        if (date == null) return null;

        return date + " 00:00:00";
    }

    public static String calcDayEnd(String date) {
        if (date == null) return null;

        return date + " 23:59:59";
    }
}
