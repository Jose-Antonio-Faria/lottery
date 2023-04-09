package com.jose.lottery.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 *
 * @author jose
 */
public class DateUtils {

    public static LocalDateTime getTodayDate() {
        LocalDateTime currentDateTime = LocalDateTime.now(ZoneId.of("UTC"));
        LocalDateTime currentDayMidnight = currentDateTime.withHour(0).withMinute(0).withSecond(0).withNano(0);
        return currentDayMidnight;
    }

}
