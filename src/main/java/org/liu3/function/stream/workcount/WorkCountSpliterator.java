package org.liu3.function.stream.workcount;

import java.util.Spliterator;
import java.util.function.Consumer;

public class WorkCountSpliterator implements Spliterator<Character> {
    private String text;
    private int charIdx;

    public WorkCountSpliterator(String text) {
        super();
        this.text = text;
    }

    @Override
    public boolean tryAdvance(Consumer<? super Character> action) {
        action.accept(text.charAt(charIdx++));
        return charIdx < text.length();
    }

    @Override
    public Spliterator<Character> trySplit() {
        int remainSize = text.length() - charIdx;
        if (remainSize < 30) {//剩余字符串小于30，进行串行处理，不再生产子拆分器
            return null;
        }
        for (int splitpos = (charIdx + remainSize/2); splitpos < text.length(); splitpos++) {//采用折半搜索

            //如果是空格就进行拆分
            if (Character.isWhitespace(text.charAt(splitpos))) {

                String subText = text.substring(charIdx, splitpos);
                Spliterator<Character> subSpliterator = new WorkCountSpliterator(subText);
                charIdx = splitpos;//向前推进缩小范围
                System.out.println("拆分了：subText="+subText+ " || " + subSpliterator);
                return subSpliterator;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "WorkCountSpliterator [needSpliterator="
                + text + ", currentCharAt=" + charIdx + "]";
    }

    @Override
    public long estimateSize() {
        return text.length() - charIdx;
    }

    @Override
    public int characteristics() {
        return ORDERED | SIZED | SUBSIZED | NONNULL | IMMUTABLE;
    }
}
