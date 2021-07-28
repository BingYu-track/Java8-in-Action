package lambdasinaction.chap06;

import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;
import static java.util.stream.Collectors.partitioningBy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 将数字按质数和非质数分区(除了1和它本身以外不再有其他因数的自然数)
 */
public class PartitionPrimeNumbers {

  public static void main(String ... args) {
    System.out.println("Numbers partitioned in prime and non-prime: " + partitionPrimes(100));
    Integer candidate = 100;
    boolean prime = isPrime(new ArrayList<Integer>(), candidate);
    System.out.println("Numbers partitioned in prime and non-prime: " + partitionPrimesWithCustomCollector(100));
  }

  //判断待测数是否是质数
  public boolean isPrimeTest1(int candidate) {
    return IntStream.range(2, candidate) //从2开始，直至但不包括待测数
            .noneMatch(i -> candidate % i == 0); //如果待测数字不能被流中任何数字整除则返回true
  }



  /*
    数学思想：
    质数是只有1和本身能整除的整数。所以在求质数的时候，要将素数与1到质数本身中间的所有整数都相除，看是否有整除的数，
    如果有，那肯定不是质数了。但是从算法上考虑，为了减少重复量，开平方后面的数就不用相除了，
    简单来说就是: "如果它不是质数，那么它一定可以表示成两个数（除了1和它本身）相乘，这两个数必然有一个小于等于它的平方根。只要找到小于或等于的那个就行了"
    举例说明: 24的因数有1、2、3、4、6、8、12、24
    按定义应该用2－23去除，但经过分析上面的数可以发现
    1×24、2×12、3×8、4×6 如果2、3、4是某个数的因数，那么另外几个数也是，反之也一样所以为提高效率，
    可以只检查小于该数平方根的那些数，如24的平方根大于4小于5，检查2~4就可以了！
   */

  //判断待测数是否是质数，这里对上一步进行了优化，仅测试小于等于待测数平方根的因子
  public static boolean isPrime(int candidate) {
    return IntStream.rangeClosed(2, candidate-1) //从2开始，直至但不包括待测数
        .limit((long) Math.floor(Math.sqrt(candidate)) - 1) //限制范围数的长度，长度为: 对待测数开根方，再取其整数后减1(Math.sqrt返回的是浮点数)
        .noneMatch(i -> candidate % i == 0);
  }

  /**
   * 使用partitioningBy将前n个自然数按质数和非质数分区
   * @param n
   * @return
   */
  public static Map<Boolean, List<Integer>> partitionPrimes(int n) {
    return IntStream.rangeClosed(2, n).boxed() //装箱
            .collect(partitioningBy(candidate -> isPrime(candidate)));
  }
  /*
    output:
    {
      false=[4, 6, 8, 9, 10, 12, 14, 15, 16, 18, 20, 21, 22, 24, 25, 26, 27, 28, 30,
              32, 33, 34, 35, 36, 38, 39, 40, 42, 44, 45, 46, 48, 49, 50, 51, 52, 54,
              55, 56, 57, 58, 60, 62, 63, 64, 65, 66, 68, 69, 70, 72, 74, 75, 76, 77,
              78, 80, 81, 82, 84, 85, 86, 87, 88, 90, 91, 92, 93, 94, 95, 96, 98, 99, 100],
      true=[2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97]

    }
   */

  //将上面的isPrime(int candidate)方法进一步优化
  public boolean isPrimeTest2(int candidate) {
    int candidateRoot = (int) Math.sqrt((double) candidate); //获取待测数开根方后的整数candidateRoot
    return IntStream.rangeClosed(2, candidateRoot)  //直接从2开始到candidateRoot进行遍历执行
                    .noneMatch(i -> candidate % i == 0);
  }


  /*
    优化点: 仅看被测试数是不是能够被测试数之前的质数整除。
    数学思想: 根据"算数基本定理" 任何一个大于1的自然数 N,如果N不为质数，那么N可以唯一分解成有限个质数的乘积;
    因此如果被测试数无法被分解成质数的乘积，则说明该测试数就是质数，因此仅看被测试数是否能够被测试数之前的质数整除
    就能判断测试数是否是质数了。

    然而我们目前所见的预定义收集器的问题，也就是必须自己开发一个收集器的原因在于，在收集过程中是没有办法访问部分结果的。这意味
    着，当测试某一个数字是否是质数的时候，你没法访问目前已经找到的其他质数的列表。假设你有这个列表，那就可以把它传给isPrime方法
    ，将方法重写如下:
   */
  /**
   * 判断被测数是否是质数
   * @param primes 被测数之前的所有质数集合
   * @param candidate 被测数
   * @return
   */
  public static boolean isPrime(List<Integer> primes, int candidate) {
    return primes.stream().noneMatch(i -> candidate % i == 0);
  }


  /**
   * 判断被测数是否是质数（这里进行了进一步优化，结合了开方算法和"算数基本定理"，仅仅用小于被测数平方根的质数来测试）
   * @param primes 被测数之前的所有质数集合
   * @param candidate 待测数
   * @return
   */
  public static boolean isPrime(List<Integer> primes, Integer candidate) {
    double candidateRoot = Math.sqrt(candidate); //被测数开方后的数candidateRoot
    return primes.stream()
                 .takeWhile(i -> i <= candidateRoot) //设置执行条件，即质数要小于等于candidateRoot
                 .noneMatch(i -> candidate % i == 0); //匹配是否有质数能被待测数整除
  } //如果primes为空列表，结果会返回true，因为没有质数列表里没有数字，因此noneMatch(i -> candidate % i == 0)当然就会返回true了


  //如果是Java8，模拟takeWhile
  public static <A> List<A> takeWhile(List<A> list, Predicate<A> p) {
    int i = 0;
    for (A item : list) {
      if (!p.test(item)) { //如果谓词判断为false就停止执行
        return list.subList(0, i);
      }
      i++;
    }
    return list;
  }


  public static Map<Boolean, List<Integer>> partitionPrimesWithCustomCollector(int n) {
    return IntStream.rangeClosed(2, n).boxed().collect(new PrimeNumbersCollector());
  }
  /*
    output:
    {
      false=[4, 6, 8, 9, 10, 12, 14, 15, 16, 18, 20, 21, 22, 24, 25, 26, 27, 28, 30, 32, 33, 34,
              35, 36, 38, 39, 40, 42, 44, 45, 46, 48, 49, 50, 51, 52, 54, 55, 56, 57, 58, 60, 62,
              63, 64, 65, 66, 68, 69, 70, 72, 74, 75, 76, 77, 78, 80, 81, 82, 84, 85, 86, 87, 88,
              90, 91, 92, 93, 94, 95, 96, 98, 99, 100],
      true=[2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97]
    }

   */

  //前面是用PrimeNumbersCollector实现接口实现的收集器，现在使用Stream的collect方法自定义质数收集器
  public Map<Boolean, List<Integer>> partitionPrimesWithCustomCollector2(int n) {
    return IntStream.rangeClosed(2, n).boxed()
            .collect(
                    () -> new HashMap<Boolean, List<Integer>>() {{
                      put(true, new ArrayList<Integer>());
                      put(false, new ArrayList<Integer>());
                    }},
                    (acc, candidate) -> {
                      acc.get( isPrime(acc.get(true), candidate) )
                              .add(candidate);
                    },
                    (map1, map2) -> {
                      map1.get(true).addAll(map2.get(true));
                      map1.get(false).addAll(map2.get(false));
                    });
  } //相比PrimeNumbersCollector类实现的收集器，它的可读性会差一点，可重用性会差一点



  public Map<Boolean, List<Integer>> partitionPrimesWithInlineCollector(int n) {
    return Stream.iterate(2, i -> i + 1).limit(n)
        .collect(
            () -> new HashMap<Boolean, List<Integer>>() {{
              put(true, new ArrayList<Integer>());
              put(false, new ArrayList<Integer>());
            }},
            (acc, candidate) -> {
              acc.get(isPrime(acc.get(true), candidate))
                  .add(candidate);
            },
            (map1, map2) -> {
              map1.get(true).addAll(map2.get(true));
              map1.get(false).addAll(map2.get(false));
            }
        );
  }

}
