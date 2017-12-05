package com.lotus.java8Learn.collect;

import com.lotus.java8Learn.entity.Dish;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static java.util.stream.Collector.Characteristics.CONCURRENT;
import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;

/**
 * 自定义ToListCollector
 * @param <T>
 */
public class ToListCollector<T> implements Collector<T, List<T>, List<T>> {
    /**
     * supplier 方法必须返回一个结果为空的 Supplier ，也就是一个无参数函数，在调用时它会
     * 创建一个空的累加器实例，供数据收集过程使用。
     * @return
     */
    @Override
    public Supplier<List<T>> supplier() {
        return ArrayList::new;
    }

    /**
     * accumulator 方法会返回执行归约操作的函数。当遍历到流中第n个元素时，这个函数执行
     * 时会有两个参数：保存归约结果的累加器（已收集了流中的前 n-1 个项目），还有第n个元素本身。
     * @return
     */
    @Override
    public BiConsumer<List<T>, T> accumulator() {
        return List::add;
    }

    /**
     * 在遍历完流后， finisher 方法必须返回在累积过程的最后要调用的一个函数，以便将累加
     * 器对象转换为整个集合操作的最终结果。
     * 通常，就像 ToListCollector 的情况一样，累加器对
     * 象恰好符合预期的最终结果，因此无需进行转换。所以 finisher 方法只需返回 identity 函数
     * @return
     */
    @Override
    public Function<List<T>, List<T>> finisher() {
        return Function.identity();
    }

    /**
     * 四个方法中的最后一个—— combiner 方法会返回一个供归约操作使用的函数，它定义了对
     * 流的各个子部分进行并行处理时，各个子部分归约所得的累加器要如何合并。
     * @return
     */
    @Override
    public BinaryOperator<List<T>> combiner() {
        return (list1, list2) -> {
            list1.addAll(list2);
            return list1;
        };
    }

    /**
     * 最后一个方法—— characteristics 会返回一个不可变的 Characteristics 集合，它定义
     * 了收集器的行为——尤其是关于流是否可以并行归约，以及可以使用哪些优化的提示。
     * Characteristics 是一个包含三个项目的枚举。
     *   UNORDERED ——归约结果不受流中项目的遍历和累积顺序的影响。
     *   CONCURRENT —— accumulator 函数可以从多个线程同时调用，且该收集器可以并行归
     * 约流。如果收集器没有标为 UNORDERED ，那它仅在用于无序数据源时才可以并行归约。
     *   IDENTITY_FINISH ——这表明完成器方法返回的函数是一个恒等函数，可以跳过。这种
     * 情况下，累加器对象将会直接用作归约过程的最终结果。这也意味着，将累加器 A 不加检
     * 查地转换为结果 R 是安全的。
     * @return
     */
    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(IDENTITY_FINISH, CONCURRENT));
    }


    public static void main(String[] args){
        List<Dish> dishes = Dish.getMenu().stream().collect(new ToListCollector<Dish>());
        System.out.println(dishes);
        dishes = Dish.getMenu().stream().collect(
                ArrayList::new,
                List::add,
                List::addAll
        );
        System.out.println(dishes);
    }
}