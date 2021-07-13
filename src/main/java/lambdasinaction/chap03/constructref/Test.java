package lambdasinaction.chap03.constructref;

import lambdasinaction.chap03.Apple;
import lambdasinaction.chap03.Color;

import java.util.*;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @version 1.0
 * @Description: 构造函数引用
 * @author: bingyu
 * @date: 2021/7/13
 */
public class Test {



    public List<Apple> map(List<Integer> list, Function<Integer, Apple> f) {
        List<Apple> result = new ArrayList<>();
        for(Integer i: list) {
            result.add(f.apply(i));
        }
        return result;
    }

    public static void main(String[] args) {
        //例1:
        Supplier<Apple> c1 = () -> new Apple();
        Supplier<Apple> c2 = Apple::new; //这里调用的是Apple的无参构造器，因为它会自动匹配，接口Supplier的抽象方法是没有参数的，所以
        //匹配调用的就是Apple的无参构造器
        Apple a1 = c2.get();
        System.out.println(a1); //Apple{color=null, weight=0}

        //例2:
        Function<Integer,Apple> function = Apple::new; //这里调用的是Apple的Apple(int weight)构造器，因为要匹配Function的抽象方法
        Apple apply = function.apply(2);
        System.out.println(apply); //Apple{color=null, weight=2}

        //例3:
        List<Integer> weights = Arrays.asList(7, 3, 4, 10);
        List<Apple> apples = new Test().map(weights, Apple::new); //生成指定重量的苹果列表
        System.out.println(apples); //[Apple{color=null, weight=7}, Apple{color=null, weight=3}, Apple{color=null, weight=4}, Apple{color=null, weight=10}]

        //例4:
        //这里调用的是Apple的Apple(int weight, Color color)构造器,要匹配BiFunction的抽象方法
        BiFunction<Integer, Color,Apple> biFunction = Apple::new;
        Apple apply1 = biFunction.apply(5, Color.GREEN);
        System.out.println(apply1); //Apple{color=GREEN, weight=5}
    }
}
