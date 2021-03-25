package org.liu3.策略模式;

/**
 * @Author: liutianshuo
 * @Date: 2021/2/4
 */
public interface StrategyMapper<T, R> {

    /**
     * 根据入参获取到对应的策略处理者。可通过 if-else 实现，也可通过 Map 实现。
     * @param param 入参
     * @return 策略处理者
     */
    Handler<T, R> get(T param);
}
