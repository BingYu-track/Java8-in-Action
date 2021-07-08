package lambdasinaction.chap02;

import lambdasinaction.chap02.FilteringApples.Apple;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static lambdasinaction.chap02.FilteringApples.Color.RED;

/**
 * @version 1.0
 * @Description: 进一步对将List类型抽象画
 * @author: bingyu
 * @date: 2021/7/8
 */
public class Test {

    public static void main(String[] args) {
        List<FilteringApples.Apple> inventory = Arrays.asList(
                new FilteringApples.Apple(80, FilteringApples.Color.GREEN),
                new FilteringApples.Apple(155, FilteringApples.Color.GREEN),
                new FilteringApples.Apple(120, RED));

        List<Integer> numbers = Arrays.asList(89,34,22);

        List<Apple> redApples = filter(inventory, (Apple apple) -> RED.equals(apple.getColor()));
        List<Integer> evenNumbers = filter(numbers, (Integer i) -> i % 2 == 0);
    }


    //使用泛型将内容进一步抽象化
    public interface Predicate<T>{
        boolean test(T t);
    }
    public static <T> List<T> filter(List<T> list, Predicate<T> p){
        List<T> result = new ArrayList<>();
        for(T e: list){
            if(p.test(e)){
                result.add(e);
            }
        }
        return result;
    }


}
