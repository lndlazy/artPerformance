package com.art.recruitment.artperformance.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateFormatUtils {

    public static final String DATE_FORMAT_PATTERN_YMD = "yyyy.MM.dd";
    public static final String DATE_FORMAT_PATTERN_YMD_HM = "yyyy.MM.dd HH:mm";
    public static final String DATE_FORMAT_PATTERN_YMD_HMS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 时间戳转字符串
     *
     * @param timestamp     时间戳
     * @param isPreciseTime 是否包含时分
     * @return 格式化的日期字符串
     */
    public static String long2Str(long timestamp, boolean isPreciseTime) {
        return long2Str(timestamp, getFormatPattern(isPreciseTime));
    }

    private static String long2Str(long timestamp, String pattern) {
        return new SimpleDateFormat(pattern, Locale.CHINA).format(new Date(timestamp));
    }


    public static long parseServerTime(String serverTime, String format) {
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINESE);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        Date date = null;
        try {
            date = sdf.parse(serverTime);
        } catch (Exception e) {
            e.printStackTrace();
//            Timber.e(e, "");
            return -1;
        }
        return date.getTime();
    }


    /**
     * 将日期以yyyy-MM-dd HH:mm:ss格式化
     *
     * @param dateL
     *            日期
     * @return
     */
    public static String formatDateTime(long dateL,String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = new Date(dateL);
        return sdf.format(date);
    }


    /**
     * 字符串转时间戳
     *
     * @param dateStr       日期字符串
     * @param isPreciseTime 是否包含时分
     * @return 时间戳
     */
    public static long str2Long(String dateStr, boolean isPreciseTime) {
        return str2Long(dateStr, getFormatPattern(isPreciseTime));
    }

    private static long str2Long(String dateStr, String pattern) {
        try {
            return new SimpleDateFormat(pattern, Locale.CHINA).parse(dateStr).getTime();
        } catch (Throwable ignored) {
        }
        return 0;
    }

    private static String getFormatPattern(boolean showSpecificTime) {
        if (showSpecificTime) {
            return DATE_FORMAT_PATTERN_YMD_HM;
        } else {
            return DATE_FORMAT_PATTERN_YMD;
        }
    }

    public static String getTodayDateTime(String dateFormat) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat,
                Locale.getDefault());
        return format.format(new Date());
    }

}
