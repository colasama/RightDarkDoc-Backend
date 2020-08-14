package com.rightdarkdoc.utils;

import org.springframework.format.datetime.DateFormatterRegistrar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeUtils {
    public static String formatTime(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return  simpleDateFormat.format(date);
    }
}