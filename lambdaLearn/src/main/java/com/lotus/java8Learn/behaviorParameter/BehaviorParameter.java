package com.lotus.java8Learn.behaviorParameter;

import com.lotus.java8Learn.entity.Apple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * 通过行为参数化传递代码
 * 示例代码演示筛选集合，循序递进的方式，总结出使用lambda的原理和好处
 * Created by zhusheng on 2017/11/14 0014.
 */
public class BehaviorParameter {
    public static void main(String args[]){
        List<Apple> inventory = Arrays.asList(new Apple("green",100),
                                                new Apple("red",160),
                                                new Apple("red",110),
                                                new Apple("green",80));
        List<Apple> redAndHeavyApples =
                filterApples(inventory, new AppleRedAndHeavyPredicate());

        System.out.println(redAndHeavyApples);

        //使用匿名类
        List<Apple> redApples = filterApples(inventory, new ApplePredicate() {
            public boolean test(Apple apple){
                return "red".equals(apple.getColor());
            }
        });

        //使用 Lambda 表达式
        List<Apple> result =
                filterApples(inventory, (Apple apple) -> "red".equals(apple.getColor()));
        System.out.println(result);
    }

    /**
     * 筛选绿苹果
     * @param inventory
     * @return
     */
    public static List<Apple> filterGreenApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<Apple>();
        for(Apple apple: inventory){
            if( "green".equals(apple.getColor())){
                result.add(apple);
            }
        }
        return result;
    }

    /**
     * 把颜色作为参数
     * @param inventory
     * @param color
     * @return
     */
    public static List<Apple> filterApplesByColor(List<Apple> inventory,
                                                  String color) {
        List<Apple> result = new ArrayList<Apple>();
        for (Apple apple: inventory){
            if ( apple.getColor().equals(color) ) {
                result.add(apple);
            }
        }
        return result;
    }

    /**
     * 把重量作为参数
     * @param inventory
     * @param weight
     * @return
     */
    public static List<Apple> filterApplesByWeight(List<Apple> inventory,
                                                   int weight) {
        List<Apple> result = new ArrayList<Apple>();
        for (Apple apple: inventory){
            if ( apple.getWeight() > weight ){
                result.add(apple);
            }
        }
        return result;
    }

    /**
     * 将重量和颜色都做为参数
     * @param inventory List<Apple>
     * @param color String
     * @param weight int
     * @param flag boolean true 表示根据颜色筛选，false 表示根据重量筛选
     * @return
     */
    public static List<Apple> filterApples(List<Apple> inventory, String color,
                                           int weight, boolean flag) {
        List<Apple> result = new ArrayList<Apple>();
        for (Apple apple: inventory){
            if ( (flag && apple.getColor().equals(color)) ||
                    (!flag && apple.getWeight() > weight) ){
                result.add(apple);
            }
        }
        return result;
    }

    /**
     * 根据抽象条件筛选
     * @param inventory
     * @param p
     * @return
     */
    public static List<Apple> filterApples(List<Apple> inventory,
                                           ApplePredicate p){
        List<Apple> result = new ArrayList<>();
        for(Apple apple: inventory){
            if(p.test(apple)){
                result.add(apple);
            }
        }
        return result;
    }

    /**
     * 将list类型参数化
     * @param list
     * @param p
     * @param <T>
     * @return
     */
    public static <T> List<T> filter(List<T> list, Predicate<T> p){
        List<T> result = new ArrayList<>();
        for(T e: list){
            if(p.test(e)){
                result.add(e);
            }
        }
        return result;
    }
}
