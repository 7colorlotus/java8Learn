package com.lotus.java8Learn.concurrency;

public class NotifyDemo {
    private static Express express = new Express();

    private static class CheckSite extends Thread{
        @Override
        public void run() {
            express.waitSite();
        }
    }

    private static class CheckKm extends Thread{
        @Override
        public void run() {
            express.waitKM();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for(int i = 0; i < 3; i++){
            new CheckSite().start();
        }

        for(int i = 0; i < 3; i++){
            new CheckKm().start();
        }

        Thread.sleep(100);

        express.changeKm();
    }
}
