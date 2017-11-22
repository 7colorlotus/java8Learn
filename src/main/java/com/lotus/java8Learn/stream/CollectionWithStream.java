package com.lotus.java8Learn.stream;

import com.lotus.java8Learn.entity.Dish;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * 可以把流看作在时间中分布的一组值。
 * 相反，集合则是空间（这里就是计算机内存）中分布的一组值，在一个时间点上全体存在
 * Created by zhusheng on 2017/11/22 0022.
 */
public class CollectionWithStream {
    public static void main(String[] args){
        testIterator();
        iteratorCompare();
    }

    /**
     * 流只能遍历一次
     *
     * 可以把流看作在时间中分布的一组值。
     * 相反，集合则是空间（这里就是计算机内存）中分布的一组值，在一个时间点上全体存在
     */
    public static void testIterator(){
        List<String> title = Arrays.asList("Java8", "In", "Action");
        Stream<String> s = title.stream();
        s.forEach(System.out::println);
        s.forEach(System.out::println);
    }

    /**
     * 迭代对比，集合迭代与流迭代
     */
    public static void iteratorCompare(){
        List<Dish> menu = Dish.getMenu();
        List<String> names = new ArrayList<>();


        //集合：用 for-each 循环外部迭代
        for(Dish d: menu){
            names.add(d.getName());
        }

        //集合：用背后的迭代器做外部迭代
        Iterator<Dish> iterator = menu.iterator();
        while(iterator.hasNext()) {
            Dish d = iterator.next();
            names.add(d.getName());
        }

        //流：内部迭代
        names = menu.stream()
                .map(Dish::getName)
                .collect(toList());
    }
}
