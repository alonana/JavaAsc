package com.javaasc.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    public static SimpleDateFormat getDateFormat(boolean withMilliseconds) throws Exception {
        if (withMilliseconds) {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public static String getTimeCurrent(boolean withMilliseconds) throws Exception {
        return getTime(System.currentTimeMillis(), withMilliseconds);
    }

    public static String getTime(long time, boolean withMilliseconds) throws Exception {
        SimpleDateFormat dateFormat = getDateFormat(withMilliseconds);
        return dateFormat.format(new Date(time));
    }
}
