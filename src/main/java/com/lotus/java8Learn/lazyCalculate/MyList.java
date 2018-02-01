package com.lotus.java8Learn.lazyCalculate;

import java.util.function.Predicate;

interface MyList<T> {
    T head();

    MyList<T> tail();

    default boolean isEmpty() {
        return true;
    }

    default MyList<T> filter(Predicate<T> p){
        return new Empty<T>();
    }
}