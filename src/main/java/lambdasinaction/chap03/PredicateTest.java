package lambdasinaction.chap03;

import java.util.*;
import java.util.function.Predicate;

/**
 * @version 1.0
 * @Description: Predicate<T>
 * @author: bingyu
 * @date: 2021/7/11
 */
public class PredicateTest {

    public <T> List<T> filter(List<T> list, Predicate<T> p) {
        List<T> results = new ArrayList<>();
        for(T t: list) {
            if(p.test(t)) {
                results.add(t);
            }
        }
        return results;
    }

    public static void main(String[] args) {
        PredicateTest predicateTest = new PredicateTest();
        List<String> listOfStrings = Arrays.asList("1", "2", "3");
        Predicate<String> nonEmptyStringPredicate = (String s) -> !s.isEmpty(); //这里就是实现test方法的一个具体实例
        List<String> nonEmpty = predicateTest.filter(listOfStrings, nonEmptyStringPredicate);
    }
}
