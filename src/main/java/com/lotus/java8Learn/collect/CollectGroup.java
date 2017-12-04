package com.lotus.java8Learn.collect;

import com.lotus.java8Learn.entity.CaloricLevel;
import com.lotus.java8Learn.entity.Dish;
import com.lotus.java8Learn.entity.Transaction;

import java.util.*;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;

/**
 * 将集合数据分组
 * Created by zhusheng on 2017/12/4 0004.
 */
public class CollectGroup {
    static List<Transaction> transactions = Transaction.getTransactions();

    static List<Dish> menu = Dish.getMenu();

    public static void main(String[] args) {
        collectDataByChildGroup();
    }


    /**
     * java8之前的分组方式
     */
    public static void originalStyle() {
        Map<Currency, List<Transaction>> transactionsByCurrencies =
                new HashMap<>();
        for (Transaction transaction : transactions) {
            Currency currency = transaction.getCurrency();
            List<Transaction> transactionsForCurrency =
                    transactionsByCurrencies.get(currency);
            if (transactionsForCurrency == null) {
                transactionsForCurrency = new ArrayList<>();
                transactionsByCurrencies
                        .put(currency, transactionsForCurrency);
            }
            transactionsForCurrency.add(transaction);
        }
        System.out.println(transactionsByCurrencies);
    }

    /**
     * java8的分组方式
     */
    public static void java8Style() {
        Map<Currency, List<Transaction>> transactionsByCurrencies = transactions.stream().collect(groupingBy(Transaction::getCurrency));
        System.out.println(transactionsByCurrencies);
    }

    /**
     * 按条件分组
     */
    public static void conditionGroup() {
        Map<CaloricLevel, List<Dish>> dishesByCaloricLevel = menu.stream().collect(
                groupingBy(dish -> {
                    if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                    else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                    else return CaloricLevel.FAT;
                }));
        System.out.println(dishesByCaloricLevel);
    }

    /**
     * 多级分组
     */
    public static void multiLevelGroup() {
        Map<Dish.Type, Map<CaloricLevel, List<Dish>>> dishesByTypeCaloricLevel =
                menu.stream().collect(
                        groupingBy(Dish::getType,
                                groupingBy(dish -> {
                                    if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                                    else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                                    else return CaloricLevel.FAT;
                                })
                        )
                );
        System.out.println(dishesByTypeCaloricLevel);
    }


    /**
     * 按子组收集数据
     */
    public static void collectDataByChildGroup() {
        //1. 把收集器的结果转换为另一种类型
        Map<Dish.Type, Dish> mostCaloricByType =
                menu.stream()
                        .collect(groupingBy(Dish::getType,
                                collectingAndThen(
                                        maxBy(comparingInt(Dish::getCalories)),
                                        Optional::get)));
        System.out.println(mostCaloricByType);

        //2. 与 groupingBy 联合使用的其他收集器的例子
        Map<Dish.Type, Integer> totalCaloriesByType =
                menu.stream().collect(groupingBy(Dish::getType,
                        summingInt(Dish::getCalories)));
        System.out.println(totalCaloriesByType);

        Map<Dish.Type, Set<CaloricLevel>> caloricLevelsByType =
                menu.stream().collect(
                        groupingBy(Dish::getType, mapping(
                                dish -> {
                                    if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                                    else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                                    else return CaloricLevel.FAT;
                                },
                                toSet())));
        System.out.println(caloricLevelsByType);

        Map<Dish.Type, Set<CaloricLevel>> caloricLevelsByType2 =
                menu.stream().collect(
                        groupingBy(Dish::getType, mapping(
                                dish -> { if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                                else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                                else return CaloricLevel.FAT; },
                                toCollection(HashSet::new) )));
        System.out.println(caloricLevelsByType2);
    }


}
