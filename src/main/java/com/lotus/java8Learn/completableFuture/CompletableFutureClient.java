package com.lotus.java8Learn.completableFuture;

import com.lotus.java8Learn.completableFuture.model.Discount;
import com.lotus.java8Learn.completableFuture.model.Quote;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * 并行——使用流还是 CompletableFutures ？
 *
 * 目前为止，你已经知道对集合进行并行计算有两种方式：要么将其转化为并行流，利用 map
 * 这样的操作开展工作，要么枚举出集合中的每一个元素，创建新的线程，在 Completable-
 * Future 内对其进行操作。后者提供了更多的灵活性，你可以调整线程池的大小，而这能帮助
 * 你确保整体的计算不会因为线程都在等待I/O而发生阻塞。
 * 我们对使用这些API的建议如下。
 * ❑如果你进行的是计算密集型的操作，并且没有I/O，那么推荐使用 Stream 接口，因为实
 * 现简单，同时效率也可能是最高的（如果所有的线程都是计算密集型的，那就没有必要
 * 创建比处理器核数更多的线程）。
 * ❑反之，如果你并行的工作单元还涉及等待I/O的操作（包括网络连接等待），那么使用
 * CompletableFuture 灵活性更好，你可以像前文讨论的那样，依据等待/计算，或者
 * W/C的比率设定需要使用的线程数。这种情况不使用并行流的另一个原因是，处理流的
 * 流水线中如果发生I/O等待，流的延迟特性会让我们很难判断到底什么时候触发了等待。
 *
 * Created by zhusheng on 2018/1/3 0003.
 */
public class CompletableFutureClient {
    List<Shop> shops = Arrays.asList(new Shop("BestPrice"),
            new Shop("LetsSaveBig"),
            new Shop("MyFavoriteShop"),
            new Shop("BuyItAll"));


    /**
     * 采用顺序查询所有商店的方式实现的 findPrices 方法
     * @param product
     * @return
     */
    public List<String> findPrices(String product) {
        return shops.stream()
                .map(shop -> String.format("%s price is %.2f",
                        shop.getName(), shop.getPrice(product)))
                .collect(toList());
    }

    /**
     * 使用并行流对请求进行并行操作
     * @param product
     * @return
     */
    public List<String> findPricesConcurrent(String product) {
        return shops.parallelStream()
                .map(shop -> String.format("%s price is %.2f",
                        shop.getName(), shop.getPrice(product)))
                .collect(toList());
    }

    /**
     * 使用 CompletableFuture 发起异步请求
     * @param product
     * @return
     */
    public List<String> findPricesFactoryMethod(String product) {
        List<CompletableFuture<String>> priceFutures =
                shops.stream()
                        .map(shop -> CompletableFuture.supplyAsync(
                                () -> shop.getName() + " price is " +
                                        shop.getPrice(product)))
                        .collect(Collectors.toList());
        return priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(toList());
    }

    /**
     * Java程序无法终止或者退出一个正
     * 在运行中的线程，所以最后剩下的那个线程会由于一直等待无法发生的事件而引发问题。与此相
     * 反，如果将线程标记为守护进程，意味着程序退出时它也会被回收。这二者之间没有性能上的差
     * 异。
     */
    private final Executor executor =
            Executors.newFixedThreadPool(Math.min(shops.size(), 100),
                    new ThreadFactory() {
                        public Thread newThread(Runnable r) {
                            Thread t = new Thread(r);
                            t.setDaemon(true);
                            return t;
                        }
                    });

    /**
     * 使用定制线程池
     * @param product
     * @return
     */
    public List<String> findPricesByCustomeExecustor(String product) {
        List<CompletableFuture<String>> priceFutures =
                shops.stream()
                        .map(shop -> CompletableFuture.supplyAsync(
                                () -> shop.getName() + " price is " +
                                        shop.getPrice(product),executor))
                        .collect(Collectors.toList());
        return priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(toList());
    }

    /**
     * 使用 Discount 服务
     * @param product
     * @return
     */
    public List<String> findPricesByDiscount(String product) {
        return shops.stream()
                .map(shop -> shop.getPriceForDiscount(product))
                .map(Quote::parse)
                .map(Discount::applyDiscount)
                .collect(toList());
    }

    /**
     * 使用 Discount 服务,以异步方式重新实现 findPrices 方法
     * @param product
     * @return
     */
    public List<String> findPricesByDiscountUseAsyc(String product) {
        List<CompletableFuture<String>> priceFutures =
                shops.stream()
                        .map(shop -> CompletableFuture.supplyAsync(
                                () -> shop.getPriceForDiscount(product), executor))
                        .map(future -> future.thenApply(Quote::parse))
                        .map(future -> future.thenCompose(quote ->
                                CompletableFuture.supplyAsync(
                                        () -> Discount.applyDiscount(quote), executor)))
                        .collect(toList());
        return priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(toList());
    }


    private static final Random random = new Random();
    public static void randomDelay() {
        int delay = 500 + random.nextInt(2000);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 响应 CompletableFuture 的 completion 事件
     * @param product
     * @return
     */
    public Stream<CompletableFuture<String>> findPricesStream(String product) {
        return shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPriceForDiscount(product), executor))
                .map(future -> future.thenApply(Quote::parse))
                .map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executor)));
    }

    public static void main(String args[]) {
        long start = System.nanoTime();
        System.out.println(new CompletableFutureClient().findPricesByDiscountUseAsyc("myPhone27S"));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");

        System.out.println( Runtime.getRuntime().availableProcessors());//CPU的核数

        //
        CompletableFuture[] futures = new CompletableFutureClient().findPricesStream("myPhone")
                .map(f -> f.thenAccept(System.out::println))
                .toArray(size -> new CompletableFuture[size]);
        CompletableFuture.allOf(futures).join();

        System.out.println();System.out.println();System.out.println();

        //打印每个价格计算所消耗的时间
        long start2 = System.nanoTime();
        CompletableFuture[] futures2 = new CompletableFutureClient().findPricesStream("myPhone27S")
                .map(f -> f.thenAccept(
                        s -> System.out.println(s + " (done in " +
                                ((System.nanoTime() - start) / 1_000_000) + " msecs)")))
                .toArray(size -> new CompletableFuture[size]);
        CompletableFuture.allOf(futures2).join();
        System.out.println("All shops have now responded in "
                + ((System.nanoTime() - start2) / 1_000_000) + " msecs");
    }
}
