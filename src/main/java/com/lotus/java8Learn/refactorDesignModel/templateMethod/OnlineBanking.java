package com.lotus.java8Learn.refactorDesignModel.templateMethod;

import java.util.function.Consumer;

abstract class OnlineBanking {
    public void processCustomer(int id) {
        Customer c = Database.getCustomerWithId(id);
        makeCustomerHappy(c);
    }

    abstract void makeCustomerHappy(Customer c);
}