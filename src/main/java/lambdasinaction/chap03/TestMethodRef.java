package lambdasinaction.chap03;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @version 1.0
 * @Description: 方法引用
 * @author: bingyu
 * @date: 2021/7/12
 */
public class TestMethodRef {

    /**
     *   使用方法引用代替Lambda表达式的要求(类::静态方法名、对象::实例方法名)：
     *   1.Lambda体中调用方法的参数列表与返回值类型，要与函数式接口中抽象方法的函数列表和返回值类型保持一致
     *
     *   使用类名:实例方法名的条件：
     *   2.要求函数式接口的抽象方法的参数要比Lambda中实例方法参数多一个，而且Lambda参数列表中的第一个参数
     *     必须是实例方法的调用者，且其余参数的类型一样
     */

    //类::静态方法名(指向静态方法的方法引用)
    public void test1() {
        //使用Integer的compare静态方法，因为Integer的compare方法与Comparator接口中的抽象方法的参数列表和返回值都一致
        Comparator<Integer> com = (x,y) -> Integer.compare(x,y);

        //上个版本的另一种简化，两者是同等的
        Comparator<Integer> com1 = Integer::compare;
    }



    //对象::实例方法名(指向任意类型实例方法的方法引用)
    public void test2() {
        //第一种:
        Consumer<String> con = (x) -> System.out.println(x);

        //第二种:
        PrintStream ps1 = System.out;
        Consumer<String> con1 = (x) -> ps1.println(x);

        //第三种
        PrintStream ps2 = System.out; //实例
        Consumer<String> con2 = ps2::println;
        Consumer<String> con3 = System.out::println;
        /*
        本例中Consumer的抽象方法是void accept(T t);，它与我调用的PrintStream对象的 public void println(String x)方法
        参数列表和返回值类型是一致的
        */

    }

    //类::实例方法名(指向任意类型实例方法的方法引用)
    //使用类::实例方法名是有条件的,它要求函数式接口的抽象方法的参数要比Lambda中实例方法参数多一个，而且Lambda参数列表中的第一个参数
    //必须是实例方法的调用者，而第二个参数是实例方法的参数时，可以使用类名::实例方法名
    public void test3(){
        BiPredicate<String,String> bp = (x,y) -> x.equals(y);
        BiPredicate<String,String> bp1 = String::equals;
        //这里BiPredicate的抽象方法是boolean test(T t, U u);，它比lambda中的实例方法equals(y)要多一个参数，而且
        //这个lambda中的第一个参数就是equals实例方法的调用者，因此可以使用类名::实例方法名

        Function<Apple, Integer> getWeight2 = (Apple p) -> p.getWeight();
        Function<Apple, Integer> getWeight = Apple::getWeight;
        //同样Function的抽象方法是R apply(T t); 它比lambda中的实例方法getWeight()多一个参数，且这个lambda中的第一个参数就是
        //getWeight()方法的调用者，因此，它也可以使用类名::实例方法名
    }
}
