package org.liu3.动态调用;

import java.lang.invoke.*;

/**
 * @Author: liutianshuo
 * @Date: 2021/2/22
 */
public class InvokeDynamicTest {

    public static void main(String[] args) throws Throwable {

        /*
         * 原文章说明:
         * 看一下BootstrapMethod()，
         * 所有逻辑就是调用MethodHandles$Lookup的findStatic()方法，产生testMethod()方法的MethodHandle，
         * 然后用它创建一个ConstantCallSite对象。
         * 最后，这个对象返回给invokedynamic指令实现对testMethod()方法的调用，invokedynamic指令的调用过程到此就宣告完成了。
         */
        MethodHandle handle = invokeMethod();
        //调用指定handle,执行方法
        handle.invokeExact("白日依山尽");


        /* --------------- */

    }

    public static void testMethod(String s){
        System.out.println("动态调用的方法,"+s);
    }

    /**
     * 返回一个InvokeDynamicTest类的,
     * 对应静态方法,指定的方法名,指定的入参和返回值的CallSite对象
     */
    public static CallSite doWork(MethodHandles.Lookup lookup, String name, MethodType type) throws NoSuchMethodException, IllegalAccessException {
        return new ConstantCallSite(lookup.findStatic(InvokeDynamicTest.class, name, type));
    }

    /**
     * 查找一个叫doWork的,对应静态方法的MethodHandle,
     * 用字符串的返回格式指明了doWork方法的入参和返回值类型
     */
    private static MethodHandle getHandle() throws NoSuchMethodException, IllegalAccessException {
        /*
         * 用字符串指定 MethodHandle对应方法的入参和返回值
         * 当然也可以用 MethodType.methodType()来设置
         */
        String descriptor = "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;";
        MethodType type = MethodType.fromMethodDescriptorString(descriptor,null);

        return MethodHandles.lookup().findStatic(InvokeDynamicTest.class, "doWork", type);
    }

    /**
     * 操作是围绕调用InvokeDynamicTest.testMethod方法进行的
     * @return
     * @throws Throwable
     */
    private static MethodHandle invokeMethod() throws Throwable {

        //获取doWork方法相关的MethodHandle对象
        MethodHandle handle1 = getHandle();

        //调用handle1
        CallSite cs = (CallSite) handle1.invokeWithArguments(
                MethodHandles.lookup(),
                "testMethod",
                MethodType.fromMethodDescriptorString("(Ljava/lang/String;)V", null)
        );

        MethodHandle handle2 = cs.dynamicInvoker();

        return handle2;
    }

}
