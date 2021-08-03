package lambdasinaction.chap08;

import static java.util.Map.entry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * java8中Map新增的常用方法
 */
public class WorkingWithMap {

  public static void main(String[] args) {
    forEachWithMaps(); //forEach
    sortOnMaps();  //排序
    getOrDefaultOnMaps(); //getOrDefault()方法
  }

  //Mark: 1.forEach方法
  private static void forEachWithMaps() {
    Map<String, Integer> ageOfFriends = Map.of("Raphael", 30, "Olivia", 25, "Thibaut", 26);

    //1.1 使用Map.Entry<K,V>迭代器访问Map中的每一个元素
    for (Map.Entry<String, Integer> entry: ageOfFriends.entrySet()) {
      String friend = entry.getKey();
      Integer age = entry.getValue();
      System.out.println(friend + " is " + age + " years old");
    }

    //1.2 使用forEach()方法访问Map中的每一个元素
    System.out.println("--> Iterating a map with forEach()");
    ageOfFriends.forEach((friend, age) -> System.out.println(friend + " is " + age + " years old"));

  }

  //Mark: 2.排序
  //有两种新的工具可以帮助你对Map 中的键或值排序，它们是: Entry.comparingByValue和Entry.comparingByKey
  private static void sortOnMaps() {
    Map<String, String> favouriteMovies = Map.ofEntries(
            entry("Raphael", "Star Wars"),
            entry("Cristina", "Matrix"),
            entry("Olivia", "James Bond"));

    //按照人名的字母顺序对流中的元素进行排序
    favouriteMovies.entrySet().stream()
            .sorted(Entry.comparingByKey())
            .forEachOrdered(System.out::println); //forEachOrdered()方法实现的功能和forEach()方法基本是一样的，只有在并行流时才会有区别

  }

  //Mark: 3.getOrDefault方法 以接受的第一个参数作为键，第二个参数作为默认值（在Map 中找不到指定的键时，该默认值会作为返回值）：
  private static void getOrDefaultOnMaps() {
    //3.1 在使用Map.ofEntries工厂方法创建Map时，里面是不能存放null元素，否则就会报错，因此favouriteMovies是没有任何null值的
    Map<String, String> favouriteMovies = Map.ofEntries(entry("Raphael", "Star Wars"),
            entry("Olivia", "James Bond"));
    System.out.println(favouriteMovies.getOrDefault("Olivia", "Matrix")); //有键，且值不为null输出James Bond
    System.out.println(favouriteMovies.getOrDefault("Thibaut", "Matrix")); //无键，输出默认值Matrix

    //3.2 测试getOrDefault对null值的处理
    Map<String, String> map = new HashMap<>();
    map.put(null,"Matrix");
    map.put("Olivia", "James Bond");
    map.put("Raphael", null);
    System.out.println(map.getOrDefault("Raphael", "Matrix")); //有键，但值为null，仍然返回的是null值
    System.out.println(map.getOrDefault(null, "hxw")); //键为null值，但值为Matrix，返回Matrix
  }



}
