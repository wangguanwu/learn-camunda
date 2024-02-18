package com.gw.camunda.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author guanwu
 * @created on 2024-02-04 14:12:35
 **/
public class DateTimeUtils {

    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";


    public static final String DEFAULT_TIME_ZONE = "GMT+8:00";

    public static String getDefaultDateTimeFormatString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT);
        return simpleDateFormat.format(date);
    }

    public static String getDefaultDateFormatString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        return simpleDateFormat.format(date);
    }
}
