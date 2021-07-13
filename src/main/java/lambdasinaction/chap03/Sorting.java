package lambdasinaction.chap03;


import java.awt.*;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;

/**
 * 方法引用
 */
public class Sorting {

  static class AppleComparator implements Comparator<Apple> {

    @Override
    public int compare(Apple a1, Apple a2) {
      return a1.getWeight() - a2.getWeight();
    }

  }

  public static void main(String... args) {
    // 1
    List<Apple> inventory = new ArrayList<>();
    inventory.addAll(Arrays.asList(
            new Apple(80, Color.GREEN),
            new Apple(155, Color.GREEN),
            new Apple(120, Color.RED)
    ));

    inventory.sort(new AppleComparator());
    System.out.println(inventory);

    // reshuffling things a little
    inventory.set(1, new Apple(30, Color.GREEN)); //向index=1的位置放入绿苹果

    //1.使用匿名内部类排序
    inventory.sort(new Comparator<Apple>() {

      @Override
      public int compare(Apple a1, Apple a2) {
        return a1.getWeight() - a2.getWeight();
      }
    });
    System.out.println(inventory);

    // reshuffling things a little
    inventory.set(1, new Apple(20, Color.RED));

    //2.使用lambda排序
    inventory.sort((a1, a2) -> a1.getWeight() - a2.getWeight());
    System.out.println(inventory);

    //3.使用Comparator的comparing方法提取Comparator
    Function<Apple, Integer> appleIntegerFunction = (Apple a) -> a.getWeight(); //Function的抽象方法接口: R apply(T t);
    Comparator<Apple> c = Comparator.comparing(appleIntegerFunction);
    inventory.sort(c);
    //简化
    inventory.sort(Comparator.comparing((Apple a) -> a.getWeight()));

    //4.使用方法引用
    inventory.sort(Comparator.comparing(Apple::getWeight));
    //逆序排列
    inventory.sort(Comparator.comparing(Apple::getWeight).reversed());

    inventory.sort(Comparator.comparing(Apple::getWeight)
            .reversed()
            .thenComparing(Apple::getCountry)); //两个苹果一样重时，进一步按国家排序

    // reshuffling things a little
    inventory.set(1, new Apple(10, Color.RED));

    //3.使用"方法引用" 排序
    //TODO: 重点理解
    Function<Apple, Integer> getWeight = Apple::getWeight;
    Function<Apple, Integer> getWeight2 = (Apple p) -> p.getWeight();
    Comparator<Apple> com = Comparator.comparing(getWeight);
    Comparator<Apple> com1 = Comparator.comparing(getWeight2);
    inventory.sort(com); //这个Apple::getWeight方法引用其实就是(Apple apple) -> apple.getWeight()的快捷写法
    System.out.println(inventory);

    //1.静态方法引用例如：
    Integer.parseInt("1");
    BiFunction<String, Integer, Integer> stringIntegerIntegerBiFunction = Integer::parseInt;

    //指向任意类型实例方法的引用
    String str = "123";
    str.length();
    List<String> strs = Arrays.asList("a","b","A","B");
    Comparator<String> stringComparator = (String s1, String s2) -> s1.compareToIgnoreCase(s2);
    Comparator<String> compareToIgnoreCase = String::compareToIgnoreCase;
    strs.sort(String::compareToIgnoreCase);
    //strs.sort(String::length);

  }

  public static void test(int i) {

  }

}
