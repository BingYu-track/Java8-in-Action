package lambdasinaction.chap03;

import static java.util.Comparator.comparing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

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

    // reshuffling things a little
    inventory.set(1, new Apple(10, Color.RED));

    //3.使用"方法引用" 排序
    inventory.sort(comparing(Apple::getWeight));
    System.out.println(inventory);
  }

}
