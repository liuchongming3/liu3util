package org.liu3.function.stream.workcount;

import java.util.function.BinaryOperator;

public class WordCountCombiner implements BinaryOperator<WordCounterTuple> {

    @Override
    public WordCounterTuple apply(WordCounterTuple t, WordCounterTuple u) {
        return new WordCounterTuple(t.isLastSpace(),t.getCounter()+u.getCounter());
    }
}
