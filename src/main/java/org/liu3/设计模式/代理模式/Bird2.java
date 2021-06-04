package org.liu3.设计模式.代理模式;

/**
 * @Author: liutianshuo
 * @Date: 2021/4/9
 */
public class Bird2 implements Flyable {

    private Flyable fly;

    public Bird2(Flyable fly) {
        this.fly = fly;
    }

    @Override
    public void fly() {
        System.out.print("这是一个静态代理类的输出:");
        fly.fly();
    }

    @Override
    public void eat(String food) {
        System.out.print("这是一个静态代理类的输出:");
        fly.eat(food);
    }
}
