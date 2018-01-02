package com.lotus.java8Learn.refactorDesignModel.factoryModel;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 工厂方法模式重构
 * Created by zhusheng on 2018/1/2 0002.
 */
public class FactoryModelClient {

    public static void traditionalStyle(){
        Product p = ProductFactory.createProduct("loan");
    }

    //java8 Style
    final static Map<String, Supplier<Product>> map = new HashMap<>();
    static {
        map.put("loan", Loan::new);
        map.put("stock", Stock::new);
        map.put("bond", Bond::new);
    }

    public static Product java8Style(String name){
        Supplier<Product> p = map.get(name);
        if(p != null) return p.get();
        throw new IllegalArgumentException("No such product " + name);
    }
}
