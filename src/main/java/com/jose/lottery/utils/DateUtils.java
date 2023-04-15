package com.jose.lottery.utils;

import java.time.LocalDate;
import java.time.ZoneId;

/**
 *
 * @author jose
 */
public class DateUtils {

    public static LocalDate getTodayDate() {
        LocalDate currentDateTime = LocalDate.now(ZoneId.of("UTC"));
        return currentDateTime;
    }

}
