package com.lotus.java8Learn.lambdaTest;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.lotus.java8Learn.behaviorParameter.BehaviorParameter.filter;
import static org.junit.Assert.assertEquals;

/**
 * Created by zhusheng on 2018/1/2 0002.
 */
public class LambdaTest {

    @Test
    public void testMoveRightBy() throws Exception {
        Point p1 = new Point(5, 5);
        Point p2 = p1.moveRightBy(10);
        assertEquals(15, p2.getX());
        assertEquals(5, p2.getY());
    }

    /**
     * 1.测试可见 Lambda 函数的行为
     * @throws Exception
     */
    @Test
    public void testComparingTwoPoints() throws Exception {
        Point p1 = new Point(10, 15);
        Point p2 = new Point(10, 20);
        int result = Point.compareByXAndThenY.compare(p1 , p2);
        assertEquals(-1, result);
    }

    /**
     * 2.测试使用 Lambda 的方法的行为
     * @throws Exception
     */
    @Test
    public void testMoveAllPointsRightBy() throws Exception{
        List<Point> points =
                Arrays.asList(new Point(5, 5), new Point(10, 5));
        List<Point> expectedPoints =
                Arrays.asList(new Point(15, 5), new Point(20, 5));
        List<Point> newPoints = Point.moveAllPointsRightBy(points, 10);
        assertEquals(expectedPoints, newPoints);
    }


    /**
     * 3.将复杂的 Lambda 表达式分到不同的方法
     * 可能你会碰到非常复杂的Lambda表达式，包含大量的业务逻辑，比如需要处理复杂情况的
     * 定价算法。你无法在测试程序中引用Lambda表达式，这种情况该如何处理呢？一种策略是将
     * Lambda表达式转换为方法引用（这时你往往需要声明一个新的常规方法），我们在8.1.3节详细讨
     * 论过这种情况。这之后，你可以用常规的方式对新的方法进行测试。
     */

    /**
     * 4.高阶函数的测试
     */
    @Test
    public void testFilter() throws Exception{
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4);
        List<Integer> even = filter(numbers, i -> i % 2 == 0);
        List<Integer> smallerThanThree = filter(numbers, i -> i < 3);
        assertEquals(Arrays.asList(2, 4), even);
        assertEquals(Arrays.asList(1, 2), smallerThanThree);
    }
}
