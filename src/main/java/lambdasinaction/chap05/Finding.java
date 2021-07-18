package lambdasinaction.chap05;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lambdasinaction.chap04.Dish;

/**
 * 查找和匹配
 */
public class Finding {

  public static void main(String... args) {
    //anyMatch
    if (isVegetarianFriendlyMenu()) {
      System.out.println("Vegetarian friendly");
    }

    //allMatch
    System.out.println(isHealthyMenu());
    //noneMatch
    System.out.println(isHealthyMenu2());

    //findAny
    Optional<Dish> dish = findVegetarianDish();
    dish.ifPresent(d -> System.out.println(d.getName()));

    //findFirst
    //找出第一个平方能被3整除的数
    List<Integer> someNumbers = Arrays.asList(1, 2, 3, 4, 5);
    Optional<Integer> firstSquareDivisibleByThree =
            someNumbers.stream()
                    .map(n -> n * n)
                    .filter(n -> n % 3 == 0)
                    .findFirst(); // 9
  }

  private static boolean isVegetarianFriendlyMenu() {
    return Dish.menu.stream().anyMatch(Dish::isVegetarian);
  }

  private static boolean isHealthyMenu() {
    return Dish.menu.stream().allMatch(d -> d.getCalories() < 1000);
  }

  private static boolean isHealthyMenu2() {
    return Dish.menu.stream().noneMatch(d -> d.getCalories() >= 1000);
  }

  private static Optional<Dish> findVegetarianDish() {
    return Dish.menu.stream().filter(Dish::isVegetarian).findAny();
  }

}
