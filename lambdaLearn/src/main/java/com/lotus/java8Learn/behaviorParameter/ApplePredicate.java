package com.lotus.java8Learn.behaviorParameter;

import com.lotus.java8Learn.entity.Apple;

/**
 * 函数式接口
 */
@FunctionalInterface
public interface ApplePredicate {
    //函数式接口中的抽象方法叫函数描述符
    boolean test(Apple apple);
}