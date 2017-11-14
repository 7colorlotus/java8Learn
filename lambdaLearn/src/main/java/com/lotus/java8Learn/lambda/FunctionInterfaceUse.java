package com.lotus.java8Learn.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

/**
 * Java 8的库设计师帮你在 java.util.function 包中引入了几个新的函数式接口: Predicate 、 Consumer 和 Function
 * Created by zhusheng on 2017/11/14 0014.
 */
public class FunctionInterfaceUse {

    public static void main(String args[]){
        List<String> listOfStrings = Arrays.asList("fads","xvxcv","","234","");
        Predicate<String> nonEmptyStringPredicate = (String s) -> !s.isEmpty();
        List<String> nonEmpty = filter(listOfStrings, nonEmptyStringPredicate);
        System.out.println(nonEmpty);


        forEach(
                Arrays.asList(1,2,3,4,5),
                (Integer i) -> System.out.println(i)
        );

        List<Integer> l = map(
                Arrays.asList("lambdas","in","action"),
                (String s) -> s.length()
        );
        System.out.println(l);


        /*public interface IntPredicate{
            boolean test(int t);
        }*/
        IntPredicate evenNumbers = (int i) -> i % 2 == 0;
        System.out.println(evenNumbers.test(1000));
        Predicate<Integer> oddNumbers = (Integer i) -> i % 2 == 1;
        System.out.println(oddNumbers.test(1000));

        int portNumber = 1337;
        Runnable r = () -> System.out.println(portNumber);
        //lambda表达式里使用过的局部变量，默认就是final类型的，不能再对该局部变量做改变
//        portNumber = 13d37;
    }

    /**
     * 使用Predicate接口
     * @FunctionalInterface
     * public interface Predicate<T>{
     *      boolean test(T t);
     * }
     * @param list
     * @param p
     * @param <T>
     * @return
     */
    public static <T> List<T> filter(List<T> list, Predicate<T> p) {
        List<T> results = new ArrayList<>();
        for(T s: list){
            if(p.test(s)){
                results.add(s);
            }
        }
        return results;
    }

    /**
     * 使用Consumer接口
     * @FunctionalInterface
     * public interface Consumer<T>{
     *      void accept(T t);
     * }
     * @param list
     * @param c
     * @param <T>
     */
    public static <T> void forEach(List<T> list, Consumer<T> c){
        for(T i: list){
            c.accept(i);
        }
    }


    /**
     * 使用 Function接口
     * @FunctionalInterface
     * public interface Function<T, R>{
     *      R apply(T t);
     * }
     * @param list
     * @param f
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> List<R> map(List<T> list, Function<T, R> f) {
        List<R> result = new ArrayList<>();
        for(T s: list){
            result.add(f.apply(s));
        }
        return result;
    }
}
