package com.lotus.java8Learn.behaviorParameter;

import com.lotus.java8Learn.entity.Apple;

public class AppleRedAndHeavyPredicate implements ApplePredicate {
    public boolean test(Apple apple) {
        return "red".equals(apple.getColor())
                && apple.getWeight() > 150;
    }
}