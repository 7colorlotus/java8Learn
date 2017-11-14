package com.lotus.java8Learn.lambda;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Predicate;

/**
 * 可以把Lambda表达式理解为简洁地表示可传递的匿名函数的一种方式：它没有名称，但它
 * 有参数列表、函数主体、返回类型，可能还有一个可以抛出的异常列表。这个定义够大的，让我
 * 们慢慢道来。
 *   匿名——我们说匿名，是因为它不像普通的方法那样有一个明确的名称：写得少而想
 * 得多！
 *   函数——我们说它是函数，是因为Lambda函数不像方法那样属于某个特定的类。但和方
 * 法一样，Lambda有参数列表、函数主体、返回类型，还可能有可以抛出的异常列表。
 *   传递——Lambda表达式可以作为参数传递给方法或存储在变量中。
 *   简洁——无需像匿名类那样写很多模板代码。
 * Created by zhusheng on 2017/11/13 0013.
 */
public class LambdaLearn {
    public static void main(String[] args){
        List<String> strList = Arrays.asList(new String[]{"123","2323","sss"});

        strList.sort((str1,str2)->{ return -str1.compareTo(str2);});
        System.out.println(strList.toString());

        Comparator<String> comparator = (str1, str2)->{ return str1.compareTo(str2);};
        strList.sort(comparator);
        System.out.println(strList.toString());

    }

    public Callable<String> fetch() {
        return () -> "Tricky example ;-)";
    }
}
