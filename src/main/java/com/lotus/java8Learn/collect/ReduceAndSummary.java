package com.lotus.java8Learn.collect;

import com.lotus.java8Learn.entity.Dish;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * 规约和汇总
 * Created by zhusheng on 2017/12/4 0004.
 */
public class ReduceAndSummary {
    static List<Dish> menu = Dish.getMenu();

    public static void main(String[] args) {
        diffrentStyle();
    }

    /**
     * 统计数量
     */
    public static void count() {
        Long dishCount = menu.stream().count();
        System.out.println("dishCount:" + dishCount);
        dishCount = menu.stream().collect(counting());
        System.out.println("dishCount2:" + dishCount);
    }

    /**
     * 查找最大值
     */
    public static void max() {
        Comparator<Dish> dishCaloriesComparator = Comparator.comparingInt(Dish::getCalories);
        Optional<Dish> mostCalorieDish = menu.stream().collect(maxBy(dishCaloriesComparator));
        System.out.println("mostCalorieDish:" + mostCalorieDish.get());
    }

    /**
     * 汇总
     * Collectors.summingLong 和 Collectors.summingDouble 方法的作用完全一样，
     * 可以用于求和字段为 long 或 double 的情况
     */
    public static void summary() {
        int totalCalories = menu.stream().collect(summingInt(Dish::getCalories));
        System.out.println("totalCalories:" + totalCalories);

        double averageCalories = menu.stream().collect(averagingInt(Dish::getCalories));
        System.out.println("averageCalories:" + averageCalories);

        IntSummaryStatistics menuStatistics = menu.stream().collect(summarizingInt(Dish::getCalories));
        System.out.println("menuStatistics:" + menuStatistics);
    }

    /**
     * 连接字符串
     * joining 在内部使用了 StringBuilder 来把生成的字符串逐个追加起来。
     */
    public static void concatStr() {
        String shortMenu = menu.stream().map(Dish::getName).collect(joining());
        System.out.println("shortMenu:" + shortMenu);

        shortMenu = menu.stream().map(Dish::getName).collect(joining(" "));
        System.out.println("shortMenu:" + shortMenu);

        //shortMenu = menu.stream().collect(joining());
        //System.out.println("shortMenu:" + shortMenu);
    }

    /**
     * 广义的归约汇总
     */
    public static void testReducing() {
        int totalCalories = menu.stream().collect(reducing(0, Dish::getCalories, Integer::sum));
        System.out.println("totalCalories:" + totalCalories);


        /**
         * 使用Stream的reduce方法
         *
         * 这个解决方案有两个问题：一个语义问题和一个实际问题。语义问题在于， reduce 方法
         * 旨在把两个值结合起来生成一个新值，它是一个不可变的归约。与此相反， collect 方法的设
         * 计就是要改变容器，从而累积要输出的结果。这意味着，上面的代码片段是在滥用 reduce 方
         * 法，因为它在原地改变了作为累加器的 List 。你在下一章中会更详细地看到，以错误的语义
         * 使用 reduce 方法还会造成一个实际问题：这个归约过程不能并行工作，因为由多个线程并发
         * 修改同一个数据结构可能会破坏 List 本身。在这种情况下，如果你想要线程安全，就需要每
         * 次分配一个新的 List ，而对象分配又会影响性能。这就是 collect 方法特别适合表达可变容
         * 器上的归约的原因，更关键的是它适合并行操作
         */
        Stream<Integer> stream = Arrays.asList(1, 2, 3, 4, 5, 6).stream();
        List<Integer> numbers = stream.reduce(
                new ArrayList<Integer>(),
                (List<Integer> l, Integer e) -> {
                    l.add(e);
                    return l;
                },
                (List<Integer> l1, List<Integer> l2) -> {
                    l1.addAll(l2);
                    return l1;
                });
        System.out.println(numbers);

    }

    /**
     * 使用不同的方式实现同一个操作
     * */
    public static void diffrentStyle(){
        int totalCalories1 = menu.stream().collect(reducing(
                0, Dish::getCalories, (i, j) -> i + j));

        int totalCalories2 = menu.stream().collect(reducing(0,
                                        Dish::getCalories,
                                        Integer::sum));

        int totalCalories3 = menu.stream().map(Dish::getCalories).reduce(Integer::sum).get();

        int totalCalories4 = menu.stream().mapToInt(Dish::getCalories).sum();

        System.out.println("totalCalories1 == totalCalories2:"+(totalCalories1 == totalCalories2));
        System.out.println("totalCalories2 == totalCalories3:"+(totalCalories2 == totalCalories3));
        System.out.println("totalCalories3 == totalCalories4:"+(totalCalories3 == totalCalories4));
    }

}
