package com.lotus.java8Learn.concurrency;

import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * 并行单词计数器
 */
class WordCounterSpliterator implements Spliterator<Character> {
    private final String string;
    private int currentChar = 0;

    public WordCounterSpliterator(String string) {
        this.string = string;
    }

    /**
     * tryAdvance 方法把 String 中当前位置的 Character 传给了 Consumer ，并让位置加一。
     * 作为参数传递的 Consumer 是一个Java内部类，在遍历流时将要处理的 Character 传给了
     * 一系列要对其执行的函数。这里只有一个归约函数，即 WordCounter 类的 accumulate
     * 方法。如果新的指针位置小于 String 的总长，且还有要遍历的 Character ，则
     * tryAdvance 返回 true 。
     * @param action
     * @return
     */
    @Override
    public boolean tryAdvance(Consumer<? super Character> action) {
        action.accept(string.charAt(currentChar++));
        return currentChar < string.length();
    }

    /**
     * trySplit 方法是 Spliterator 中最重要的一个方法，因为它定义了拆分要遍历的数据
     * 结构的逻辑。就像在代码清单7-1中实现的 RecursiveTask 的 compute 方法一样（分支
     * /合并框架的使用方式），首先要设定不再进一步拆分的下限。这里用了一个非常低的下
     * 限——10个 Character ，仅仅是为了保证程序会对那个比较短的 String 做几次拆分。
     * 在实际应用中，就像分支/合并的例子那样，你肯定要用更高的下限来避免生成太多的
     * 任务。如果剩余的 Character 数量低于下限，你就返回 null 表示无需进一步拆分。相
     * 反，如果你需要执行拆分，就把试探的拆分位置设在要解析的 String 块的中间。但我
     * 们没有直接使用这个拆分位置，因为要避免把词在中间断开，于是就往前找，直到找到
     * 一个空格。一旦找到了适当的拆分位置，就可以创建一个新的 Spliterator 来遍历从
     * 当前位置到拆分位置的子串；把当前位置 this 设为拆分位置，因为之前的部分将由新
     * Spliterator 来处理，最后返回。
     * @return
     */
    @Override
    public Spliterator<Character> trySplit() {
        int currentSize = string.length() - currentChar;
        if (currentSize < 10) {
            return null;
        }
        for (int splitPos = currentSize / 2 + currentChar;
             splitPos < string.length(); splitPos++) {
            if (Character.isWhitespace(string.charAt(splitPos))) {
                Spliterator<Character> spliterator =
                        new WordCounterSpliterator(string.substring(currentChar,
                                splitPos));
                currentChar = splitPos;
                return spliterator;
            }
        }
        return null;
    }

    /**
     * 还需要遍历的元素的 estimatedSize 就是这个 Spliterator 解析的 String 的总长度和
     * 当前遍历的位置的差。
     * @return
     */
    @Override
    public long estimateSize() {
        return string.length() - currentChar;
    }

    /**
     * 最后， characteristic 方法告诉框架这个 Spliterator 是 ORDERED （顺序就是 String
     * 中各个 Character 的次序）、 SIZED （ estimatedSize 方法的返回值是精确的）、
     * SUBSIZED （ trySplit 方法创建的其他 Spliterator 也有确切大小）、 NONNULL （ String
     * 中 不 能 有 为 null 的 Character ） 和 IMMUTABLE （ 在 解 析 String 时 不 能 再 添 加
     * Character ，因为 String 本身是一个不可变类）的。
     * @return
     */
    @Override
    public int characteristics() {
        return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
    }
}