package org.liu3.动态调用;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * 有两个不同类型的对象,都有一个相同名字和参数的方法,
 * 那么使用MethodHandle就可以统一去调用方法,
 * 不必有父类继承或接口实现的关系
 * @Author: liutianshuo
 * @Date: 2021/2/22
 */
public class MethodHandleTest {

    public static void main(String[] args) {
        try{
            Object o1 = System.out;
            Object o2 = new Print();

            MethodHandle handle1 = getHandle(o1, o1.getClass(), "println", void.class, String.class);
            MethodHandle handle2 = getHandle(o2, o2.getClass(), "println", void.class, String.class);

            handle1.invoke("obj1");
            handle2.invoke("obj2");

        }catch (Throwable e){
            e.printStackTrace();
        }
    }

    public static MethodHandle getHandle(Object obj, Class methodClass, String methodName, Class returnType, Class... paramsType) throws NoSuchMethodException, IllegalAccessException {
        MethodType type = MethodType.methodType(returnType, paramsType);
        /*
         * lookup()方法来自于MethodHandles.lookup，这句的作用是在指定类中查找符合给定的方法名称、方法类型，并且符合调用权限的方法句柄
         * 因为这里调用的是一个虚方法，按照Java语言的规则，方法第一个参数是隐式的，代表该方法的接收者，也即是this指向的对象，
         * 这个参数以前是放在参数列表中进行传递的，而现在提供了bindTo()方法来完成这件事情
         */
        MethodHandle handle = MethodHandles.lookup().findVirtual(methodClass, methodName, type);
        MethodHandle bind = handle.bindTo(obj);
        return bind;
    }
    static public class Print{
        public void println(String s){
            System.out.println(s);
        }
    }
}
