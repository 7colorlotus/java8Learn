package com.lotus.java8Learn.UseStream;

import com.lotus.java8Learn.entity.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 归约
 * 将流中所有元素反复结合起来，得到一个值
 * Created by zhusheng on 2017/11/22 0022.
 */
public class ReduceSample {
    public static void main(String[] args){
        List<Integer> someNumbers = Arrays.asList(1, 2, 3, 4, 5);

        //有初始值
        Integer result = someNumbers.stream().reduce(0,(a,b) -> a + b);
        System.out.println(result);

        //无初始值返回Optional，结果可能不存在
        Optional<Integer> resultOptional = someNumbers.stream().reduce((a, b) -> a + b);
        System.out.println(resultOptional.get());

        resultOptional = someNumbers.stream().reduce(Integer::max);
        System.out.println(resultOptional.get());

        testPractise();
    }

    /**
     * 怎样用 map 和 reduce 方法数一数流中有多少个菜呢？
     */
    public static void testPractise(){
        List<Dish> menu = Dish.getMenu();
        Optional<Integer> optional =  menu.stream().map(d -> 1).reduce(Integer::sum);
        System.out.println("dish count: "+optional.get());
        System.out.println("dish count: " + menu.stream().count());
    }
}
