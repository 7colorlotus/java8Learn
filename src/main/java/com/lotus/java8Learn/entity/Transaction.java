package com.lotus.java8Learn.entity;

import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

/**
 * 交易
 */
public class Transaction {
    private final Trader trader;
    private final int year;
    private final int value;
    private Currency currency;

    public Transaction(Trader trader, int year, int value) {
        this.trader = trader;
        this.year = year;
        this.value = value;
    }

    public Transaction(Trader trader, int year, int value, Currency currency) {
        this.trader = trader;
        this.year = year;
        this.value = value;
        this.currency = currency;
    }

    public Trader getTrader() {
        return this.trader;
    }

    public int getYear() {
        return this.year;
    }

    public int getValue() {
        return this.value;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String toString() {
        return "{" + this.trader + ", " +
                "year: " + this.year + ", " +
                "currency: " + this.currency + ", " +
                "value:" + this.value + "}";
    }

    public static List<Transaction> getTransactions(){
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario","Milan");
        Trader alan = new Trader("Alan","Cambridge");
        Trader brian = new Trader("Brian","Cambridge");
        return Arrays.asList(
                new Transaction(brian, 2011, 300, Currency.getInstance(Locale.CHINA)),
                new Transaction(raoul, 2012, 1000, Currency.getInstance(Locale.CANADA)),
                new Transaction(raoul, 2011, 400, Currency.getInstance(Locale.US)),
                new Transaction(mario, 2012, 710, Currency.getInstance(Locale.UK)),
                new Transaction(mario, 2012, 700, Currency.getInstance(Locale.US)),
                new Transaction(alan, 2012, 950, Currency.getInstance(Locale.CHINA))
        );
    }

}