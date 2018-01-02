package com.lotus.java8Learn.refactorDesignModel.templateMethod;

/**
 * Created by zhusheng on 2018/1/2 0002.
 */
public class Customer {
    private Integer id;
    private String name;

    public Customer(){}

    public Customer(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
