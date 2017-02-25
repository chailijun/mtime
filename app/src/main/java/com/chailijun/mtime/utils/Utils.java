package com.chailijun.mtime.utils;

import android.content.Context;
import android.net.TrafficStats;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

public class Utils {
    private StringBuilder mFormatBuilder;
    private Formatter mFormatter;

    private long lastTotalRxBytes = 0;
    private long lastTimeStamp = 0;

    public Utils() {
        // 转换成字符串的时间
        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());

    }

    /**
     * 把毫秒转换成：1:20:30这种形式
     *
     * @param timeMs
     * @return
     */
    public String stringForTime(int timeMs) {
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
     * 时间差
     *
     * @param timeMs
     * @return
     */
    public String stringForTime(long timeMs) {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String publishTime = sdf.format(timeMs*1000);//时间戳13位

        Date now = new Date();
        Date date= null;
        try {
            date = sdf.parse(publishTime);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long l=now.getTime()-date.getTime();
        long day=l/(24*60*60*1000);
        long hour=(l/(60*60*1000)-day*24);
        long min=((l/(60*1000))-day*24*60-hour*60);
        long s=(l/1000-day*24*60*60-hour*60*60-min*60);
//        System.out.println(""+day+"天"+hour+"小时"+min+"分"+s+"秒");
        if (day > 0){
            int month = calendar.get(Calendar.MONTH) + 1;
            int day_of_month = calendar.get(Calendar.DAY_OF_MONTH);
            return month+"-"+day_of_month;
        }else if(hour-4 > 0){
            return hour- 4+"小时前";
        }else if (min > 0){
            return min+"分钟前";
        }else if (s > 0) {
            return s + "秒前";
        }
            return "刚刚";
    }



    public static String getFormatedDateTime(String pattern, long dateTime) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(pattern);
        return sDateFormat.format(new Date(dateTime + 0));
    }

    /**
     * 判断资源是否属于网络资源
     *
     * @param uri
     * @return
     */
    public boolean isNetUri(String uri) {
        boolean result = false;
        if (uri.toLowerCase().startsWith("http") || uri.toLowerCase().startsWith("rtsp") ||
                uri.toLowerCase().startsWith("mms")) {
            result = true;
        }
        return result;
    }

    /**
     * 得到网络速度
     * 每隔两秒调用一次
     *
     * @param context
     * @return
     */
    public String getNetSpeed(Context context) {
        String netSpeed = "0 kb/s";
        long nowTotalRxBytes = TrafficStats.getUidRxBytes(context.getApplicationInfo().uid) == TrafficStats.UNSUPPORTED ? 0 : (TrafficStats.getTotalRxBytes() / 1024);//转为KB;
        long nowTimeStamp = System.currentTimeMillis();
        long speed = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 / (nowTimeStamp - lastTimeStamp));//毫秒转换

        lastTimeStamp = nowTimeStamp;
        lastTotalRxBytes = nowTotalRxBytes;
        netSpeed = String.valueOf(speed) + " kb/s";
        return netSpeed;
    }
}
