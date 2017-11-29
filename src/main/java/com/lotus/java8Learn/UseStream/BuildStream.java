package com.lotus.java8Learn.UseStream;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 构建流
 * Created by zhusheng on 2017/11/29 0029.
 */
public class BuildStream {
    public static void main(String[] args) {
        buildStreamByFunctionIterate();
    }

    /**
     * 由值创建流
     */
    public static void buildStreamByValue() {
        Stream<String> stream = Stream.of("Java 8 ", "Lambdas ", "In ", "Action");
        stream.map(String::toUpperCase).forEach(System.out::println);

        Stream<String> emptyStream = Stream.empty();
    }

    /**
     * 由数组创建流
     */
    public static void buildStreamByArray() {
        int[] numbers = {2, 3, 5, 7, 11, 13};
        int sum = Arrays.stream(numbers).sum();
        System.out.println(sum);
    }

    /**
     * 由文件生成流
     */
    public static void buildStreamByFile() {
        long uniqueWords = 0;
        try (Stream<String> lines = Files.lines(Paths.get("E:\\personnal\\github\\java8Learn\\src\\main\\java\\com\\lotus\\java8Learn\\UseStream\\data.txt"), Charset.defaultCharset())) {
            uniqueWords = lines.flatMap(line -> Arrays.stream(line.split(" ")))
                    .distinct()
                    .count();
            System.out.println(uniqueWords);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 由函数生成流   Stream.iterate
     */
    public static void buildStreamByFunctionIterate() {
        //1. 迭代
        Stream.iterate(0, n -> n + 2)
                .limit(10)
                .forEach(System.out::println);

        //practise
        Stream.iterate(new int[]{0, 1},
                t -> new int[]{t[1], t[0]+t[1]})
                .limit(20)
                .forEach(t -> System.out.println("(" + t[0] + "," + t[1] +")"));

    }

    /**
     * 由函数生成流 Stream.generate
     */
    public static void buildStreamByFunctionGenerate() {
        //2.生成
        Stream.generate(Math::random)
                .limit(5)
                .forEach(System.out::println);

        IntSupplier fib = new IntSupplier(){
            private int previous = 0;
            private int current = 1;
            public int getAsInt(){
                int oldPrevious = this.previous;
                int nextValue = this.previous + this.current;
                this.previous = this.current;
                this.current = nextValue;
                return oldPrevious;
            }
        };
        IntStream.generate(fib).limit(10).forEach(System.out::println);
    }




}
