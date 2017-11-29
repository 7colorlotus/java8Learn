package com.lotus.java8Learn.UseStream;

import com.lotus.java8Learn.entity.Trader;
import com.lotus.java8Learn.entity.Transaction;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.IntBinaryOperator;
import java.util.stream.Collectors;

/**
 *(1) 找出2011年发生的所有交易，并按交易额排序（从低到高）。
 *(2) 交易员都在哪些不同的城市工作过？
 *(3) 查找所有来自于剑桥的交易员，并按姓名排序。
 *(4) 返回所有交易员的姓名字符串，按字母顺序排序。
 *(5) 有没有交易员是在米兰工作的？
 *(6) 打印生活在剑桥的交易员的所有交易额。
 *(7) 所有交易中，最高的交易额是多少？
 *(8) 找到交易额最小的交易。
 * Created by zhusheng on 2017/11/29 0029.
 */
public class UserStreamPractise {
    static Trader raoul = new Trader("Raoul", "Cambridge");
    static Trader mario = new Trader("Mario","Milan");
    static Trader alan = new Trader("Alan","Cambridge");
    static Trader brian = new Trader("Brian","Cambridge");
    static List<Transaction> transactions = Arrays.asList(
            new Transaction(brian, 2011, 300),
            new Transaction(raoul, 2012, 1000),
            new Transaction(raoul, 2011, 400),
            new Transaction(mario, 2012, 710),
            new Transaction(mario, 2012, 700),
            new Transaction(alan, 2012, 950)
    );

    public static void main(String[] args){
        //(1) 找出2011年发生的所有交易，并按交易额排序（从低到高）。
        List<Transaction> transactionList = transactions.stream()
                                                        .filter(transaction -> transaction.getYear() == 2011)
                                                        .sorted(Comparator.comparing(Transaction::getValue))
                                                        .collect(Collectors.toList());
        System.out.println(transactionList);

        //(2) 交易员都在哪些不同的城市工作过？
        List<String> citys = transactions.stream()
                                            .map(transaction -> transaction.getTrader().getCity())
                                            .distinct()
                                            .collect(Collectors.toList());
        System.out.println(citys);

        //(3) 查找所有来自于剑桥的交易员，并按姓名排序。
        List<Trader> traders = transactions.stream()
                .map(transaction -> transaction.getTrader())
                .filter(trader -> trader.getCity().equals("Cambridge"))
                .distinct()
                .sorted(Comparator.comparing(Trader::getName))
                .collect(Collectors.toList());
        System.out.println(traders);

        //(4) 返回所有交易员的姓名字符串，按字母顺序排序。
        List<String> nameList = transactions.stream()
                                            .map(transaction -> transaction.getTrader().getName())
                                            .distinct()
                                            .sorted(String::compareTo)
                                            .collect(Collectors.toList());
        System.out.println(nameList);

        //(5) 有没有交易员是在米兰工作的？
        boolean milanTrade = transactions.stream().anyMatch(transaction -> transaction.getTrader().getCity().equals("Milan"));
        System.out.println("has trade work in Millan?"+milanTrade);


        //(6) 打印生活在剑桥的交易员的所有交易额。
        transactions.stream().filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                                .map(Transaction::getValue)
                               .forEach(System.out::println);

        //(7) 所有交易中，最高的交易额是多少？
        Optional<Integer> maxTradeValue = transactions.stream().map(Transaction::getValue).reduce(Integer::max);
        if(maxTradeValue.isPresent()){
            System.out.println("Max trade value is " + maxTradeValue.get());
        }

        //(8) 找到交易额最小的交易。
        Optional<Transaction> transactionOptional =  transactions.stream().min(Comparator.comparing(Transaction::getValue));
        if(transactionOptional.isPresent()){
            System.out.println("min value of Transaction  is " + transactionOptional.get());
        }
    }
}
