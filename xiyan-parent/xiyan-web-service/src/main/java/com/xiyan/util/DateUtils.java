package com.xiyan.util;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;
import java.util.TimeZone;

/**
 * 时间转换工具类
 *
 * @author bright
 */
public class DateUtils {

    public static String getDateStr(String format) {
        String timezone = "Asia/Shanghai";
        TimeZone timeZone = TimeZone.getTimeZone(timezone);
        return DateFormatUtils.format(new Date(), format, timeZone);
    }
}
