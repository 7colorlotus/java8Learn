package com.lotus.java8Learn.lazyCalculate;

/**
 * Created by zhusheng on 2018/1/25 0025.
 */
public class LazyClient {

    public static void main(String[] args){
        /*LazyList<Integer> numbers = from(2);
        int two = numbers.head();
        int three = numbers.tail().head();
        int four = numbers.tail().tail().head();
        System.out.println(two + " " + three + " " + four);*/

        /*LazyList<Integer> numbers = from(2);
        int two = primes(numbers).head();
        int three = primes(numbers).tail().head();
        int five = primes(numbers).tail().tail().head();
        System.out.println(two + " " + three + " " + five);*/


//        printAll(primes(from(2)));

        printAll2(primes(from(2)));
    }

    public static LazyList<Integer> from(int n) {
        return new LazyList<>(n, () -> from(n+1));
    }

    public static MyList<Integer> primes(MyList<Integer> numbers) {
        return new LazyList<>(
                numbers.head(),
                () -> primes(
                        numbers.tail()
                                .filter(n -> n % numbers.head() != 0)
                )
        );
    }

    static <T> void printAll(MyList<T> list){
        while (!list.isEmpty()){
            System.out.println(list.head());
            list = list.tail();
        }
    }

    static <T> void printAll2(MyList<T> list){
        if (list.isEmpty())
            return;
        System.out.println(list.head());
        printAll2(list.tail());
    }

}
