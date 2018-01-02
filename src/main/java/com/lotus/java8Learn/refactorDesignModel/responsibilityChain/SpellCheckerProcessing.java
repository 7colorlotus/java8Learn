package com.lotus.java8Learn.refactorDesignModel.responsibilityChain;

public class SpellCheckerProcessing extends ProcessingObject<String> {
    public String handleWork(String text) {
        return text.replaceAll("labda", "lambda");
    }
}