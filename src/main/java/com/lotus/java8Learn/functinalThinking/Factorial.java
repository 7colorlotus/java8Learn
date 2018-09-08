package com.lotus.java8Learn.functinalThinking;

import java.util.stream.LongStream;

/**
 * 阶乘的多种实现方式
 * Created by zhusheng on 2018/1/22 0022.
 */
public class Factorial {


    /**
     * 迭代式的阶乘计算
     * @param n
     * @return
     */
    static int factorialIterative(int n) {
        int r = 1;
        for (int i = 1; i <= n; i++) {
            r *= i;
        }
        return r;
    }


    /**
     * 递归式的阶乘计算(容易遭遇 StackOverflowError 异常)
     * @param n
     * @return
     */
    static long factorialRecursive(long n) {
        return n == 1 ? 1 : n * factorialRecursive(n-1);
    }

    /**
     * 基于 Stream 的阶乘
     * @param n
     * @return
     */
    static long factorialStreams(long n){
        return LongStream.rangeClosed(1, n)
                .reduce(1, (long a, long b) -> a * b);
    }

    /**
     * 基于“尾- 递”的阶乘
     * @param n
     * @return
     */
    static long factorialTailRecursive(long n) {
        return factorialHelper(1, n);
    }


    static long factorialHelper(long acc, long n) {
        return n == 1 ? acc : factorialHelper(acc * n, n-1);
    }
}
