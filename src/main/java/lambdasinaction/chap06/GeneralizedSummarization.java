package lambdasinaction.chap06;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.reducing;
import static lambdasinaction.chap06.Dish.menu;
/**
 * @version 1.0
 * @Description: 广义汇总
 * @author: bingyu
 * @date: 2021/7/23
 */
public class GeneralizedSummarization {

    public static void main(String[] args) {

        //方法一: 使用Collectors.reducing三个参数的方法找出最高热量的菜
        /*
         第一个参数是归约操作的起始值，也是流中没有元素时的返回值，所以很显然对于数值和而言0 是一个合适的值。
         第二个参数就是一个转换函数，将菜肴转换成一个表示其所含热量的int。
         第三个参数是一个BinaryOperator，将两个项目累积成一个同类型的值，也就是累积函数。这里它就是对两个int 求和
         */

        int totalCalories = menu.stream().collect(
                reducing(0, Dish::getCalories, (i, j) -> i + j));

        //方法二: 第3个参数不适用lambda表达式
        totalCalories = menu.stream().collect(reducing(0,
                Dish::getCalories,
                Integer::sum));

        //方法三: 使用下面这样只有一个参数形式的reducing方法来找到热量最高的菜
        //它把流中的第一个元素作为起点，把恒等函数（即一个函数仅仅是返回其输入参数）作为一个转换函数。这
        //也意味着，要是把单参数reducing方法生成的Collector传递给空流的collect方法，收集器就没有起点；因此它返回一个Optional<Dish>对象。
        Optional<Dish> mostCalorieDish = menu.stream().collect(reducing(
                (d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2));
        System.out.println(mostCalorieDish);

        /*
            Collector.reducing和Stream的reduce方法的区别
         */
        Stream<Integer> stream = Arrays.asList(1, 2, 3, 4, 5, 6).stream();
        //实现toList Collector 所做的工作
        //1.如果用2个参数的reduce方法，参数1和参数2泛型都必须与Stream中的泛型一致
//        List<Integer> numbers = stream.reduce(
//                new ArrayList<Integer>(), //初始值必须是Integer
//                (List<Integer> l, Integer e) -> {  //累加器中的2个参数也必须是Integer
//                    l.add(e);
//                    return l; //这里要返回Integer
//                }
//
//        );
//        System.out.println(numbers);

        //2.使用3个参数的reduce方法。3个参数的方法泛型是方法泛型，不会被Stream里的泛型影响
        //Mark: 还要注意的是这里并没有使用到第三个参数，第三个参数的作用是在并行计算下合并各个线程的计算结果，因为此时不是并发状态，所以第三个参数不会出现打印
        //还有如果使用该方法在并发状态下使用的话会出现问题，因为我们在内部改变了累加器内部的状态，这就是collect 方法特别适合表达可变
        //容器上的归约的原因，更关键的是它适合并行操作
        List<Integer> numbers = stream.reduce(
                new ArrayList<Integer>(), //初始值
                (List<Integer> l, Integer e) -> {  //累加器
                    l.add(e); //修改了内部状态
                    return l;
                },
                (List<Integer> l1, List<Integer> l2) -> { //组合器
                    System.out.println("l1:" + l1 + "  l2:" + l2);
                    l1.addAll(l2);
                    return l1;
                }
        );
        System.out.println(numbers);


    }
}
