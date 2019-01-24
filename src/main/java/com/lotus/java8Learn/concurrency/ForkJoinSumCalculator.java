package com.lotus.java8Learn.concurrency;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

/*
*
 * 用分支/合并框架执行并行求和
 *
 * 使用分支/合并框架的最佳做法
 *   对一个任务调用 join 方法会阻塞调用方，直到该任务做出结果。因此，有必要在两个子
     任务的计算都开始之后再调用它。否则，你得到的版本会比原始的顺序算法更慢更复杂，
     因为每个子任务都必须等待另一个子任务完成才能启动。
 *   不应该在 RecursiveTask 内部使用 ForkJoinPool 的 invoke 方法。相反，你应该始终直
    接调用 compute 或 fork 方法，只有顺序代码才应该用 invoke 来启动并行计算。
 *   对子任务调用 fork 方法可以把它排进 ForkJoinPool 。同时对左边和右边的子任务调用
     它似乎很自然，但这样做的效率要比直接对其中一个调用 compute 低。这样做你可以为
     其中一个子任务重用同一线程，从而避免在线程池中多分配一个任务造成的开销。
 *   调试使用分支/合并框架的并行计算可能有点棘手。特别是你平常都在你喜欢的IDE里面
     看栈跟踪（stack trace）来找问题，但放在分支合并计算上就不行了，因为调用 compute
     的线程并不是概念上的调用方，后者是调用 fork 的那个。
 *   和并行流一样，你不应理所当然地认为在多核处理器上使用分支/合并框架就比顺序计
     算快。我们已经说过，一个任务可以分解成多个独立的子任务，才能让性能在并行化时
     有所提升。所有这些子任务的运行时间都应该比分出新任务所花的时间长；一个惯用方
     法是把输入/输出放在一个子任务里，计算放在另一个里，这样计算就可以和输入/输出
     同时进行。此外，在比较同一算法的顺序和并行版本的性能时还有别的因素要考虑。就
     像任何其他Java代码一样，分支/合并框架需要“预热”或者说要执行几遍才会被JIT编
     译器优化。这就是为什么在测量性能之前跑几遍程序很重要，我们的测试框架就是这么
     做的。同时还要知道，编译器内置的优化可能会为顺序版本带来一些优势（例如执行死
     码分析——删去从未被使用的计算）。
     对于分支/合并拆分策略还有最后一点补充：你必须选择一个标准，来决定任务是要进一步
     拆分还是已小到可以顺序求值。
*/
public class ForkJoinSumCalculator
        extends java.util.concurrent.RecursiveTask<Long> {

    public static void main(String[] args){
        int n = 101;
        long[] numbers = LongStream.rangeClosed(1, n).toArray();
        ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
        Long result = new ForkJoinPool().invoke(task); //同步调用
        System.out.println("result:" + result);
    }

    private final long[] numbers;
    private final int start;
    private final int end;
    public static final long THRESHOLD = 10_000;

    public ForkJoinSumCalculator(long[] numbers) {
        this(numbers, 0, numbers.length);
    }

    private ForkJoinSumCalculator(long[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    /*@Override
    protected Long compute() {
        int length = end - start;
        if (length <= THRESHOLD) {
            return computeSequentially();
        }
        ForkJoinSumCalculator leftTask =
                new ForkJoinSumCalculator(numbers, start, start + length / 2);
        leftTask.fork();
        ForkJoinSumCalculator rightTask =
                new ForkJoinSumCalculator(numbers, start + length / 2, end);
        Long rightResult = rightTask.compute();
        Long leftResult = leftTask.join();
        return leftResult + rightResult;
    }*/

    @Override
    protected Long compute() {
        int length = end - start;
        if (length <= THRESHOLD) {
            return computeSequentially();
        }
        ForkJoinSumCalculator leftTask =
                new ForkJoinSumCalculator(numbers, start, start + length / 2);
        ForkJoinSumCalculator rightTask =
                new ForkJoinSumCalculator(numbers, start + length / 2, end);
        invokeAll(leftTask, rightTask);
        return leftTask.join() + rightTask.join();
    }

    /**
     * 正常的统计方法
     * @return
     */
    private long computeSequentially() {
        long sum = 0;
        for (int i = start; i < end; i++) {
            {
                sum += numbers[i];
            }
        }
        return sum;
    }
}