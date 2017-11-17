package com.lotus.java8Learn.stream;

import com.lotus.java8Learn.entity.Dish;

import java.util.*;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

/**
 * 初步认识流，演示流跟之前的集合处理对比示例
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
                menu.parallelStream()
                        .filter(d -> d.getCalories() < 400)
                        .sorted(comparing(Dish::getCalories))
                        .map(Dish::getName)
                        .collect(toList());
        System.out.println(lowCaloricDishesName);
    }
}
