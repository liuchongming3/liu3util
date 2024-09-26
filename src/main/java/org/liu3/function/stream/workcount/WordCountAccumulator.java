package org.liu3.function.stream.workcount;

import java.util.function.BiFunction;

public class WordCountAccumulator implements BiFunction<WordCounterTuple, Character, WordCounterTuple> {

    @Override
    public WordCounterTuple apply(WordCounterTuple lastWordCounterTuple, Character currentChar) {

        if (Character.isWhitespace(currentChar)) {
            return new WordCounterTuple(true, lastWordCounterTuple.getCounter());
        } else {
            int count = lastWordCounterTuple.getCounter();
            //如果不是空白字符,则字符总数+1
            if(lastWordCounterTuple.isLastSpace()){
                count++;
            }
            return new WordCounterTuple(false, count);
        }
    }

}
