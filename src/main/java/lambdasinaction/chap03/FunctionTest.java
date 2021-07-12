package lambdasinaction.chap03;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @version 1.0
 * @Description: Function<T,R>的apply方法
 * @author: bingyu
 * @date: 2021/7/11
 */
public class FunctionTest {


    public <T, R> List<R> map(List<T> list, Function<T, R> f) {
        List<R> result = new ArrayList<>();
        for(T t: list) {
            result.add(f.apply(t));
        }
        return result;
    }

    public static void main(String[] args) {
        FunctionTest functionTest = new FunctionTest();
        ////根据一个字符串List，生成对应的字符长度list
        List<Integer> l = functionTest.map(
                Arrays.asList("lambdas", "in", "action"),
                (String s) -> s.length()
        );
    }
}
