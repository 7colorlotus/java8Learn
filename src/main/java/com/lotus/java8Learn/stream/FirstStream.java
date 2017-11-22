package com.lotus.java8Learn.stream;

import com.lotus.java8Learn.entity.Dish;

import java.util.*;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

/**
 * 初步认识流，演示流跟之前的集合处理对比示例
 * 流的定义
 *   元素序列——就像集合一样，流也提供了一个接口，可以访问特定元素类型的一组有序
 * 值。因为集合是数据结构，所以它的主要目的是以特定的时间/空间复杂度存储和访问元
 * 素（如 ArrayList 与  LinkedList ）。但流的目的在于表达计算，比如你前面见到的
 * filter 、 sorted 和 map 。集合讲的是数据，流讲的是计算。我们会在后面几节中详细解
 * 释这个思想。
 *   源——流会使用一个提供数据的源，如集合、数组或输入/输出资源。 请注意，从有序集
 * 合生成流时会保留原有的顺序。由列表生成的流，其元素顺序与列表一致。
 *   数据处理操作——流的数据处理功能支持类似于数据库的操作，以及函数式编程语言中
 * 的常用操作，如 filter 、 map 、 reduce 、 find 、 match 、 sort 等。流操作可以顺序执
 * 行，也可并行执行。
 * 此外，流操作有两个重要的特点。
 *   流水线——很多流操作本身会返回一个流，这样多个操作就可以链接起来，形成一个大
 * 的流水线。这让我们下一章中的一些优化成为可能，如延迟和短路。流水线的操作可以
 * 看作对数据源进行数据库式查询。
 *   内部迭代——与使用迭代器显式迭代的集合不同，流的迭代操作是在背后进行的。我们
 * 在第1章中简要地提到了这个思想，下一节会再谈到它。
 * Created by zhusheng on 2017/11/17 0017.
 */
public class FirstStream {
    private static List<Dish> menu = Arrays.asList(
            new Dish("pork", false, 800, Dish.Type.MEAT),
            new Dish("beef", false, 700, Dish.Type.MEAT),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("french fries", true, 530, Dish.Type.OTHER),
            new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("season fruit", true, 120, Dish.Type.OTHER),
            new Dish("pizza", true, 550, Dish.Type.OTHER),
            new Dish("prawns", false, 300, Dish.Type.FISH),
            new Dish("salmon", false, 450, Dish.Type.FISH) );


    public static void main(String[] args){
        //java7的方式
        List<Dish> lowCaloricDishes = new ArrayList<>();
        for(Dish d: menu){
            if(d.getCalories() < 400){
                lowCaloricDishes.add(d);
            }
        }
        Collections.sort(lowCaloricDishes, new Comparator<Dish>() {
            public int compare(Dish d1, Dish d2){
                return Integer.compare(d1.getCalories(), d2.getCalories());
            }
        });
        List<String> lowCaloricDishesName = new ArrayList<>();
        for(Dish d: lowCaloricDishes){
            lowCaloricDishesName.add(d.getName());
        }
        System.out.println(lowCaloricDishesName);

        //java8的方式
        lowCaloricDishesName =
                menu.stream()
                        .filter(d -> d.getCalories() < 400)
                        .sorted(comparing(Dish::getCalories))
                        .map(Dish::getName)
                        .collect(toList());
        System.out.println(lowCaloricDishesName);

        //java8的方式，多核架构并行执行
        lowCaloricDishesName =
                menu.parallelStream()//修改点
                        .filter(d -> d.getCalories() < 400)
                        .sorted(comparing(Dish::getCalories))
                        .map(Dish::getName)
                        .collect(toList());
        System.out.println(lowCaloricDishesName);
    }
}
