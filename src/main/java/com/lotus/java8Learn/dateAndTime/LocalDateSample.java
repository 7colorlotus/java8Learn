package com.lotus.java8Learn.dateAndTime;

import java.time.*;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

/**
 * Created by zhusheng on 2018/1/18 0018.
 */
public class LocalDateSample {
    public static void main(String[] args){
        createLocalDate();
        System.out.println("--------");
        useTemporalField();
        System.out.println("--------");
        createLocalTime();

        LocalDate dateParse = LocalDate.parse("2014-03-18");
        LocalTime timeParse = LocalTime.parse("13:45:20");

        System.out.println("--------");
        createLocalDateTime();

        System.out.println("--------");
        useInstant();
    }


    /**
     * 创建时间
     */
    public static void createLocalDate(){
        LocalDate date = LocalDate.of(2014, 3, 18);
        int year = date.getYear();
        Month month = date.getMonth();
        int day = date.getDayOfMonth();
        DayOfWeek dow = date.getDayOfWeek();
        int len = date.lengthOfMonth();
        boolean leap = date.isLeapYear();
        System.out.println(" year: " + year
                            + "\n month: " + month
                            + "\n day: " + day
                            + "\n dow:" + dow
                            + "\n len:" + len
                            + "\n leap:" + leap
        );


    }

    /**
     * 使用 TemporalField 读取 LocalDate 的值
     */
    public static void useTemporalField(){
        LocalDate date = LocalDate.of(2014, 3, 18);
        int year = date.get(ChronoField.YEAR);
        int month = date.get(ChronoField.MONTH_OF_YEAR);
        int day = date.get(ChronoField.DAY_OF_MONTH);
        System.out.println(" year: " + year
                + "\n month: " + month
                + "\n day: " + day
        );
    }

    /**
     * 创建 LocalTime 并读取其值
     */
    public static void createLocalTime(){
        LocalTime time = LocalTime.of(13, 45, 20);
        int hour = time.getHour();
        int minute = time.getMinute();
        int second = time.getSecond();
        System.out.println(" hour: " + hour
                + "\n minute: " + minute
                + "\n second: " + second
        );
    }

    /**
     * 直接创建 LocalDateTime 对象，或者通过合并日期和时间的方式创建
     */
    public static void createLocalDateTime(){
        LocalDate date = LocalDate.of(2014, 3, 18);
        LocalTime time = LocalTime.of(13, 45, 20);
        LocalDateTime dt1 = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45, 20);
        System.out.println("dt1:" + dt1);
        LocalDateTime dt2 = LocalDateTime.of(date, time);
        System.out.println("dt2:" + dt2);
        LocalDateTime dt3 = date.atTime(13, 45, 20);
        System.out.println("dt3:" + dt3);
        LocalDateTime dt4 = date.atTime(time);
        System.out.println("dt4:" + dt4);
        LocalDateTime dt5 = time.atDate(date);
        System.out.println("dt5:" + dt5);
    }

    /**
     * 机器的日期和时间格式
     */
    public static void useInstant(){
        System.out.println(Instant.ofEpochSecond(3)); //2秒 之 后 再 加上
        System.out.println(Instant.ofEpochSecond(3, 0)); //100万纳秒（1秒）
        System.out.println(Instant.ofEpochSecond(2, 1_000_000_000));//4秒之前的100
        System.out.println(Instant.ofEpochSecond(4, -1_000_000_000));//万纳秒（1秒）
    }


    /**
     * Duration的方法列表
     * 方 法 名  是否是静态方法  方法描述
     between  是  创建两个时间点之间的 interval
     from  是  由一个临时时间点创建 interval
     of  是  由它的组成部分创建 interval的实例
     parse  是  由字符串创建 interval 的实例
     addTo  否  创建该 interval 的副本，并将其叠加到某个指定的 temporal 对象
     get  否  读取该 interval 的状态
     isNegative  否  检查该 interval 是否为负值，不包含零
     isZero  否  检查该 interval 的时长是否为零
     minus  否  通过减去一定的时间创建该 interval 的副本
     multipliedBy  否  将 interval 的值乘以某个标量创建该 interval 的副本
     negated  否  以忽略某个时长的方式创建该 interval 的副本
     plus  否  以增加某个指定的时长的方式创建该 interval 的副本
     subtractFrom  否  从指定的 temporal 对象中减去该 interval
     * 定义 Duration 或 Period
     */
    public static void useDurationAndPeriod(){
        /*Duration d1 = Duration.between(time1, time2);
        Duration d1 = Duration.between(dateTime1, dateTime2);
        Duration d2 = Duration.between(instant1, instant2);*/
        Period tenDays = Period.between(LocalDate.of(2014, 3, 8), LocalDate.of(2014, 3, 18));

        Duration threeMinutes = Duration.ofMinutes(3);
        Duration threeMinutes2 = Duration.of(3, ChronoUnit.MINUTES);
        Period tenDays2 = Period.ofDays(10);
        Period threeWeeks = Period.ofWeeks(3);
        Period twoYearsSixMonthsOneDay = Period.of(2, 6, 1);
    }
}
