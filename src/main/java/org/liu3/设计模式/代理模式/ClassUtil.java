package org.liu3.设计模式.代理模式;

import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;

/**
 * @Author: liutianshuo
 * @Date: 2021/6/4
 */
public class ClassUtil {

    public static void saveClass(Object proxy){
        Class cls = proxy.getClass();
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
