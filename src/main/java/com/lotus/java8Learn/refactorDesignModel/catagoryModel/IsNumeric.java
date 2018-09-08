package com.lotus.java8Learn.refactorDesignModel.catagoryModel;

public class IsNumeric implements ValidationStrategy {
    public boolean execute(String s) {
        return s.matches("\\d+");
    }
}