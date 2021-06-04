package org.liu3.设计模式.策略模式;

/**
 * @Author: liutianshuo
 * @Date: 2021/1/26
 */
public class HandlerRouter2 extends Router<String, StringBuilder> implements Handler<String, StringBuilder> {

    @Override
    protected StrategyMapper<String, StringBuilder> registerStrategyMapper() {
        return null;
    }

    @Override
    public StringBuilder apply(String param) {
        return null;
    }
}
