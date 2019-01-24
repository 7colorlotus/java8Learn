package com.lotus.java8Learn.concurrency;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 演示Semaphore, 可以用来设计连接池
 */
public class SemaphoreDemo {
    public static final int POOL_SIZE = 5;
    static volatile  Semaphore semaphore = new Semaphore(POOL_SIZE);
    private static  AtomicInteger unused = new AtomicInteger(POOL_SIZE);
    static LinkedList<String> connPool = new LinkedList();

    static {
        connPool.addFirst("conn1");
        connPool.addFirst("conn2");
        connPool.addFirst("conn3");
        connPool.addFirst("conn4");
        connPool.addFirst("conn5");
    }

    /**
     * 获取连接
     * @return
     */
    public static String getConn(){
        String result = null;
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        result = connPool.removeLast();
        System.out.println("获取连接后剩余" + unused.decrementAndGet() + "个连接");
        return result;
    }

    /**
     * 释放连接
     * @return
     */
    public static void releaseConn(String conn){
        if(null == conn){
            return ;
        }
        connPool.addFirst(conn);
        semaphore.release();
        System.out.println("释放连接后剩余" + unused.incrementAndGet() + "个连接");
    }

    /**
     * 获取连接线程
     */
    static class GetConnThread extends Thread{
        long startTime;

        public GetConnThread(long startTime){
            this.startTime = startTime;
        }
        public void run(){
            String threadName = Thread.currentThread().getName();
            String conn = getConn();
            while(conn == null){
                conn = getConn();
            }
            System.out.println("线程："+threadName+"获取连接：" + conn +",耗时：" + (System.currentTimeMillis() - startTime));

            try {
                Thread.sleep(5 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            releaseConn(conn);
            System.out.println("线程："+threadName+"释放连接");
        }
    }

    public static void main(String[] args) {
        for(int i = 0; i < 10; i++ ){
            new GetConnThread(System.currentTimeMillis()).start();
        }
    }

}
