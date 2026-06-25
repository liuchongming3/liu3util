package org.liu3.designpattern.strategy;

/**
 * @Author: liutianshuo
 * @Date: 2021/1/26
 */
public class Router1 extends Router<String, StringBuilder> {

    @Override
    protected StrategyMapper<String, StringBuilder> registerStrategyMapper() {
        return null;
    }
}
