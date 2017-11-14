package com.lotus.java8Learn.behaviorParameter;

import com.lotus.java8Learn.entity.Apple;

public class AppleHeavyWeightPredicate implements ApplePredicate {
    public boolean test(Apple apple) {
        return apple.getWeight() > 150;
    }
}