package com.lotus.java8Learn.refactorDesignModel.templateMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhusheng on 2018/1/2 0002.
 */
public class Database {
    private static Map<Integer,Customer> customerMap = new HashMap<>();

    static {
        customerMap.put(1,new Customer(1,"张三"));
        customerMap.put(2,new Customer(2,"李四"));
        customerMap.put(3,new Customer(3,"王五"));
        customerMap.put(4,new Customer(4,"马六"));
    }

    public static Customer getCustomerWithId(int id) {
        return customerMap.get(id);
    }
}
