package org.liu3.设计模式.代理模式;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Author: liutianshuo
 * @Date: 2021/4/9
 */
public class Bird3 implements InvocationHandler {

    public Flyable bird;

    public Bird3(Flyable bird) {
        this.bird = bird;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        //Flyable fly = (Flyable) proxy;


        System.out.println("代理方法:"+method.getName());
        System.out.print("动态代理的调用:");
        Object res = method.invoke(bird, args);

        return res;
    }
}
