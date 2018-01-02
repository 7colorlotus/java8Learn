package com.lotus.java8Learn.refactorDesignModel.responsibilityChain;

import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * Created by zhusheng on 2018/1/2 0002.
 */
public class ResponsibilityChainClient {

    public static void main(String[] args){
        traditinalStyle();

        java8Style();
    }

    public static void traditinalStyle(){
        ProcessingObject<String> p1 = new HeaderTextProcessing();
        ProcessingObject<String> p2 = new SpellCheckerProcessing();
        p1.setSuccessor(p2);
        String result = p1.handle("Aren't labdas really sexy?!!");
        System.out.println(result);
    }

    public static void java8Style(){
        UnaryOperator<String> headerProcessing = (String text) -> "From Raoul, Mario and Alan: " + text;

        UnaryOperator<String> spellCheckerProcessing = (String text) -> text.replaceAll("labda", "lambda");

        Function<String, String> pipeline = headerProcessing.andThen(spellCheckerProcessing);

        String result = pipeline.apply("Aren't labdas really sexy?!!");
        System.out.println(result);
    }
}
