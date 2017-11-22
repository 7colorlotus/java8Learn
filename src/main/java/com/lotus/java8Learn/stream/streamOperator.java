package com.lotus.java8Learn.stream;

import com.lotus.java8Learn.entity.Dish;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 *
 * 流操作
 * 中间操作
 * 操作      类型  返回类型        操作参数    函数描述符
 * filter   中间  Stream<T>   Predicate<T>    T -> boolean
 * map      中间  Stream<R>   Function<T, R>  T -> R
 * limit    中间  Stream<T>
 * sorted   中间  Stream<T>   Comparator<T>   (T, T) -> int
 * distinct 中间  Stream<T>
 *
 * forEach  终端  消费流中的每个元素并对其应用 Lambda。这一操作返回 void
 * count    终端  返回流中元素的个数。这一操作返回 long
 * collect  终端  把流归约成一个集合，比如 List 、 Map 甚至是 Integer 。详见第 6 章
 *
 * Created by zhusheng on 2017/11/22 0022.
 */
public class streamOperator {
    public static void main(String[] args){
        List<String> names = Dish.getMenu().stream() //从菜单获得流
                .filter(d -> d.getCalories() > 300) //过滤，中间操作
                .map(Dish::getName)//映射，中间操作
                .limit(3)//截断，中间操作
                .collect(toList());//填充List中，终端操作
    }
}
