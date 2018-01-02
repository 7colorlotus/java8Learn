package com.lotus.java8Learn.optional;

import java.util.Optional;

/**
 * Created by zhusheng on 2018/1/2 0002.
 */
public class OptionalUtility {
    public static Optional<Integer> stringToInt(String s) {
        try {
            return Optional.of(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
