package lambdasinaction.chap07;

import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * 并行流
 */
public class ParallelStreams {


  /**  顺序流求和
   * 接受数字n 作为参数，并返回从1 到给定参数的所有数字的和
   * @param n
   * @return
   */
  public static long sequentialSum(long n) {
    return Stream.iterate(1L, i -> i + 1).limit(n).reduce(Long::sum).get();
  }

  //传统java代码求和
  public static long iterativeSum(long n) {
    long result = 0;
    for (long i = 0; i <= n; i++) {
      result += i;
    }
    return result;
  }

  /**  并行流求和
   * 接受数字n 作为参数，并返回从1 到给定参数的所有数字的和
   * @param n
   * @return
   */
  public static long parallelSum(long n) {
    return Stream.iterate(1L, i -> i + 1)
                 .limit(n)
                 .parallel() //将流转换为并行流
                 .reduce(Long::sum).get();
  }

  public static long rangedSum(long n) {
    return LongStream.rangeClosed(1, n).reduce(Long::sum).getAsLong();
  }

  public static long parallelRangedSum(long n) {
    return LongStream.rangeClosed(1, n).parallel().reduce(Long::sum).getAsLong();
  }

  public static long sideEffectSum(long n) {
    Accumulator accumulator = new Accumulator();
    LongStream.rangeClosed(1, n).forEach(accumulator::add);
    return accumulator.total;
  }

  /*
    这里将上一个方法改为并行
   */
  public static long sideEffectParallelSum(long n) {
    Accumulator accumulator = new Accumulator();
    LongStream.rangeClosed(1, n).parallel().forEach(accumulator::add);
    return accumulator.total;
  }


  /**
   * 自定义静态内部类累加器
   */
  public static class Accumulator {

    private long total = 0; //累加变量

    public void add(long value) {
      total += value;
    }

  }

}
