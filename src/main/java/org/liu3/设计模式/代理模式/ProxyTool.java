package org.liu3.设计模式.代理模式;

import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.lang.reflect.Proxy;

/**
 * 静态代理大体上是代理对象对外宣称有某种功能,
 * 实际实现功能时,由同类型的另外的对象实例来实现,
 * 并且代理对象可以自己再添一点花样,比如收尾动作
 * 这点和装饰模式很像,但装饰模式不要求代理类和被代理类是同类型的
 *
 * @Author: liutianshuo
 * @Date: 2021/4/9
 */
public class ProxyTool {

    public static void main(String[] args) {
        proxy2();
    }

    public static void proxy1(){
        Bird b1 = new Bird();
        //代理对象
        Bird2 b2 = new Bird2(b1);

        b1.fly();

        b2.fly();

    }

    public static void proxy2(){

        //原始对象
        Flyable b1 = new Bird();
        //静态代理对象
        Flyable b2 = new Bird2(b1);
        /*
         * 动态代理对象,可以不是Flyable类型的
         * 只是持有一个Flyable的实例属性
         */
        Bird3 b3 = new Bird3(b1);

        //现在b3这个
        Flyable birdProxy = (Flyable) Proxy.newProxyInstance(
                b1.getClass().getClassLoader(),
                b1.getClass().getInterfaces(),
                b3);
        birdProxy.fly();
        birdProxy.eat("花生");

        Class cls = birdProxy.getClass();
        System.out.println(cls);
        byte[] classFile = ProxyGenerator.generateProxyClass("com.sun.proxy.$Proxy0", cls.getInterfaces());
        String path = "D:/代理类.class";

        try(FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(classFile);
            fos.flush();
            System.out.println("代理类class文件写入成功");
        } catch (Exception e) {
            System.out.println("写文件错误");
        }
    }
}
