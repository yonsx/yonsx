package io.github.yonsx.server.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

/**
 * Times
 *
 * @author yakir <a href="yakirchen.github.io">yakirchen.github.io</a> on 04/04/2019 17:45.
 */
public class Times {

    private static final DateTimeFormatter DTF     = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd HH:mm:ss SSS").toFormatter();
    private static final ZoneId            ZONE_ID = ZoneId.of("CTT", ZoneId.SHORT_IDS); // 统一时区 Asia/Shanghai

    public static String nowString() {
        return LocalDateTime.now(ZONE_ID).format(DTF);
    }
}
