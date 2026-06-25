package org.liu3.function.stream.workcount;

import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * 一个使用串行和并行方法获取文本中单词数的方法
 * https://mp.weixin.qq.com/s/h3W8Ys1pDk82nREQmlzmFw
 */
public class WordCount {

    //Taylor Swift《...Ready For It?》
    static final String SENTENCE_36 = "Knew he was a killer first time that I saw him " +//11
            " Wonder how many girls he had loved and left haunted " +//10
            " But if he's a ghost then I can be a phantom " +//11
            " Holdin' him for ransom ";//4

    public static void main(String[] args) {
        //串行流
        //将String，映射为Character流
        Stream<Character> stream = IntStream
                .range(0, SENTENCE_36.length())
                .mapToObj(SENTENCE_36::charAt);

        //WordCount.countWords(stream);

        /**
         * 并行,不使用自定义的WorkCountSpliterator而直接使用parallel方式会统计错误
         * 问题出在并行时会对文本进行拆分
         */
        WorkCountSpliterator spliter=new WorkCountSpliterator(SENTENCE_36);
        Stream<Character> streamParallel = StreamSupport.stream(spliter, true);
        WordCount.countWordsParallel(streamParallel);

    }

    /**
     * 使用流的reduce方法执行
     */
    public  static int countWords(Stream<Character> stream) {
        WordCounterTuple  wordCounter = stream.reduce(
                new WordCounterTuple( true,0),
                new WordCountAccumulator(),
                new WordCountCombiner()
        );
        System.out.println(wordCounter.getCounter());
        return wordCounter.getCounter();
    }

    public  static int countWordsParallel(Stream<Character> stream) {
        WordCounterTuple  wordCounter = stream.parallel().reduce(
                new WordCounterTuple( true,0),
                new WordCountAccumulator(),
                new WordCountCombiner());
        System.out.println(wordCounter.getCounter());
        return wordCounter.getCounter();
    }
}
