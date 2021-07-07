package lambdasinaction.chap01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class FilteringApples {

  public static void main(String... args) {

    List<Apple> inventory = Arrays.asList(
        new Apple(80, "green"),
        new Apple(155, "green"),
        new Apple(120, "red")
    );

    /**
     * 直接传递方法
     */

    //选出绿苹果
    List<Apple> greenApples = filterApples(inventory, FilteringApples::isGreenApple); //是否是绿苹果
    System.out.println(greenApples);

    //选出指定重量的苹果
    List<Apple> heavyApples = filterApples(inventory, FilteringApples::isHeavyApple); //重量是否大于150
    System.out.println(heavyApples);

    //注意上面将isGreenApple和isHeavyApple作为方法传递了进去，因此我们每次还需要定义isGreenApple和isHeavyApple这两个方法
    //下面我们使用->这种Lambda表达式可以直接免除这些方法的定义

    /**
     * 使用Lambda表达式(要注意的是，如果Lambda的长度多于几行，那样可读性会变得比较差，那样的话还是最后定义一个方法来描述，因此在逻辑不是很复杂
     * 的情况下，直接使用Lambda表达式是相当好的选择)
     */
    List<Apple> greenApples2 = filterApples(inventory, (Apple a) -> "green".equals(a.getColor()));
    System.out.println(greenApples2);

    List<Apple> heavyApples2 = filterApples(inventory, (Apple a) -> a.getWeight() > 150);
    System.out.println(heavyApples2);

    List<Apple> weirdApples = filterApples(inventory, (Apple a) -> a.getWeight() < 80 || "brown".equals(a.getColor()));
    System.out.println(weirdApples);
  }

  public static boolean isGreenApple(Apple apple) {
    return "green".equals(apple.getColor());
  }

  public static boolean isHeavyApple(Apple apple) {
    return apple.getWeight() > 150;
  }

  public static List<Apple> filterApples(List<Apple> inventory, Predicate<Apple> p) { //方法作为Predicate参数p传进去，predicate也称为谓词
    List<Apple> result = new ArrayList<>();
    for (Apple apple : inventory) {
      if (p.test(apple)) { //判断苹果是否符合p所代表的条件
        result.add(apple);
      }
    }
    return result;
  }

  public static List<Apple> filterGreenApples(List<Apple> inventory) {
    List<Apple> result = new ArrayList<>();
    for (Apple apple : inventory) {
      if ("green".equals(apple.getColor())) {
        result.add(apple);
      }
    }
    return result;
  }

  public static List<Apple> filterHeavyApples(List<Apple> inventory) {
    List<Apple> result = new ArrayList<>();
    for (Apple apple : inventory) {
      if (apple.getWeight() > 150) {
        result.add(apple);
      }
    }
    return result;
  }



  public static class Apple {

    private int weight = 0;
    private String color = "";

    public Apple(int weight, String color) {
      this.weight = weight;
      this.color = color;
    }

    public int getWeight() {
      return weight;
    }

    public void setWeight(int weight) {
      this.weight = weight;
    }

    public String getColor() {
      return color;
    }

    public void setColor(String color) {
      this.color = color;
    }

    @SuppressWarnings("boxing")
    @Override
    public String toString() {
      return String.format("Apple{color='%s', weight=%d}", color, weight);
    }

  }

}
