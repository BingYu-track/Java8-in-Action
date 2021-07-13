package lambdasinaction.chap03.arrayref;

import java.util.function.Function;

/**
 * @version 1.0
 * @Description: 数组引用
 * @author: bingyu
 * @date: 2021/7/13
 */
public class Test {

    public static void main(String[] args) {
        Function<Integer,String[]> fun = (x) -> new String[x];
        String[] strs = fun.apply(10);
        System.out.println(strs.length);

        Function<Integer,String[]> fun2 = String[]::new;
        String[] strs2 = fun2.apply(20);
        System.out.println(strs2.length);
    }
}
