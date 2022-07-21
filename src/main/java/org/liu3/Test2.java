package org.liu3;

//import io.netty.util.HashedWheelTimer;

/**
 * @Author: liutianshuo
 * @Date: 2021/6/11
 */
public class Test2 {
    public void print(){
        System.out.println("Test2-2");


    }

    public static void main(String[] args) {
        R1 r1 = new R1("t1");
        R1 r2 = new R1("t2");
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        r1.setT(t2);
        t1.start();
        t2.start();

//        HashedWheelTimer h;

        System.out.println("main");
    }

}

class R1 implements Runnable {

    String name;
    Thread t;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Thread getT() {
        return t;
    }

    public void setT(Thread t) {
        this.t = t;
    }

    public R1(String name){
        this.name=name;
    }

    @Override
    public void run() {
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(name+" run");
    }
}