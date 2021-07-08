package lambdasinaction.chap02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * 行为参数化--编写能够应对变化的需求的代码
 */
public class FilteringApples {

  public static void main(String... args) {
    List<Apple> inventory = Arrays.asList(
        new Apple(80, Color.GREEN),
        new Apple(155, Color.GREEN),
        new Apple(120, Color.RED));

    // [Apple{color=GREEN, weight=80}, Apple{color=GREEN, weight=155}]
    List<Apple> greenApples = filterApplesByColor(inventory, Color.GREEN);
    System.out.println(greenApples);

    // [Apple{color=RED, weight=120}]
    List<Apple> redApples = filterApplesByColor(inventory, Color.RED);
    System.out.println(redApples);

    // [Apple{color=GREEN, weight=80}, Apple{color=GREEN, weight=155}]
    List<Apple> greenApples2 = filterApples(inventory, new AppleColorPredicate());
    System.out.println(greenApples2);

    // [Apple{color=GREEN, weight=155}]
    List<Apple> heavyApples = filterApples(inventory, new AppleWeightPredicate());
    System.out.println(heavyApples);

    // []
    List<Apple> redAndHeavyApples = filterApples(inventory, new AppleRedAndHeavyPredicate());
    System.out.println(redAndHeavyApples);

    // [Apple{color=RED, weight=120}]
    //我们知道，在上面为了传入不同行为，需要实例化多个ApplePredicate，为了简化代码使用了匿名内部类，但是这样处理仍然不满意
    //后面会详细讲解Lambda表达式来更好的解决这个问题
    List<Apple> redApples2 = filterApples(inventory, new ApplePredicate() {
      @Override
      public boolean test(Apple a) {
        return a.getColor() == Color.RED;
      }
    });
    System.out.println(redApples2);

    //使用Lambda表达式：
    List<Apple> result = filterApples(inventory, (Apple apple) -> Color.RED.equals(apple.getColor()));
  }

  //例1: 筛选绿苹果
  public static List<Apple> filterGreenApples(List<Apple> inventory) {
    List<Apple> result = new ArrayList<>();
    for (Apple apple : inventory) {
      if (apple.getColor() == Color.GREEN) { //选出绿色的苹果
        result.add(apple);
      }
    }
    return result;
  }


  //例2: 把颜色作为参数，可以用来筛选不同颜色的苹果
  public static List<Apple> filterApplesByColor(List<Apple> inventory, Color color) {
    List<Apple> result = new ArrayList<>();
    for (Apple apple : inventory) {
      if (apple.getColor() == color) {
        result.add(apple);
      }
    }
    return result;
  }


  //例3:把重量作为参数，筛选不同重量的苹果
  public static List<Apple> filterApplesByWeight(List<Apple> inventory, int weight) {
    List<Apple> result = new ArrayList<>();
    for (Apple apple : inventory) {
      if (apple.getWeight() > weight) {
        result.add(apple);
      }
    }
    return result;
  }

  //例4(将每一个能想到的属性作为参数进行筛选):上面我们看到两个不同属性的筛选方法都重复了大部分代码，我们要将颜色和重量结合为一个方法
  public static List<Apple> filterApples(List<Apple> inventory, Color color, int weight, boolean flag) {
    List<Apple> result = new ArrayList<>();
    for (Apple apple: inventory) {
      //虽然这样解决了问题，但是解决问题的方式非常笨拙，而且这种方式还不能很好的应对未来变化的需求，如果还需要对苹果的不同属性
      //做筛选，比如大小、形状、产地，在按照这种思路去解决，工作量就会复杂且增大很多，应尽量避免写出这样的代码
      if ((flag && apple.getColor().equals(color)) || (!flag && apple.getWeight() > weight)){
        result.add(apple);
      }
    }
    return result;
  }

  //例5(根据抽象条件进行筛选)：我们在这里进行了行为参数化，让filter方法可以接受ApplePredicate不同的行为，这样，就把迭代和筛选的逻辑分隔开了，
  //可以适应未来多变的需求，后面需要增加苹果新的属性的筛选，我们只需要创建一个类来实现ApplePredicate即可！
  public static List<Apple> filterApples(List<Apple> inventory, ApplePredicate p) {
    List<Apple> result = new ArrayList<>();
    for (Apple apple : inventory) {
      if (p.test(apple)) {
        result.add(apple);
      }
    }
    return result;
  }

  enum Color {
    RED,
    GREEN
  }

  //静态内部类
  public static class Apple {

    private int weight = 0;
    private Color color;

    public Apple(int weight, Color color) {
      this.weight = weight;
      this.color = color;
    }

    public int getWeight() {
      return weight;
    }

    public void setWeight(int weight) {
      this.weight = weight;
    }

    public Color getColor() {
      return color;
    }

    public void setColor(Color color) {
      this.color = color;
    }

    @SuppressWarnings("boxing")
    @Override
    public String toString() {
      return String.format("Apple{color=%s, weight=%d}", color, weight);
    }

  }


  //定义一个苹果属性的接口，ApplePredicate的每个实现都代表着对苹果不同的筛选标准
  interface ApplePredicate {

    boolean test(Apple a);

  }


  static class AppleWeightPredicate implements ApplePredicate {

    @Override
    public boolean test(Apple apple) {
      return apple.getWeight() > 150;
    }

  }

  static class AppleColorPredicate implements ApplePredicate {

    @Override
    public boolean test(Apple apple) {
      return apple.getColor() == Color.GREEN;
    }

  }

  static class AppleRedAndHeavyPredicate implements ApplePredicate {

    @Override
    public boolean test(Apple apple) {
      return apple.getColor() == Color.RED && apple.getWeight() > 150;
    }

  }

}
