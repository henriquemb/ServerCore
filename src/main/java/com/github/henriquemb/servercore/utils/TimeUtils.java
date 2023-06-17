package com.github.henriquemb.servercore.utils;

import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

public class TimeUtils {
    public static long stringTimeToMilliseconds(String time) {
        PeriodFormatter formatter = new PeriodFormatterBuilder()
                .appendDays().appendSuffix("d")
                .appendHours().appendSuffix("h")
                .appendMinutes().appendSuffix("min")
                .appendSeconds().appendSuffix("s")
                .toFormatter();

        time = time.replace(" ", "");

        if (time.equals("0"))
            time = "";

        try {
            Period period = formatter.parsePeriod(time);
            return period.toStandardDuration().getMillis();
        }
        catch (Exception e) {
            return 0L;
        }
    }

    public static String millisecondsToStringTime(long milliseconds) {
        PeriodFormatter formatter = new PeriodFormatterBuilder()
                .appendDays().appendSuffix("d ")
                .appendHours().appendSuffix("h ")
                .appendMinutes().appendSuffix("min ")
                .appendSeconds().appendSuffix("s")
                .toFormatter();

        try {
            Period period = new Period(milliseconds);
            return period.toString(formatter);
        }
        catch (Exception e) {
            return null;
        }
    }
}
