package com.lotus.java8Learn.concurrency;

public class Express{
      public  static String SITE = "ShangHai";
      public static Long KM = 100L;

      public synchronized void waitSite(){
          String threadName = Thread.currentThread().getName();
          if(SITE.equals("ShangHai")){
              try {
                  System.out.println("[" + threadName + "] waitSite ...");
                  wait();
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
          }
          System.out.println("[" + threadName + "] Express site has been changed ...");
      }

      public synchronized void waitKM(){
          String threadName = Thread.currentThread().getName();
          if(KM <= 100){
              try {
                  System.out.println("[" + threadName + "] waitKM ...");
                  wait();
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
          }
          System.out.println("[" + threadName + "] Express km has been changed ...");
      }

    public  synchronized void changeKm() {
          KM = 101l;
        notify();
    }

    public synchronized void changeSite() {
        SITE = "Beijing";
        notify();
    }
}