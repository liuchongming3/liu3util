package org.liu3.设计模式.代理模式.cglib;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @Author: liutianshuo
 * @Date: 2021/6/4
 */
public class MyMethodInterceptor implements MethodInterceptor {

    public static void main(String[] args) {

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloConcrete.class);
        enhancer.setCallback(new MyMethodInterceptor());


        /**
         * 生成的对象继承了HelloConcrete，实现了org.springframework.cglib.proxy.Factory接口
         * 如果手动让HelloConcrete实现Factory接口,
         * 则运行时会报java.lang.ClassFormatError-->Duplicate method name的异常
         */

        HelloConcrete hello = (HelloConcrete)enhancer.create();
        System.out.println(hello.sayHello("I love you!"));

        Class cls = hello.getClass();
        Field[] fields = cls.getDeclaredFields();
        Method[] methods = cls.getDeclaredMethods();
        Class<?>[] ifs = cls.getInterfaces();
        System.out.println(cls);
        //org.springframework.cglib.proxy.Factory
    }


    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("You said: " + Arrays.toString(objects));
        return methodProxy.invokeSuper(o, objects);
    }
}
