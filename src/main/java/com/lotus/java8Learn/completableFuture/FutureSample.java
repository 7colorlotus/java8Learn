package com.lotus.java8Learn.completableFuture;

import java.util.concurrent.*;

/**
 * Created by zhusheng on 2018/1/3 0003.
 */
public class FutureSample {
    public static void main(String[] args){
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<Double> future = executor.submit(new Callable<Double>() {
            public Double call() {
                return doSomeLongComputation();
            }});
        doSomethingElse();
        try {
            Double result = future.get(1, TimeUnit.SECONDS);
        } catch (ExecutionException ee) {
            // 计算抛出一个异常
            System.out.println("计算抛出一个异常");
        } catch (InterruptedException ie) {
            // 当前线程在等待过程中被中断
            System.out.println("当前线程在等待过程中被中断");
        } catch (TimeoutException te) {
            // 在Future对象完成之前超过已过期
            System.out.println("在Future对象完成之前超过已过期");
        }
    }

    public static void doSomethingElse(){
        System.out.println("doSomethingElse......");
        return ;
    }

    public static Double doSomeLongComputation(){
        System.out.println("doSomeLongComputation......");
        try{
            Thread.sleep(1000);
        }catch (Exception e){

        }

        return 0d;
    }
}
