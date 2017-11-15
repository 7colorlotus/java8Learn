package com.lotus.java8Learn.lambda;

import com.lotus.java8Learn.entity.Apple;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 苹果根据重量排序实现方式
 * 第 1 步：传递代码
 * 第 2 步：使用匿名类
 * 第 3 步：使用 Lambda 表达式
 * 第 4 步：使用方法引用
 * Created by zhusheng on 2017/11/14 0014.
 */
public class AppleSort {
    public static void main(String[] args){
        Apple apple1 = new Apple("green",100);
        Apple apple2 = new Apple("red",110);
        Apple apple3 = new Apple("green",120);
        Apple apple4 = new Apple("red",140);
        List<Apple> appleList = Arrays.asList(apple1,apple2,apple3,apple4);
        //第 1 步：传递代码
        appleList.sort(new AppleComparator());
        System.out.println("第 1 步:"+appleList);

        //第 2 步：使用匿名类
        appleList.sort(new Comparator<Apple>(){

            @Override
            public int compare(Apple o1, Apple o2) {
                return o2.getWeight() - o1.getWeight();
            }
        });
        System.out.println("第 2 步:"+appleList);

        //第 3 步：使用 Lambda 表达式
        appleList.sort((Apple o1,Apple o2) -> o1.getWeight() - o2.getWeight());
        System.out.println("第 3 步:"+appleList);
        appleList.sort((o1,o2) -> o2.getWeight() - o1.getWeight());
        System.out.println("第 3 步:"+appleList);

        //第 4 步：使用方法引用
        appleList.sort(Comparator.comparing(apple -> apple.getWeight()));
        System.out.println("第 4 步:"+appleList);
        appleList.sort(Comparator.comparing(Apple::getWeight).reversed());
        System.out.println("第 4 步:"+appleList);
    }
}


class AppleComparator implements Comparator<Apple>{

    @Override
    public int compare(Apple o1, Apple o2) {
        return o1.getWeight() - o2.getWeight();
    }
}
