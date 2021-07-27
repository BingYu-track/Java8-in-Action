package lambdasinaction.chap06;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;
import static lambdasinaction.chap06.Dish.menu;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 分区
 */
public class Partitioning {

  public static void main(String... args) {
    System.out.println("Dishes partitioned by vegetarian: " + partitionByVegeterian());
    System.out.println("Vegetarian Dishes by type: " + vegetarianDishesByType());
    System.out.println("Most caloric dishes by vegetarian: " + mostCaloricPartitionedByVegetarian());
    System.out.println(test1());
    //System.out.println(test2());
    System.out.println(test3());

  }

  //1.将食物分成素食和非素食
  private static Map<Boolean, List<Dish>> partitionByVegeterian() {
    return menu.stream().collect(partitioningBy(Dish::isVegetarian)); //分区函数partitioningBy
  }
  //output: {false=[pork, beef, chicken, prawns, salmon], true=[french fries, rice, season fruit, pizza]}


  //2.在分区后的基础上，对食物进行分类
  private static Map<Boolean, Map<Dish.Type, List<Dish>>> vegetarianDishesByType() {
    return menu.stream().collect(partitioningBy(Dish::isVegetarian, //分区函数
                            groupingBy(Dish::getType))); //第二个收集器
  }
  //output: {false={MEAT=[pork, beef, chicken], FISH=[prawns, salmon]}, true={OTHER=[french fries, rice, season fruit, pizza]}}


  //3.找到素食和非素食中热量最高的菜
  private static Object mostCaloricPartitionedByVegetarian() {
    return menu.stream().collect(
        partitioningBy(Dish::isVegetarian, //分区函数
            collectingAndThen(  //collectingAndThen收集器
                maxBy(comparingInt(Dish::getCalories)),  //归约收集器
                    Optional::get))); //转换函数
  }
  //output: {false=pork, true=pizza}

  //4.多级分区
  //4.1 在将食物区分成素食和非素食的基础上进行二次分区，区分为热量大于500和小于500的食物
  private static Map<Boolean, Map<Boolean, List<Dish>>> test1(){
    Map<Boolean, Map<Boolean, List<Dish>>> collect = menu.stream().collect(partitioningBy(Dish::isVegetarian,
            partitioningBy(d -> d.getCalories() > 500)));
    return collect;
  }
  //output: {false={false=[chicken, prawns, salmon], true=[pork, beef]}, true={false=[rice, season fruit], true=[french fries, pizza]}}

  //4.2
//  private static Object test2(){
//    return menu.stream().collect(partitioningBy(Dish::isVegetarian,
//            partitioningBy(Dish::getType))); //不符合抽象方法签名
//  }

  //4.3 算出素食和非素食分表有多少种菜
  private static Map<Boolean, Long> test3(){
    Map<Boolean, Long> collect = menu.stream().collect(partitioningBy(Dish::isVegetarian,
            counting()));
    return collect;
  }
  //output: {false=5, true=4}


}
