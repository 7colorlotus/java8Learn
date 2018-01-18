package com.lotus.java8Learn.dateAndTime;

import java.time.DayOfWeek;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;

/**
 * 实现一个定制的 TemporalAdjuster
 * 请设计一个 NextWorkingDay 类，该类实现了 TemporalAdjuster 接口，能够计算明天
 * 的日期，同时过滤掉周六和周日这些节假日。格式如下所示：
 * date = date.with(new NextWorkingDay());
 * 如果当天的星期介于周一至周五之间，日期向后移动一天；如果当天是周六或者周日，则
 * 返回下一个周一。
 *
 * 使用lambda表达式实现此类的功能
 * date = date.with(temporal -> {
                         DayOfWeek dow =
                         DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
                         int dayToAdd = 1;
                         if (dow == DayOfWeek.FRIDAY) dayToAdd = 3;
                         else if (dow == DayOfWeek.SATURDAY) dayToAdd = 2;
                         return temporal.plus(dayToAdd, ChronoUnit.DAYS);
                         });

    //代码复用方式

     TemporalAdjuster nextWorkingDay = TemporalAdjusters.ofDateAdjuster(
     temporal -> {
         DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
         int dayToAdd = 1;
         if (dow == DayOfWeek.FRIDAY) dayToAdd = 3;
         if (dow == DayOfWeek.SATURDAY) dayToAdd = 2;
         return temporal.plus(dayToAdd, ChronoUnit.DAYS);
     });
     date = date.with(nextWorkingDay);
 */
public class NextWorkingDay implements TemporalAdjuster {
    @Override
    public Temporal adjustInto(Temporal temporal) {
        DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
        int dayToAdd = 1;
        if (dow == DayOfWeek.FRIDAY) dayToAdd = 3;
        else if (dow == DayOfWeek.SATURDAY) dayToAdd = 2;
        return temporal.plus(dayToAdd, ChronoUnit.DAYS);
    }
}