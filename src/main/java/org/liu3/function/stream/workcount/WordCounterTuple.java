package org.liu3.function.stream.workcount;

public class WordCounterTuple {

    private boolean lastSpace;//标记上一个词是否是空格
    private int counter; //用于记录当前词数量


    public WordCounterTuple( boolean lastSpace,int counter) {
        this.lastSpace = lastSpace;
        this.counter = counter;

    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public boolean isLastSpace() {
        return lastSpace;
    }

    public void setLastSpace(boolean lastSpace) {
        this.lastSpace = lastSpace;
    }
}
