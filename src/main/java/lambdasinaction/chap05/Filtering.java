package lambdasinaction.chap05;

import static java.util.stream.Collectors.toList;
import static lambdasinaction.chap04.Dish.menu;

import java.util.Arrays;
import java.util.List;

import lambdasinaction.chap04.Dish;

/**
 *
 */
public class Filtering {

  public static void main(String... args) {
    // Filtering with predicate 筛选操作
    System.out.println("Filtering with a predicate");
    List<Dish> vegetarianMenu = menu.stream()
        .filter(Dish::isVegetarian)
        .collect(toList());
    vegetarianMenu.forEach(System.out::println);

    // Filtering unique elements
    System.out.println("Filtering unique elements:");
    List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
    numbers.stream()
        .filter(i -> i % 2 == 0)
        .distinct()
        .forEach(System.out::println);

    // Slicing a stream  分片流
    // This list is sorted in ascending order of number of calories!
    List<Dish> specialMenu = Arrays.asList(
        new Dish("season fruit", true, 120, Dish.Type.OTHER),
        new Dish("rice", true, 350, Dish.Type.OTHER),
        new Dish("prawns", false, 300, Dish.Type.FISH),
        new Dish("chicken", false, 400, Dish.Type.MEAT),
        new Dish("french fries", true, 530, Dish.Type.OTHER));
    System.out.println("使用Filter对菜单进行筛选:"); //找出小于320卡热量的菜单
    List<Dish> filteredMenu = specialMenu.stream().filter(dish ->
    {
      System.out.println(dish.getName());
      return dish.getCalories() < 320;
    }).collect(toList());
    /*filter遍历的打印结果:
      season fruit
      prawns
      rice
      chicken
      french frie
    */
    System.out.println("filter筛选结果:");
    filteredMenu.forEach(System.out::println);

    System.out.println("使用takeWhile()对菜单进行筛选:"); //找出小于320卡热量的菜单
    List<Dish> slicedMenu1 = specialMenu.stream()
        .takeWhile(dish -> {
          System.out.println(dish.getName());
          return dish.getCalories() < 320; ////一旦遇到元素的断言为false就停止处理
        })
        .collect(toList());
    /*takeWhile方法遍历打印的结果:
      season fruit
      rice
      重点: 你可以看到，takeWhile一旦遇到第一个大于320卡路里的rice时，就停止了遍历，而前面的filter是遍历了所有的元素
    */
    System.out.println("takeWhile的筛选结果:");
    slicedMenu1.forEach(System.out::println);

    System.out.println("使用dropWhile()对菜单进行筛选:"); //找出大于320卡热量的菜单
    List<Dish> slicedMenu2 = specialMenu.stream()
        .dropWhile(dish -> {
          System.out.println(dish.getName());
          return dish.getCalories() < 320;  //一旦遇到元素的断言为false也和takewhile一样就停止处理，但不同的是，dropwhile会丢弃该元素，并返回所有剩余的元素
        }).collect(toList());
    /*dropWhile方法遍历打印的结果:
      season fruit
      rice


     */
    System.out.println("dropWhile的筛选结果:");
    slicedMenu2.forEach(System.out::println);

    // Truncating a stream
    List<Dish> dishesLimit3 = menu.stream()
        .filter(d -> d.getCalories() > 300)
        .limit(3)
        .collect(toList());
    System.out.println("Truncating a stream:");
    dishesLimit3.forEach(System.out::println);

    // Skipping elements
    List<Dish> dishesSkip2 = menu.stream()
        .filter(d -> d.getCalories() > 300)
        .skip(2)
        .collect(toList());
    System.out.println("Skipping elements:");
    dishesSkip2.forEach(System.out::println);
  }

}
