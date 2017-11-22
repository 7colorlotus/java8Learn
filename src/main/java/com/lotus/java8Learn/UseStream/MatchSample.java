package com.lotus.java8Learn.UseStream;

import com.lotus.java8Learn.entity.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 查找和匹配
 * 常见的数据处理套路是看看数据集中的某些元素是否匹配一个给定的属性。
 * Stream API通过 allMatch 、 anyMatch 、 noneMatch 、 findFirst 和 findAny 方法提供了这样的工具。
 * Created by zhusheng on 2017/11/22 0022.
 */
public class MatchSample {
    public static void main(String[] args){
        List<Dish> menu = Dish.getMenu();
        if(menu.stream().anyMatch(Dish::isVegetarian)){
            System.out.println("The menu is (somewhat) vegetarian friendly!!");
        }

        boolean isHealthy = menu.stream().allMatch(d -> d.getCalories() < 1000);
        System.out.println("isHealthy:"+isHealthy);

        isHealthy = menu.stream().noneMatch(d -> d.getCalories() >= 1000);
        System.out.println("isHealthy:"+isHealthy);

        //Optional<T> 类（ java.util.Optional ）是一个容器类，代表一个值存在或不存在
        Optional<Dish> dish = menu.stream().filter(Dish::isVegetarian).findAny();
        if(dish.isPresent()){
            Dish dish1 = dish.get();
            System.out.println(dish1.getName());
        }

        List<Integer> someNumbers = Arrays.asList(1, 2, 3, 4, 5);
        Optional<Integer> number = someNumbers.stream().map(n -> n * n).filter(n -> n % 3 == 0).findFirst();
        System.out.println("number : "+ number.get());
    }
}
