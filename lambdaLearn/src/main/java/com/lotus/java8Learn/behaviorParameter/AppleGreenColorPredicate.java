package com.lotus.java8Learn.behaviorParameter;

import com.lotus.java8Learn.entity.Apple;

public class AppleGreenColorPredicate implements ApplePredicate {
    public boolean test(Apple apple) {
        return "green".equals(apple.getColor());
    }
}