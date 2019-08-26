package com.temp.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {
    private static SimpleDateFormat format;

    static {
        format = new SimpleDateFormat("yyyy-MM-dd");
    }

    public static String getYmd(Date date) {
        return format.format(date);
    }

    /**
     * 获取年-月-日时间格式
     * @return string
     */
    public static String getYmd() {
        return format.format(new Date());
    }

    /**
     * 获取没有中心横线的年月日格式
     * @return string
     */
    public static String getTYmd() {
        return format.format(new Date());
    }

    /**
     * 获取没有中心横线的年月格式
     * @return string
     */
    public static String getTYm() {
        return format.format(new Date());
    }

    public static String getYmd(int num) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, num);
        return format.format(cal.getTime());
    }

    public static String nextDay(String str) {
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
        return format.format(calendar.getTime());
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

        return format.format(date) + " 00:00:00";
    }

    public static String calcDayEnd(Date date) {
        if (date == null) return null;

        return format.format(date) + " 23:59:59";
    }

    public static String calcDayStart(String date) {
        if (date == null) return null;

        return date + " 00:00:00";
    }

    public static String calcDayEnd(String date) {
        if (date == null) return null;

        return date + " 23:59:59";
    }

    /**
     * 计算两个日期之间的差值
     * @param start 开始日期
     * @param end 结束日期
     * @return 相差的天数
     */
    public static Integer calcDiffDay(String start, String end) {
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = format.parse(start);
            endDate = format.parse(end);
        } catch (Exception e) {
            // 日期型字符串格式错误
            System.out.println("日期型字符串格式错误");
            return 0;
        }
        return (int) ((endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000));
    }

    /**
     * 获取连续的年月日时间
     * @param start 开始时间
     * @param end 结束时间
     * @return list
     */
    public static List<String> getContinuousDate(String start, String end) {
        List<String> dateList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(format.parse(start));
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("日期型字符串格式错误");
        }
        int day = calcDiffDay(start, end);
        for (int i = 0; i <= day; i++) {
            calendar.add(Calendar.DAY_OF_MONTH, i == 0 ? 0 : 1);
            dateList.add(format.format(calendar.getTime()));
        }
        return dateList;
    }
}
