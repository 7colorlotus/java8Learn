package com.lotus.java8Learn.dateAndTime;

import java.time.*;
import java.util.TimeZone;

/**
 * Created by zhusheng on 2018/1/18 0018.
 */
public class ZoneIdSample {

    public static void useZoneId4Time(){
        //创建ZoneId
        ZoneId romeZone = ZoneId.of("Europe/Rome");
        ZoneId zoneId = TimeZone.getDefault().toZoneId();

        //为时间点添加时区信息
        LocalDate date = LocalDate.of(2014, Month.MARCH, 18);
        ZonedDateTime zdt1 = date.atStartOfDay(romeZone);
        LocalDateTime dateTime = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45);
        ZonedDateTime zdt2 = dateTime.atZone(romeZone);
        Instant instant = Instant.now();
        ZonedDateTime zdt3 = instant.atZone(romeZone);

        //将 LocalDateTime 转换为 Instant
        LocalDateTime dateTime2 = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45);
        Instant instantFromDateTime = dateTime2.toInstant(ZoneOffset.of("-05:00"));

        //通过反向的方式得到 LocalDateTime 对象
        Instant instant2 = Instant.now();
        LocalDateTime timeFromInstant = LocalDateTime.ofInstant(instant2, romeZone);
    }

}
