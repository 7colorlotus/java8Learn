package com.lotus.java8Learn.collect;

import com.lotus.java8Learn.entity.Dish;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;

/**
 * 分区一个特殊的分组函数，key是Boolean类型
 * 分区是分组的特殊情况：由一个谓词（返回一个布尔值的函数）作为分类函数，它称分区函数。
 * 分区的好处在于保留了分区函数返回 true 或 false 的两套流元素列表。
 *
 * Created by zhusheng on 2017/12/4 0004.
 */
public class PartitioningBy {
    public static void main(String[] args) {
        List<Dish> menu = Dish.getMenu();
        Map<Boolean, List<Dish>> partitionedMenu =
                menu.stream().collect(partitioningBy(Dish::isVegetarian));
        System.out.println("partitionedMenu:" + partitionedMenu);

        Map<Boolean, Map<Dish.Type, List<Dish>>> vegetarianDishesByType =
                menu.stream().collect(
                        partitioningBy(Dish::isVegetarian,
                                groupingBy(Dish::getType)));
        System.out.println("vegetarianDishesByType:" + vegetarianDishesByType);

        Map<Boolean, Dish> mostCaloricPartitionedByVegetarian =
                menu.stream().collect(
                        partitioningBy(Dish::isVegetarian,
                                collectingAndThen(
                                        maxBy(comparingInt(Dish::getCalories)),
                                        Optional::get)));
        System.out.println("mostCaloricPartitionedByVegetarian:" + mostCaloricPartitionedByVegetarian);


        System.out.println(partitionPrimes(100).get(true));
    }

    /**
     * 获得0-n之间的质数
     * @param n
     * @return
     */
    public static Map<Boolean, List<Integer>> partitionPrimes(int n) {
        return IntStream.rangeClosed(2, n).boxed()
                .collect(
                        partitioningBy(candidate -> isPrime(candidate)));
    }

    /**
     * 判断一个数是否是质数
     * @param candidate
     * @return
     */
    public static boolean isPrime(int candidate) {
        int candidateRoot = (int) Math.sqrt((double) candidate);
        return IntStream.rangeClosed(2, candidateRoot)
                .noneMatch(i -> candidate % i == 0);
    }


}
