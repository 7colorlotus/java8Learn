package com.lotus.java8Learn.concurrency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多个线程实现有序执行
 */
public class SequenceThread {

    public static void main(String[] args) throws Exception{
        useCountDownLatch();
    }

    /**
     * 使用线程的优先级无法实现  XX
     */
    public static void usePriority(){
        Thread t1 = new Thread(()->{
            System.out.println("线程1执行了。。。。");
        },"线程1");

        Thread t2 = new Thread(()->{
            System.out.println("线程2执行了。。。。");
        },"线程2");

        Thread t3 = new Thread(()->{
            System.out.println("线程3执行了。。。。");
        },"线程3");

        t1.setPriority(1);

        t2.setPriority(2);

        t3.setPriority(3);

        t1.start();

        t2.start();

        t3.start();
    }

    /**
     * 利用线程的join
     */
    public static void useThreadJoin()throws Exception{
        System.out.println("第一种方式，使用线程的join方法");
        Thread t1 = new Thread(()->{
            System.out.println("线程1执行了。。。。");
        },"线程1");

        Thread t2 = new Thread(()->{
            System.out.println("线程2执行了。。。。");
        },"线程2");

        Thread t3 = new Thread(()->{
            System.out.println("线程3执行了。。。。");
        },"线程3");
        t1.start();
        t1.join();
        t2.start();
        t2.join();
        t3.start();
        t3.join();
        System.out.println("所有线程都结束。。。");
    }

    /**
     * 使用newSingleThreadExecutor线程池，不严格
     */
    public static void useThreadPool(){
        System.out.println("第二种方式，使用newSingleThreadExecutor线程池");
        ExecutorService es = Executors.newSingleThreadExecutor();
        es.submit(()->{
            System.out.println("Runable 1执行了。。。。");
        });

        es.submit(()->{
            System.out.println("Runable 2执行了。。。。");
        });

        es.submit(()->{
            System.out.println("Runable 3执行了。。。。");
        });
        es.shutdown();
        System.out.println("第二种方式所有Runable线程都结束。。。");
    }

    /**
     * 第三种方式，使用自定义计序方式，繁琐
     */
    public static void useAtomicCount(){
        System.out.println("第三种方式，使用自定义计序方式");
        AtomicInteger count = new AtomicInteger(0);
        Thread t4 = new Thread(new Task(0,"线程4", count));
        Thread t5 = new Thread(new Task(1,"线程5", count));
        Thread t6 = new Thread(new Task(2,"线程6", count));
        t4.start();
        t5.start();
        t6.start();

        while (true) {
            if(!(t4.isAlive() || t5.isAlive() || t6.isAlive())){
                System.out.println("第三种方式所有线程都结束。。。");
                break;
            }
        }
    }

    /**
     * 使用countDownLatch
     */
    public static void useCountDownLatch() throws InterruptedException {
        CountDownLatch  countDownLatch1 = new CountDownLatch(1);
        Thread t1 = new Thread(()->{
            System.out.println("线程1执行了。。。。");
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownLatch1.countDown();
        },"线程1");
        t1.start();
        countDownLatch1.await();

        CountDownLatch countDownLatch2 = new CountDownLatch(1);
        Thread t2 = new Thread(()->{
            System.out.println("线程2执行了。。。。");
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownLatch2.countDown();
        },"线程2");
        t2.start();
        countDownLatch2.await();

        CountDownLatch countDownLatch3 = new CountDownLatch(1);
        Thread t3 = new Thread(()->{
            System.out.println("线程3执行了。。。。");
            countDownLatch3.countDown();
        },"线程3");
        t3.start();
        countDownLatch3.await();
    }
}



class Task implements Runnable{
    private int order;
    public AtomicInteger count;
    private String threadName;


    public Task(int order,String threadName, AtomicInteger count){
        this.order = order;
        this.threadName = threadName;
        this.count = count;
    }
    @Override
    public void run() {
        while (true){
            if(order == count.get() % 3){
                System.out.println(threadName + "被执行了");
                count.incrementAndGet();
                break;//只执行一次
            }
        }
    }
}
