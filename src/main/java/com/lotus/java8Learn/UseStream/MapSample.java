package com.lotus.java8Learn.UseStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * 映射
 * Created by zhusheng on 2017/11/22 0022.
 */
public class MapSample {
    public static void main(String[] args) {
        /*List<String> words = Arrays.asList("Java 8", "Lambdas", "In", "Action");
        List<Integer> wordLengths = words.stream()
                .map(String::length)
                .collect(toList());
        System.out.println(wordLengths);*/

        testArray();

        testPractise1();
        testPractise2();
        testPractise3();
    }

    /**
     * 使用flatMap
     * 使用 flatMap 方法的效果是，各个数组并不是分别映射成一个流，而是映射成流的内容
     * flatmap 方法让你把一个流中的每个值都换成另一个流，然后把所有的流连接起来成为一个流。
     * 将存在集合中的字符串，去除重复字母后输出
     */
    public static void testArray() {
        List<String> words = Arrays.asList("Goodbye", "World");
        List<String> uniqueCharacters =
                words.stream()
                        .map(w -> w.split(""))
                        .flatMap(Arrays::stream)
                        .distinct()
                        .collect(Collectors.toList());
        System.out.println(uniqueCharacters);
    }

    /**
     * 给定一个数字列表，如何返回一个由每个数的平方构成的列表呢？
     * 例如，给定[1, 2, 3, 4,5]，应该返回[1, 4, 9, 16, 25]
     */
    public static void testPractise1() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> results = numbers.stream()
                .map(n -> n * n)
                .collect(toList());
        System.out.println(results);
    }

    /**
     * 给定两个数字列表，如何返回所有的数对呢？例如，给定列表[1, 2, 3]和列表[3, 4]，应
     * 该返回[(1, 3), (1, 4), (2, 3), (2, 4), (3, 3), (3, 4)]。为简单起见，你可以用有两个元素的数组来代
     * 表数对。
     */
    public static void testPractise2() {
        List<Integer> numberList1 = Arrays.asList(1, 2, 3);
        List<Integer> numberList2 = Arrays.asList(3, 4);
        List<Integer[]> result = numberList1.stream()
                .flatMap(i -> numberList2.stream().map(j -> new Integer[]{i, j}))
                .collect(toList());
        System.out.print("[");
        for (Integer[] integers : result) {
            System.out.print("(" + integers[0] + "," + integers[1] + ")" + ",");
        }
        System.out.println("]");
    }

    /**
     * 如何扩展前一个例子，只返回总和能被3整除的数对呢？例如(2, 4)和(3, 3)是可以的。
     */
    public static void testPractise3() {
        List<Integer> numberList1 = Arrays.asList(1, 2, 3);
        List<Integer> numberList2 = Arrays.asList(3, 4);
        List<Integer[]> result = numberList1.stream()
                                            .flatMap(i -> numberList2.stream()
                                                                    .filter(j -> (i + j) % 3 == 0)
                                                                    .map(j -> new Integer[]{i, j}))
                                            .collect(toList());
        System.out.print("[");
        for (Integer[] integers : result) {
            System.out.print("(" + integers[0] + "," + integers[1] + ")" + ",");
        }
        System.out.println("]");
    }
}
