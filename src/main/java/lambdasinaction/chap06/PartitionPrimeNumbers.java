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
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 将数字按质数和非质数分区(除了1和它本身以外不再有其他因数的自然数)
 */
public class PartitionPrimeNumbers {

  public static void main(String ... args) {
    System.out.println("Numbers partitioned in prime and non-prime: " + partitionPrimes(100));
    System.out.println("Numbers partitioned in prime and non-prime: " + partitionPrimesWithCustomCollector(100));
  }

  //判断待测数是否是质数
  public boolean isPrime1(int candidate) {
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
    可以只检查小于该数平方根的那些数，如24的平方根大于4小于5，检查2－4就可以了！
   */

  //判断待测数是否是质数，这里对上一步进行了优化，仅测试小于等于待测数平方根的因子
  public static boolean isPrime(int candidate) {
    return IntStream.rangeClosed(2, candidate-1) //从2开始，直至但不包括待测数
        .limit((long) Math.floor(Math.sqrt(candidate)) - 1) //限制范围数的长度，长度为: 对待测数开根方，再取其整数后减1(Math.sqrt返回的是浮点数)
        .noneMatch(i -> candidate % i == 0);
  }

  /**
   * 判断2到n这个数范围内，进行分区，哪些是质数，哪些不是质数
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


  public static Map<Boolean, List<Integer>> partitionPrimesWithCustomCollector(int n) {
    return IntStream.rangeClosed(2, n).boxed().collect(new PrimeNumbersCollector());
  }



  public static boolean isPrime(List<Integer> primes, Integer candidate) {
    double candidateRoot = Math.sqrt(candidate);
    //return takeWhile(primes, i -> i <= candidateRoot).stream().noneMatch(i -> candidate % i == 0);
    return primes.stream().takeWhile(i -> i <= candidateRoot).noneMatch(i -> candidate % i == 0);
  }

/*
  public static <A> List<A> takeWhile(List<A> list, Predicate<A> p) {
    int i = 0;
    for (A item : list) {
      if (!p.test(item)) {
        return list.subList(0, i);
      }
      i++;
    }
    return list;
  }
*/

  public static class PrimeNumbersCollector
      implements Collector<Integer, Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> {

    @Override
    public Supplier<Map<Boolean, List<Integer>>> supplier() {
      return () -> new HashMap<>() {{
        put(true, new ArrayList<Integer>());
        put(false, new ArrayList<Integer>());
      }};
    }

    @Override
    public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {
      return (Map<Boolean, List<Integer>> acc, Integer candidate) -> {
        acc.get(isPrime(acc.get(true), candidate))
            .add(candidate);
      };
    }

    @Override
    public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {
      return (Map<Boolean, List<Integer>> map1, Map<Boolean, List<Integer>> map2) -> {
        map1.get(true).addAll(map2.get(true));
        map1.get(false).addAll(map2.get(false));
        return map1;
      };
    }

    @Override
    public Function<Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> finisher() {
      return i -> i;
    }

    @Override
    public Set<Characteristics> characteristics() {
      return Collections.unmodifiableSet(EnumSet.of(IDENTITY_FINISH));
    }

  }

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
