package com.lotus.java8Learn.UseStream;

import com.lotus.java8Learn.entity.Dish;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * 筛选和切片
 * Created by zhusheng on 2017/11/22 0022.
 */
public class FilterSample {
    public static void main(String[] args){
        List<Dish> menu = Dish.getMenu();
        //用谓词筛选
        List<Dish> vegetarianMenu = menu.stream()
                //方法引用检查菜肴是否适合素食者
                .filter(Dish::isVegetarian)
                .collect(toList());

        //筛选各异的元素
        List<Integer> numbers = Arrays.asList();
        numbers.stream()
                .filter(i -> i % 2 == 0)
                .distinct()
                .forEach(System.out::println);
        //截短流
        List<Dish> dishes = menu.stream()
                .filter(d -> d.getCalories() > 300)
                .limit(3)
                .collect(toList());

        dishes = menu.stream()
                .filter(d -> d.getCalories() > 300)
                .skip(2)
                .collect(toList());
    }

}
