package com.lotus.java8Learn.UseStream;

import com.lotus.java8Learn.entity.Dish;

import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 数值流
 * Created by zhusheng on 2017/11/29 0029.
 */
public class NumberValueStream {

    public static void main(String[] args){
        pythagoreanTriples();
    }

    /**
     * 原始类型流特化
     */
    public static void originalStreamSpecialize(){
        //原始流
        List<Dish> menu = Dish.getMenu();
        //下面代码它有一个暗含的装箱成本。每个 Integer 都必须拆箱成一个原始类型
        int calories = menu.stream()
                .map(Dish::getCalories)
                .reduce(0, Integer::sum);

        //上面的代码可以如下优化
        calories = menu.stream()
                .mapToInt(Dish::getCalories) //返回一个IntStream
                .sum();

        //转换回对象流
        IntStream intStream = menu.stream().mapToInt(Dish::getCalories);
        Stream<Integer> stream = intStream.boxed(); //将数值流转 换为 Stream


        //默认值 OptionalInt
        OptionalInt maxCalories = menu.stream()
                .mapToInt(Dish::getCalories)
                .max();
        maxCalories.orElse(1);//如果没有最大值的话，显式提供一个默认最大值
    }


    /**
     * 数值范围
     */
    public static void numberRange(){
        IntStream evenNumbers = IntStream.rangeClosed(1, 100) //表 示 范 围[1, 100]
                                        .filter(n -> n % 2 == 0); //一个从1到100的偶数 流
        System.out.println(evenNumbers.count());

        IntStream oddNumbers = IntStream.range(1,100) ////表 示 范 围[1, 100)
                                        .filter(n -> n % 2 == 1); //一个从1到100的奇数 流
        System.out.println(oddNumbers.count());
    }

    /**
     * 数值流应用：勾股数
     */
    public static void pythagoreanTriples(){
        Stream<int[]> pythagoreanTriples =
                IntStream.rangeClosed(1, 100).boxed()
                        .flatMap(a ->
                                IntStream.rangeClosed(a, 100)
                                        .filter(b -> Math.sqrt(a*a + b*b) % 1 == 0)
                                        .mapToObj(b -> new int[]{a, b, (int)Math.sqrt(a * a + b * b)})
                        );


        pythagoreanTriples.limit(5)
                .forEach(t ->
                        System.out.println(t[0] + ", " + t[1] + ", " + t[2]));
    }
}
