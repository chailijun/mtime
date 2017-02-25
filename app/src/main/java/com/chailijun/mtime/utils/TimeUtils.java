package com.chailijun.mtime.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

import static com.chailijun.mtime.R.string.day;


public class TimeUtils {

    /**
     * 时间差
     *
     * @param timeStamp ：unix时间戳，1480314010
     * @return
     */
    public static String getDistanceTime(long timeStamp) {

        return getDistanceTime(timeStamp * 1000, System.currentTimeMillis());
    }


    /**
     * 获取时间差值
     *
     * @param timeStr
     * @return
     */
    public static String getDistanceTime(String timeStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String delta = "";
        try {
            Date date = format.parse(timeStr);
            long oldTime = date.getTime();
            Date date1 = new Date();
            long currTime = date1.getTime();
            delta = getDistanceTime(oldTime, currTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return delta;
    }

    public static String getDistanceTime(long timeStamp, long currTime) {
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        long diff;
        String flag;
        if (timeStamp < currTime) {
            diff = currTime - timeStamp;
            flag = "前";
        } else {
//            diff = timeStamp - currTime;
//            flag = "后";
            return TimeStamp2Date(timeStamp/1000,"yyyy-MM-dd");
        }
        day = diff / (24 * 60 * 60 * 1000);
        hour = (diff / (60 * 60 * 1000) - day * 24);
        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

        if (day > 0 && day < 2) {
            return day + "天" + flag;
        }else if(day >= 2){
            return TimeStamp2Date(timeStamp/1000,"yyyy-MM-dd");
        }
        if (hour != 0) return hour + "小时" + flag;
        if (min != 0) return min + "分钟" + flag;
        return "刚刚";
    }

    /**
     * 判断是否是“预售”
     * @param showTime       "yyyy-MM-dd"
     * @param nearestShowDay 时间戳
     * @return true:是
     */
    public static boolean isAdvanceSale(String showTime, long nearestShowDay, String formats) {
        if (TextUtils.isEmpty(formats)) formats = "yyyy-MM-dd";

        SimpleDateFormat format = new SimpleDateFormat(formats);
        try {
            Date date = format.parse(showTime);
            long time = date.getTime();
            if (time / 1000 - nearestShowDay < 0) {
                return false;
            } else {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Java将Unix时间戳转换成指定格式日期字符串
     *
     * @param timestampString
     * @param formats
     * @return
     */
    public static String TimeStamp2Date(String timestampString, String formats) {
        if (TextUtils.isEmpty(formats))
            formats = "yyyy-MM-dd HH:mm:ss";
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = new SimpleDateFormat(formats, Locale.CHINA).format(new Date(timestamp));
        return date;
    }

    public static String TimeStamp2Date(long timestamp, String formats) {
        if (TextUtils.isEmpty(formats))
            formats = "yyyy-MM-dd HH:mm:ss";
        timestamp = timestamp * 1000;
        String date = new SimpleDateFormat(formats, Locale.CHINA).format(new Date(timestamp));
        return date;
    }

    /**
     * 将时间戳转换为-->今日/xx月xx日/xxxx年xx月xx日
     * @param timestamp
     * @return
     */
    public static String timeStamp2MonthAndDay(long timestamp) {

        timestamp = timestamp * 1000;
        String date = new SimpleDateFormat("yyyyMMdd", Locale.CHINA).format(new Date(timestamp));
        int yearShow = Integer.parseInt(date.substring(0, 4));
        int monthShow = Integer.parseInt(date.substring(4, 6));
        int dayShow = Integer.parseInt(date.substring(6));

        //当前时间
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        if (yearShow == year && monthShow == month && dayShow == day){
            return "今日";
        }else if(yearShow == year){
            return monthShow+"月"+dayShow+"日";
        }else {
            return yearShow+"年"+monthShow+"月"+dayShow+"日";
        }
    }

    /**
     * yyyy-MM-dd  -->  今天(xx月xx日)
     * @return
     */
    public static String whichDay(String timeStr){
        String time = timeStr;

        //当前日期
        Calendar pre = Calendar.getInstance();
        Date currDate = new Date(System.currentTimeMillis());
        pre.setTime(currDate);

        Calendar cal = Calendar.getInstance();
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).parse(timeStr);
            cal.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR) - pre.get(Calendar.DAY_OF_YEAR);

            int month = cal.get(Calendar.MONTH) + 1;
            int day = cal.get(Calendar.DAY_OF_MONTH);
            String week = getWeek(cal.get(Calendar.DAY_OF_WEEK));

            if (diffDay == 0) {
                //今天
                time = "今天("+month+"月"+day+"日)";

            }else if(diffDay == 1){
                //明天
                time = "明天("+month+"月"+day+"日)";

            }else if(diffDay == 2){
                //后天
                time = "后天("+month+"月"+day+"日)";

            }else {
                //其它
                time = month+"月"+day+"日 " +week;
            }
        }
        return time;
    }

    /**
     * 获取周几
     * @param week
     * @return
     */
    public static String getWeek(int week){
//        Calendar cal = Calendar.getInstance();
//        int i = cal.get(Calendar.DAY_OF_WEEK);
        switch (week) {
            case 1:
                return "周日";
            case 2:
                return "周一";
            case 3:
                return "周二";
            case 4:
                return "周三";
            case 5:
                return "周四";
            case 6:
                return "周五";
            case 7:
                return "周六";
            default:
                return "";
        }
    }


    /**
     * 把毫秒转换成：1:20:30这种形式
     *
     * @param timeMs
     * @return
     */
    public static String stringForTime(int timeMs) {

        // 转换成字符串的时间
        StringBuilder mFormatBuilder = new StringBuilder();
        Formatter mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());

        int totalSeconds = timeMs / 1000;
        int seconds = totalSeconds % 60;

        int minutes = (totalSeconds / 60) % 60;

        int hours = totalSeconds / 3600;

        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds)
                    .toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    /**
     * 得到系统时间
     *
     * @return
     */
    public static String getSysteTime() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(new Date());
    }

}
