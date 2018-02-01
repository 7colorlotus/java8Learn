package com.lotus.java8Learn.lazyCalculate;

import java.util.stream.IntStream;

/**
 * 对质数的计算，非延迟计算风格
 *
 * 下面的方法不可行
 * “java.lang.IllegalStateException: stream has already been operated upon or closed.”实际上，你正试图使用两个终端操作： findFirst 和 skip 将Stream切分成头尾两部分。
 * 对Stream执行 一次终端操作调用后Stream就终止了，不能再进行操作
 *
 *
 * Created by zhusheng on 2018/1/25 0025.
 */
public class MyMathUtils {



    /**
     * 第一步： 构造由数字组成的Stream
     * @return
     */
    static IntStream numbers(){
        return IntStream.iterate(2, n -> n + 1);
    }

    /**
     * 第二步： 取得首元素
     * @param numbers
     * @return
     */
    static int head(IntStream numbers){
        return numbers.findFirst().getAsInt();
    }

    /**
     * 第三步： 对尾部元素进行筛选
     * @param numbers
     * @return
     */
    static IntStream tail(IntStream numbers){
        return numbers.skip(1);
    }

    /**
     * 第四步：递归地创建由质数组成的Stream
     * @param numbers
     * @return
     */
    static IntStream primes(IntStream numbers) {
        int head = head(numbers);
        return IntStream.concat(
                IntStream.of(head),
                primes(tail(numbers).filter(n -> n % head != 0))
        );
    }
}
