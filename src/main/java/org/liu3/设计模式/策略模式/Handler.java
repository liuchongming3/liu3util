package org.liu3.设计模式.策略模式;

/**
 * @Author: liutianshuo
 * @Date: 2021/1/26
 */
public interface Handler<T, R> {

    Handler DEFAULT = t -> null;

    /**
     * apply strategy
     * @param param
     * @return
     */
    R apply(T param);
}
