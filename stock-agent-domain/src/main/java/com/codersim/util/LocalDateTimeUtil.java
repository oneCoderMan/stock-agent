package com.codersim.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @Author： yijun
 * @DATE: 2023/9/16 22:10
 * @Description 时间日期工具类
 */
public class LocalDateTimeUtil {
    private static final Logger log = LoggerFactory.getLogger(LocalDateTimeUtil.class);
    public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_PATTERN);

    public static LocalDateTime getNow() {
        return LocalDateTime.now();
    }
    public static String getNowStr() {
        return DEFAULT_FORMATTER.format(LocalDateTime.now());
    }

    public static String formatDateTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return DEFAULT_FORMATTER.format(localDateTime);
    }

    public static String localDateTime2Str(LocalDateTime localDateTime, String format) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        return dtf.format(localDateTime);
    }

    public static LocalDateTime str2LocalDateTime(String localDateTimeStr) {
        if (localDateTimeStr == null || localDateTimeStr.equals("")) {
            return null;
        }
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(localDateTimeStr, DEFAULT_FORMATTER);
            return localDateTime;
        } catch (Exception e) {
            log.info("failed to paste localDatetime str: {}", localDateTimeStr);
            return null;
        }
    }

    /**
     * 毫秒时间戳转换为localDateTime
     * @param timestamp
     * @return
     */
    public static LocalDateTime long2LocalDateTime(Long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    /**
     * localDateTime转换为毫秒时间戳
     * @param localDateTime
     * @return
     */
    public static Long localDateTime2Long(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return 0L;
        }
        ZoneId zoneId = ZoneId.systemDefault();
        return localDateTime.atZone(zoneId).toInstant().toEpochMilli();
    }

    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        ZoneId zoneId = ZoneId.systemDefault();
        return Date.from(localDateTime.atZone(zoneId).toInstant());
    }

    public static LocalDateTime date2LocalDateTime(Date date) {
        if (date == null) {
            return null;
        }
        ZoneId zoneId = ZoneId.systemDefault();
        return date.toInstant().atZone(zoneId).toLocalDateTime();
    }

}
