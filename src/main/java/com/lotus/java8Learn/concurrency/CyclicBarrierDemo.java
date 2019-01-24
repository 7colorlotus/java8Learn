package com.lotus.java8Learn.concurrency;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 演示CyclicBarrier
 */
public class CyclicBarrierDemo {
    static CyclicBarrier cyclicBarrier;

    static class Thread1 extends Thread{
        @Override
        public void run(){
            System.out.println(Thread.currentThread().getName() + "执行屏障前。。。。。");
            try {
                cyclicBarrier.await(); //一般用于线程类run方法的最末行比较实用
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "执行屏障后。。。。。");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        cyclicBarrier = new CyclicBarrier(2);

        new Thread1().start();
        Thread.sleep(2000L);
        new Thread1().start();

        System.out.println("main thread finish...");
    }
}
