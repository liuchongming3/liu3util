package org.liu3.function.stream;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @Author: liutianshuo
 * @Date: 2021/3/24
 */
public class ReduceTest {

    public static void main(String[] args) {
        //test1();
//        test2();
//        test3();
        String a = "abcdefg";
        IntStream is = IntStream.range(0, a.length());
        Stream<Character> st = is.mapToObj(a::charAt);
        st.forEach(t -> System.out.println(t));

        Stream<Character> charStream = IntStream
                .range(0, a.length())
                .mapToObj(a::charAt);

    }


    public static void test1(){
        ArrayList<Integer> list = Lists.newArrayList(1,2,3,4,5,6,7,8,9);
        int sum = list.stream().reduce(0, (x,y) -> x+y);
        //sum = list.stream().reduce(0, Integer::sum);
        //sum = list.parallelStream().reduce(0, Integer::sum);
        System.out.println(sum);

    }


    public static void test2(){
        ArrayList<Integer> list = Lists.newArrayList(1,2,3,4,5,6,7,8,9);
//        list = Lists.newArrayList(1,2,3);
//        list = new ArrayList<>();
        //int res = list.stream().reduce(0, (sum, b) -> sum + b,  Integer::sum);
        /**
         * BinaryOperator<U> combiner 参数是在多线程情况下对结果汇总的函数
         */
        int res = list.stream().parallel().reduce(0, ReduceTest::add1,  ReduceTest::add2);
        System.out.println(res);

    }

    public static void test3(){
        List<String> list = Arrays.asList("Geeks", "for", "gfg",
                "GeeksforGeeks", "GeeksQuiz");

        list.stream().mapToInt(str -> str.length()).forEach(System.out::println);
    }

    public static int add1(int a, int b){
        //System.out.println("add1 "+a+" "+b);
        return a+b;
    }
    public static int add2(int a, int b){
        System.out.println("add2 "+a+" "+b);
        return a+b+1;
    }
}
