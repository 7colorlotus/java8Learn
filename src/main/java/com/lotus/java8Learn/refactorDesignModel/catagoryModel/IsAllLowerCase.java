package com.lotus.java8Learn.refactorDesignModel.catagoryModel;

public class IsAllLowerCase implements ValidationStrategy {
    public boolean execute(String s) {
        return s.matches("[a-z]+");
    }
}