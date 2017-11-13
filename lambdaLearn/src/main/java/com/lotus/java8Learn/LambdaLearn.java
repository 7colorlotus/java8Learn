package com.lotus.java8Learn;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

/**
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
}
