package com.lotus.java8Learn.concurrency;

import java.util.Spliterator;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by zhusheng on 2017/12/14 0014.
 */
public class WordCounterClient {
    static final String SENTENCE =
            " Nel mezzo del cammin di nostra vita " +
                    "mi ritrovai in una selva oscura" +
                    " ché la dritta via era smarrita ";

    public static void main(String[] args) {
        System.out.println("Found " + countWordsIteratively(SENTENCE) + " words");

        Stream<Character> stream = IntStream.range(0, SENTENCE.length())
                .mapToObj(SENTENCE::charAt);
        System.out.println("Found " + countWords(stream) + " words");


        Spliterator<Character> spliterator = new WordCounterSpliterator(SENTENCE);
        Stream<Character> stream2 = StreamSupport.stream(spliterator, true);
        System.out.println("Found " + countWords(stream2) + " words");
    }

    /**
     * 使用函数式方法
     * @param stream
     * @return
     */
    private static int countWords(Stream<Character> stream) {
        WordCounter wordCounter = stream.reduce(new WordCounter(0, true),
                WordCounter::accumulate,
                WordCounter::combine);
        return wordCounter.getCounter();
    }


    /**
     * 使用传统方式统计单词
     * @param s
     * @return
     */
    public static int countWordsIteratively(String s) {
        int counter = 0;
        boolean lastSpace = true;
        for (char c : s.toCharArray()) {
            if (Character.isWhitespace(c)) {
                lastSpace = true;
            } else {
                if (lastSpace) counter++;
                lastSpace = false;
            }
        }
        return counter;
    }
}
