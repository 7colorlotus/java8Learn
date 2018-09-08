package com.lotus.java8Learn.refactorDesignModel.templateMethod;

import java.util.function.Consumer;

/**
 * java8实现模板方法模式
 * Created by zhusheng on 2018/1/2 0002.
 */
public class OnlineBankingLambda {
    public void processCustomer(int id, Consumer<Customer> makeCustomerHappy){
        Customer c = Database.getCustomerWithId(id);
        makeCustomerHappy.accept(c);
    }

    public void java8Style(){
        new OnlineBankingLambda().processCustomer(1, (Customer c) ->
                System.out.println("Hello " + c.getName()));
    }
}
