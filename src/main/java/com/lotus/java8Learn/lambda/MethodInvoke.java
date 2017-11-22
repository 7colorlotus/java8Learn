package com.lotus.java8Learn.lambda;

import com.lotus.java8Learn.entity.Apple;
import com.lotus.java8Learn.entity.Person;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.*;

/**
 * 方法引用的形式
 * 引用静态方法	ContainingClass::staticMethodName
 * 引用某个对象的实例方法（containingObject是Lambda中调用一个已经存在的外部对象中的方法）	containingObject::instanceMethodName
 * 引用某个类型的任意对象的实例方法（containingObject是lambda表达式的参数）	ContainingType::methodName
 * 引用构造方法	ClassName::new
 * <p>
 * Created by zhusheng on 2017/11/14 0014.
 */
public class MethodInvoke {
    public static void main(String[] args) {
        //引用静态方法
        Person p1 = new Person("张三", LocalDate.now(), Person.Sex.MALE, "zhangsan@126.com");
        Person p2 = new Person("李四", LocalDate.now(), Person.Sex.MALE, "lisi@126.com");
        Person p3 = new Person("王五", LocalDate.now(), Person.Sex.MALE, "wangwu@126.com");
        List<Person> personList = Arrays.asList(p1, p2, p3);
        personList.sort(Person::compareByAge);
        System.out.println(personList);

        //引用某个对象的实例方法
        new Thread(p1::print).start();

        //引用某个类型的任意对象的实例方法
        String[] strings = {"123", "ssaa", "sss", "aaa"};
        Arrays.sort(strings, String::compareToIgnoreCase);
        List strList = Arrays.asList(strings);
        System.out.println(strList);



        //引用构造方法
        //无构造参数
        Supplier<Apple> appleSupplier = Apple :: new;
        Apple apple = appleSupplier.get();
        System.out.println(apple);

        //一个构造参数
        Function<Integer,Apple> appleIntegerFunction = Apple :: new;
        Apple apple1 = appleIntegerFunction.apply(10);
        System.out.println(apple1);


        List<Integer> weights = Arrays.asList(7, 3, 4, 10);
        List<Apple> apples = map(weights, Apple::new);
        System.out.println(apples);

        //两个构造参数
        BiFunction<String, Integer, Apple> c3 = Apple::new;
        Apple apple3 = c3.apply("green", 110);
        System.out.println(apple3);


    }

    public static List<Apple> map(List<Integer> list,
                                  Function<Integer, Apple> f){
        List<Apple> result = new ArrayList<>();
        for(Integer e: list){
            result.add(f.apply(e));
        }
        return result;
    }

}
