package org.liu3.设计模式.策略模式;

/**
 * @Author: liutianshuo
 * @Date: 2021/1/26
 */
public class Handler1 implements Handler<String, StringBuilder> {

    @Override
    public StringBuilder apply(String param) {
        return new StringBuilder(param+"-1");
    }
}
