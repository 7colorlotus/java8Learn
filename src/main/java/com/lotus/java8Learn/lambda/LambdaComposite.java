package com.lotus.java8Learn.lambda;

import com.lotus.java8Learn.entity.Apple;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Comparator.comparing;

/**
 * Lambda复合形式
 * Created by zhusheng on 2017/11/16 0016.
 */
public class LambdaComposite {
    public static void main(String[] args){
        comparatorComposite();

        predicateComposite();

        functionComposite();
    }

    /**
     * 比较器复合
     */
    public static void comparatorComposite(){
        Comparator<Apple> c = Comparator.comparing(Apple::getWeight);
        //1.逆序
        Apple apple1 = new Apple("green",100);
        Apple apple2 = new Apple("red",110);
        Apple apple3 = new Apple("green",120);
        Apple apple4 = new Apple("red",140);
        List<Apple> inventory = Arrays.asList(apple1,apple2,apple3,apple4);
        inventory.sort(comparing(Apple::getWeight).reversed());

        //2. 比较器链
        inventory.sort(comparing(Apple::getWeight)
                .reversed()
                .thenComparing(Apple::getCountry));
        System.out.println(inventory);
    }

    /**
     * 谓词复合
     * 谓词接口包括三个方法： negate(非)、 and(并) 和 or(或)
     */
    public static void  predicateComposite(){
        Predicate<Apple> redApple = a -> "red".equals(a.getColor());
        Predicate<Apple> notRedApple = redApple.negate();

        Predicate<Apple> redAndHeavyApple =
                redApple.and(a -> a.getWeight() > 150);

        //请注意， and 和 or 方法是按照在表达式链中的位置，从左向右确定优先级的
        //因此， a.or(b).and(c) 可以看作 (a || b) && c 。
        Predicate<Apple> redAndHeavyAppleOrGreen =
                redApple.and(a -> a.getWeight() > 150)
                        .or(a -> "green".equals(a.getColor()));
        Apple apple = new Apple("green",100);
        System.out.println(redAndHeavyAppleOrGreen.test(apple));
    }

    /**
     * 函数复合
     * Function 接口为此配了 andThen 和 compose 两个默认方法
     */
    public static void functionComposite(){

        Function<Integer, Integer> f = x -> x + 1;
        Function<Integer, Integer> g = x -> x * 2;

        //数学上会写作 g(f(x)) 或 (g o f)(x)
        Function<Integer, Integer> h = f.andThen(g);
        int result = h.apply(1);
        System.out.println(result);

        //数学上会写作 f(g(x)) 或 (f o g)(x)
        Function<Integer, Integer> p = f.compose(g);
        result = p.apply(1);
        System.out.println(result);
    }
}
