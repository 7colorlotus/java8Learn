package com.lotus.java8Learn.refactorDesignModel.catagoryModel;

import com.lotus.java8Learn.refactorDesignModel.catagoryModel.IsAllLowerCase;
import com.lotus.java8Learn.refactorDesignModel.catagoryModel.IsNumeric;
import com.lotus.java8Learn.refactorDesignModel.catagoryModel.Validator;

/**
 * 使用java8重构策略模式
 * Created by zhusheng on 2018/1/2 0002.
 */
public class CatagoryModel {
    public static void main(String[] args) {
        traditionalStyle();

        java8Style();
    }

    /**
     * 传统方式
     */
    public static void traditionalStyle(){
        Validator numericValidator = new Validator(new IsNumeric());
        boolean b1 = numericValidator.validate("aaaa");
        System.out.println("traditionalStyle b1 : " + b1);
        Validator lowerCaseValidator = new Validator(new IsAllLowerCase());
        boolean b2 = lowerCaseValidator.validate("bbbb");
        System.out.println("traditionalStyle b2 : " + b2);
    }

    /**
     * java8方式
     */
    public static void java8Style(){
        Validator numericValidator = new Validator((String s) -> s.matches("\\d+"));
        boolean b1 = numericValidator.validate("aaaa");
        System.out.println("java8Style b1 : " + b1);
        Validator lowerCaseValidator = new Validator((String s) -> s.matches("[a-z]+"));
        boolean b2 = lowerCaseValidator.validate("bbbb");
        System.out.println("java8Style b2 : " + b2);
    }


}
