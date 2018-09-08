package com.lotus.java8Learn.dateAndTime;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.Date;
import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;

/**
 * Created by zhusheng on 2018/3/27 0027.
 */
public class ClockSample {
    public static void main(String[] args) {
        Clock clock = Clock.systemDefaultZone();
        System.out.println("clock.millis():"+clock.millis());
        Instant instant = clock.instant();
        System.out.println(instant.get(ChronoField.MICRO_OF_SECOND));
        Date date = Date.from(instant);
        System.out.println(date.toString());
    }
}
