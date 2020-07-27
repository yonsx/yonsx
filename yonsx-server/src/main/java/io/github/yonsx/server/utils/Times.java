package io.github.yonsx.server.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

/**
 * Times
 *
 * @author yakir <a href="yakirchen.github.io">yakirchen.github.io</a> on 04/04/2019 17:45.
 */
public class Times {

    private static final DateTimeFormatter DTF = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd HH:mm:ss SSS").toFormatter();

    public static String nowString() {
        return LocalDateTime.now().format(DTF);
    }
}
