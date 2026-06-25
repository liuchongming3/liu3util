package org.liu3.function;

/**
 * @Author: liutianshuo
 * @Date: 2021/2/23
 */
@FunctionalInterface
public interface FunctionEx<T, U> {

    U apply(T t) throws Exception;

}
