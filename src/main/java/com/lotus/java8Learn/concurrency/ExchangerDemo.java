package com.lotus.java8Learn.concurrency;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Exchanger;

/**
 * 演示Exchanger，两个线程交换数据
 */
public class ExchangerDemo {
    static Exchanger<Set> exchanger = new Exchanger();

    static class Thread1 extends Thread{
        @Override
        public void run(){
            Set<String> girlSet = new HashSet();
            girlSet.add("Lily");
            girlSet.add("Lucy");
            girlSet.add("Mirra");
            try {
                girlSet = exchanger.exchange(girlSet);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for(String name : girlSet){
                System.out.println("girlSet :"+name);
            }
        }
    }

    static class Thread2 extends Thread{
        @Override
        public void run(){
            Set<String> boySet = new HashSet();
            boySet.add("Jack");
            boySet.add("Tom");
            boySet.add("Jim");
            try {
                boySet = exchanger.exchange(boySet);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for(String name : boySet){
                System.out.println("boySet :"+name);
            }
        }
    }

    public static void main(String[] args) {
        new Thread1().start();
        new Thread2().start();
    }
}
