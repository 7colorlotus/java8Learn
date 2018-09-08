package com.lotus.java8Learn.dateAndTime;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.time.temporal.TemporalAdjusters.nextOrSame;

/**
 * 表示时间点的日期-时间类的通用方法
 *
 *  方 法 名  是否是静态方法  描 述
 *
 *  from  是  依据传入的 Temporal 对象创建对象实例
     now  是  依据系统时钟创建 Temporal 对象
     of  是  由 Temporal 对象的某个部分创建该对象的实例
     parse  是  由字符串创建 Temporal 对象的实例
     atOffset  否  将 Temporal 对象和某个时区偏移相结合
     atZone  否  将 Temporal 对象和某个时区相结合
     format  否  使用某个指定的格式器将 Temporal 对象转换为字符串（ Instant 类不提供该方法）
     get  否  读取 Temporal 对象的某一部分的值
     minus  否 创建 Temporal 对象的一个副本，通过将当前 Temporal 对象的值减去一定的时长 创建该副本
     plus  否 创建 Temporal 对象的一个副本，通过将当前 Temporal 对象的值加上一定的时长 创建该副本
     with  否  以该 Temporal 对象为模板，对某些状态进行修改创建该对象的副本
 * Created by zhusheng on 2018/1/18 0018.
 */
public class DateAndTimeDeal {
    public static void main(String[] args){

    }

    /**
     * 以比较直观的方式操纵 LocalDate 的属性
     */
    public static void useLocalDateDirect(){
        LocalDate date1 = LocalDate.of(2014, 3, 18); //2014-03-18
        LocalDate date2 = date1.withYear(2011); //2011-03-18
        LocalDate date3 = date2.withDayOfMonth(25); //2011-03-25
        LocalDate date4 = date3.with(ChronoField.MONTH_OF_YEAR, 9);//2011-09-25
    }

    /**
     * 以相对方式修改 LocalDate 对象的属性
     */
    public static void useLocalDateRelative(){
        LocalDate date1 = LocalDate.of(2014, 3, 18);//2014-03-18
        LocalDate date2 = date1.plusWeeks(1); //2011-03-18
        LocalDate date3 = date2.minusYears(3); //2011-03-25
        LocalDate date4 = date3.plus(6, ChronoUnit.MONTHS);//2011-09-25
    }

    /**
     * 方 法 名  描 述
     * dayOfWeekInMonth  创建一个新的日期，它的值为同一个月中每一周的第几天
     firstDayOfMonth  创建一个新的日期，它的值为当月的第一天
     firstDayOfNextMonth  创建一个新的日期，它的值为下月的第一天
     firstDayOfNextYear  创建一个新的日期，它的值为明年的第一天
     firstDayOfYear  创建一个新的日期，它的值为当年的第一天
     firstInMonth  创建一个新的日期，它的值为同一个月中，第一个符合星期几要求的值
     lastDayOfMonth  创建一个新的日期，它的值为当月的最后一天
     lastDayOfNextMonth  创建一个新的日期，它的值为下月的最后一天
     lastDayOfNextYear  创建一个新的日期，它的值为明年的最后一天
     lastDayOfYear  创建一个新的日期，它的值为今年的最后一天
     lastInMonth  创建一个新的日期，它的值为同一个月中，最后一个符合星期几要求的值
     next/previous 创建一个新的日期，并将其值设定为日期调整后或者调整前，第一个符合指定星期几要求的日期
     nextOrSame/previousOrSame 创建一个新的日期，并将其值设定为日期调整后或者调整前，第一个符合指定星期几要求的日期，如果该日期已经符合要求，直接返回该对象

     @FunctionalInterface
     public interface TemporalAdjuster {
     Temporal adjustInto(Temporal temporal);
     }

     * 使用预定义的 TemporalAdjuster
     */
    public static void useTemporalAdjuster(){
        LocalDate date1 = LocalDate.of(2014, 3, 18);//2014-03-18
        LocalDate date2 = date1.with(nextOrSame(DayOfWeek.SUNDAY));//2014-03-23
        LocalDate date3 = date2.with(lastDayOfMonth());//2014-03-31
    }


    /**
     * 打印输出及解析日期-时间对象
     */
    public static void parseAndFormatTime(){
        //按照某个模式创建 DateTimeFormatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date1 = LocalDate.of(2014, 3, 18);
        String formattedDate = date1.format(formatter);
        LocalDate date2 = LocalDate.parse(formattedDate, formatter);

        //创建一个本地化的 DateTimeFormatter
        DateTimeFormatter italianFormatter = DateTimeFormatter.ofPattern("d. MMMM yyyy", Locale.ITALIAN);
        LocalDate date3 = LocalDate.of(2014, 3, 18);
        String formattedDate2 = date3.format(italianFormatter); // 18. marzo 2014
        LocalDate date4 = LocalDate.parse(formattedDate, italianFormatter);


        //构造一个 DateTimeFormatter
        DateTimeFormatter italianFormatter2 = new DateTimeFormatterBuilder()
                .appendText(ChronoField.DAY_OF_MONTH)
                .appendLiteral(". ")
                .appendText(ChronoField.MONTH_OF_YEAR)
                .appendLiteral(" ")
                .appendText(ChronoField.YEAR)
                .parseCaseInsensitive()
                .toFormatter(Locale.ITALIAN);
    }
}
