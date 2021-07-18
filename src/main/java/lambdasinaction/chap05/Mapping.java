package lambdasinaction.chap05;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Flow;
import java.util.function.Function;
import java.util.stream.Stream;

import lambdasinaction.chap04.Dish;

/**
 * 映射
 */
public class Mapping{

  public static void main(String... args) {

    /**
     * map
     */
    //1.下面的代码把方法引用Dish::getName 传给了map方法，来提取流中菜肴的名称：
    List<String> dishNames = Dish.menu.stream()
        .map(Dish::getName)
        .collect(toList());
    System.out.println(dishNames);

    //2.给定一个单词列表，想要返回另一个列表，显示每个单词中有几个字母
    List<String> words = Arrays.asList("Hello", "World");
    List<Integer> wordLengths = words.stream()
        .map(String::length)
        .collect(toList());
    System.out.println(wordLengths);

    //3.找出每道菜的名称有多长
    List<Integer> dishNameLengths = Dish.menu.stream()
            .map(Dish::getName)
            .map(String::length)
            .collect(toList());

    /**
     * flatMap
     */
    //1.使用map和Arrays.stream
    words.stream()
            .map(word -> word.split(""))  //将每个单词转换为由其字母构成的数组 Stream<String[]>
            .map(Arrays::stream)//让每个数组变成单独的流  Stream<Stream<String>>
            .distinct()
            .collect(toList());

    //2.flatMap
    words.stream()
        .flatMap((String line) -> Arrays.stream(line.split(""))) //生成的是Stream<String>
        .distinct()
        .forEach(System.out::println);

    //3.flatMap
    //给定两个数字列表，返回所有可能性的数对
    List<Integer> numbers1 = Arrays.asList(1,2,3,4,5);
    List<Integer> numbers2 = Arrays.asList(6,7,8);
    List<int[]> pairs = numbers1.stream()   //Sream<Integer>
                    .flatMap(
                            //1. numbers2.stream().map(j -> new int[]{i, j})  返回数组流Stream<int[]>
                            //2. i -> numbers2.stream().map(j -> new int[]{i, j})满足Function函数的抽象方法
                            // Function<Integer, Stream<int[]>>
                            i -> {  //
                              System.out.println("i:" + i +"---------");
                             return numbers2.stream().map(j -> {  //numbers1每次遍历一个元素，numbers2就需要全部遍历完成，numbers2遍历完成的次数就是numbers1元素的个数
                                System.out.println("j:" + j +"---------");
                                return new int[]{i, j};
                              });
                      }
                    )
                    .collect(toList());                             //返回数组流Stream<int[]>
    Function<Integer, Stream<int[]>> streamFunction = (Integer i) -> numbers2.stream().map(j -> new int[]{i, j});


    //4.flatMap
    //扩展前一个例子，只返回总和能被3 整除的数对
    List<Integer> numbersA = Arrays.asList(1,2,3,4,5);
    List<Integer> numbersB = Arrays.asList(6,7,8);
    List<int[]> pairs2 = numbersA.stream()
        .flatMap(
             (Integer i) -> numbersB.stream()
            .map((Integer j) -> new int[]{i, j})
        )
        .filter(pair -> (pair[0] + pair[1]) % 3 == 0)
        .collect(toList());
    pairs.forEach(pair -> System.out.printf("(%d, %d)", pair[0], pair[1]));
  }

}
