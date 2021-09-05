package lambdasinaction.chap03;

import java.util.function.Consumer;

/**
 * @version 1.0
 * @Description: 函数式接口里的andThen()方法
 * @author: bingyu
 * @date: 2021/9/5
 */
public class TestAndThen {

    public static void main(String[] args) {
         Consumer<Integer> consumer = x -> System.out.println(x);
         Consumer<Integer> consumer2 = x -> {
                 int a = x + 4;
                 System.out.println(a);
             };
         //andThen方法是返回一个"先调用了consumer的方法，再调用consumer2的方法"的一个函数
        //在这里consumer3里就调用了consumer和consumer2方法
        Consumer<Integer> consumer3 = consumer.andThen(consumer2);
        consumer3.accept(10);
    }
}
