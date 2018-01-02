package com.lotus.java8Learn.refactorDesignModel.visitorModel;

/**
 * 观察者模式调用
 *
 * 是否我们随时随地都可以使用Lambda表达式呢？答案是否定的！我们前文介绍的例
 * 子中，Lambda适配得很好，那是因为需要执行的动作都很简单，因此才能很方便地消除僵化代
 * 码。但是，观察者的逻辑有可能十分复杂，它们可能还持有状态，抑或定义了多个方法，诸如此
 * 类。在这些情形下，你还是应该继续使用类的方式。
 * Created by zhusheng on 2018/1/2 0002.
 */
public class VisitorClient {
    public static void main(String[] args){
        traditionalStyle();

        java8Style();
    }

    /**
     * 传统方式
     */
    public static void traditionalStyle(){
        Feed f = new Feed();
        f.registerObserver(new NYTimes());
        f.registerObserver(new Guardian());
        f.registerObserver(new LeMonde());
        f.notifyObservers("The queen said her favourite book is Java 8 in Action!");
    }

    /**
     * java8方式
     */
    public static void java8Style(){
        Feed f = new Feed();

        f.registerObserver((String tweet) -> {
            if(tweet != null && tweet.contains("money")){
                System.out.println("Breaking news in NY! " + tweet);
            }
        });

        f.registerObserver((String tweet) -> {
            if(tweet != null && tweet.contains("queen")){
                System.out.println("Yet another news in London... " + tweet);
            }
        });

        f.notifyObservers("moneyqueen");
    }
}
