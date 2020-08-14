package com.rightdarkdoc.utils;

import com.rightdarkdoc.entity.Document;
import org.springframework.format.datetime.DateFormatterRegistrar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeUtils {
    public static String  formatTime(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return  simpleDateFormat.format(date);
    }

    /**
     * 给文档的两个timeString赋值
     */
    public static Document setDocumentTimeString(Document document) {
        document.setCreatetimeString(formatTime(document.getCreattime()));
        document.setLastetidtimeString(formatTime(document.getLastedittime()));
        return document;
    }

}