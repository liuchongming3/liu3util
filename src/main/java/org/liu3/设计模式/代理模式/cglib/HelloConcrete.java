package org.liu3.设计模式.代理模式.cglib;

import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Factory;

/**
 * @Author: liutianshuo
 * @Date: 2021/6/4
 */
public class HelloConcrete implements Factory {
    public String sayHello(String str) {
        return "HelloConcrete: " + str;
    }

    @Override
    public Object newInstance(Callback callback) {
        return null;
    }

    @Override
    public Object newInstance(Callback[] callbacks) {
        return null;
    }

    @Override
    public Object newInstance(Class[] classes, Object[] objects, Callback[] callbacks) {
        return null;
    }

    @Override
    public Callback getCallback(int i) {
        return null;
    }

    @Override
    public void setCallback(int i, Callback callback) {

    }

    @Override
    public void setCallbacks(Callback[] callbacks) {

    }

    @Override
    public Callback[] getCallbacks() {
        return new Callback[0];
    }
}
