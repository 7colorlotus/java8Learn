package com.lotus.java8Learn.lambdaDebug;

import com.lotus.java8Learn.lambdaTest.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * lambda调试方案
 * 1.查看栈跟踪
 * 2.使用日志调试
 * Created by zhusheng on 2018/1/2 0002.
 */
public class LambdaDebug {

    public static void main(String[] args) {
        userLogDebug();
    }

    /**
     * 查看栈跟踪
     */
    public static void checkTrace(){
        List<Point> points = Arrays.asList(new Point(12, 2), null);
        points.stream().map(p -> p.getX()).forEach(System.out::println);
    }
    /**
     * 使用peek打印日志调试lambda
     */
    public static void userLogDebug(){
        List<Integer> numbers = new ArrayList<>();
        numbers.add(3);
        numbers.add(4);
        numbers.add(5);
        numbers.add(34);
        List<Integer> result =
                    numbers.stream()
                            .peek(x -> System.out.println("from stream: " + x))
                            .map(x -> x + 17)
                            .peek(x -> System.out.println("after map: " + x))
                            .filter(x -> x % 2 == 0)
                            .peek(x -> System.out.println("after filter: " + x))
                            .limit(3)
                            .peek(x -> System.out.println("after limit: " + x))
                            .collect(toList());
    }

}
