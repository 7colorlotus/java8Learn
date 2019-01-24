package com.lotus.java8Learn.concurrency;

/**
 * 线程中断的示例
 */
public class InterruptDemo {
    static class InterruptRun implements Runnable{
        @Override
        public void run() {
            Thread currrentThread = Thread.currentThread();
            String threadName = currrentThread.getName();
            while(!currrentThread.isInterrupted()){
                System.out.println("[" + threadName + "] I'm go on ....");
            }
            System.out.println("[" + threadName + "] I'm end !!!");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new InterruptRun());
        t1.start();
        System.out.println(t1.getName() + " start....");
        Thread.sleep(1000);
        t1.interrupt(); //通知线程暂停，但是什么时候暂停由线程自己决定，一般线程会释放资源后结束
        // t1.stop(); //强制暂停，不论现在线程处于运行的什么状态，可能会导致资源不能被释放
        System.out.println(t1.getName() + " call interrupt....");
    }
}
