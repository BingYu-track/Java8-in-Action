package lambdasinaction.chap02;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import lambdasinaction.chap02.FilteringApples.Apple;
import static lambdasinaction.chap02.FilteringApples.Color.RED;

/**
 * @version 1.0
 * @Description: Comparator 来排序
 * @author: bingyu
 * @date: 2021/7/8
 */
public class Test1 {


    public static void main(String[] args) {
        List<Apple> inventory = Arrays.asList(
                new Apple(80, FilteringApples.Color.GREEN),
                new Apple(155, FilteringApples.Color.GREEN),
                new Apple(120, RED));


        inventory.sort(new Comparator<Apple>() {
            public int compare(Apple a1, Apple a2) {
                return Integer.valueOf(a1.getWeight()).compareTo(a2.getWeight());
            }
        });

        //使用Lambda
        inventory.sort(
                (Apple a1, Apple a2) -> Integer.valueOf(a1.getWeight()).compareTo(a2.getWeight()));

    }




}
