package org.liu3.设计模式.代理模式;

/**
 * @Author: liutianshuo
 * @Date: 2021/4/9
 */
public class Bird implements Flyable {

    @Override
    public void fly() {
        System.out.println("凤凰台上凤凰游，凤去台空江自流。" +
                "吴宫花草埋幽径，晋代衣冠成古丘。" +
                "三山半落青天外，二水中分白鹭洲。" +
                "总为浮云能蔽日，长安不见使人愁。");
    }

    @Override
    public void eat(String food) {
        System.out.println("吃了:"+food);
    }
}
