package com.lotus.java8Learn.completableFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by zhusheng on 2018/1/5 0005.
 */
public class CompletableFutureTest {
    private static final Executor executor =
            Executors.newFixedThreadPool(3,
                    new ThreadFactory() {
                        public Thread newThread(Runnable r) {
                            Thread t = new Thread(r);
                            t.setDaemon(true);
                            return t;
                        }
                    });

    public static Integer str2Upcase(String str){
        return str.length();
    }

    public static void main(String[] args){
        List<String> strList = new ArrayList<>();
        CompletableFuture[] futures = strList.stream()
                .map(str -> CompletableFuture.supplyAsync(() -> str.toUpperCase(),executor))
                .map(future -> future.thenAccept(System.out::println))
                .toArray(size -> new CompletableFuture[size]);
        CompletableFuture.allOf(futures).join();
    }
}
