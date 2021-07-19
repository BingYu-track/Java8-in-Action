package lambdasinaction.chap05;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lambdasinaction.chap04.Dish;

/**
 * 归约操作
 */
public class Reducing {

  public static void main(String... args) {
    //1. 元素求和
    List<Integer> numbers = Arrays.asList(3, 4, 5, 1, 2);
    //reduce方法 参数1:累积函数的初始值  参数2:用于将2个值组合成一个值的lambda表达式
    int sum = numbers.stream().reduce(0, (a, b) -> a + b); //这里就是0+3=3 、3+4=7........
    System.out.println(sum);
    //Mark: 无初始值时返回Optional对象
    Optional<Integer> reduce = numbers.stream().reduce((a, b) -> a + b);

    //2. 元素求和，使用了"类::静态方法"引用
    int sum2 = numbers.stream().reduce(0, Integer::sum);
    System.out.println(sum2);

    //3.获取最大值
    int max = numbers.stream().reduce(0, (a, b) -> Integer.max(a, b));
    System.out.println(max);

    Optional<Integer> min = numbers.stream().reduce(Integer::min);
    min.ifPresent(System.out::println);

    int calories = Dish.menu.stream()
        .map(Dish::getCalories)
        .reduce(0, Integer::sum);
    System.out.println("Number of calories:" + calories);
  }

}
