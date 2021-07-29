package lambdasinaction.chap07;

import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;

/**
 * 使用JMH测量对前n个自然数求和的函数的性能
 * 注意: 使用maven package打包后才会在target下生成benchmarks.jar的JAR文件，然后使用命令
 *    java -jar ./target/benchmarks.jar ParallelStreamBenchmark
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime) //测量用于执行基准测试目标方法所花费的平均时间
@OutputTimeUnit(TimeUnit.MILLISECONDS) //以毫秒为单位，打印输出基准测试的结果
@Fork(value = 2, jvmArgs = { "-Xms4G", "-Xmx4G" }) //采用4Gb 的堆，执行基准测试两次以获得基准测试的 更可靠的结果
@Warmup(iterations = 3) //预热3轮
@Measurement(iterations = 2) //2轮测试
public class ParallelStreamBenchmark {

  private static final long N = 10_000_000L;

  @Benchmark //基准测试的目标方法
  public long iterativeSum() {
    long result = 0;
    for (long i = 1L; i <= N; i++) {
      result += i;
    }
    return result;
  }

  @Benchmark
  public long sequentialSum() {
    return Stream.iterate(1L, i -> i + 1).limit(N).reduce(0L, Long::sum);
  }

  @Benchmark
  public long parallelSum() {
    return Stream.iterate(1L, i -> i + 1).limit(N).parallel().reduce(0L, Long::sum);
  }

  @Benchmark
  public long rangedSum() {
    return LongStream.rangeClosed(1, N).reduce(0L, Long::sum);
  }

  @Benchmark
  public long parallelRangedSum() {
    return LongStream.rangeClosed(1, N).parallel().reduce(0L, Long::sum);
  }

  @TearDown(Level.Invocation) //尽量在每次基准测试迭代结束后都进行一次垃圾回收
  public void tearDown() {
    System.gc();
  }

  public static void main(String[] args) {
    ParallelStreamBenchmark parallelStreamBenchmark = new ParallelStreamBenchmark();
    long l = parallelStreamBenchmark.sequentialSum();
    System.out.println(l);
  }

  /*
 output:

Benchmark                                  Mode  Cnt    Score    Error  Units
ParallelStreamBenchmark.iterativeSum       avgt    4    3.651 ±  0.132  ms/op
ParallelStreamBenchmark.sequentialSum      avgt    4   75.723 ± 34.910  ms/op
ParallelStreamBenchmark.parallelSum        avgt    4  119.937 ± 15.512  ms/op
ParallelStreamBenchmark.rangedSum          avgt    4    4.501 ±  4.041  ms/op
ParallelStreamBenchmark.parallelRangedSum  avgt    4    5.239 ±  1.021  ms/op


  注意这里Units是每次操作所花费的平均时间，因此，Score打分越高说明每次操作花费的时间越长，这里iterativeSum方法执行最快。
  并行流parallelRangedSum方法执行最慢,而且我们可以看到sequentialSum和parallelSum方法都比其它方法普遍要慢这是为什么？

  原因有2种:
  1.sequentialSum和parallelSum方法使用的都是Stream.iterate方法，而iterate方法会将基本类型的数字装箱,求和时又需要进行拆箱，因此效率会比较低
  2.我们很难把iterate分成多个独立块来并行执行，因为每次应用iterate这个函数都要依赖前一次应用的结果，用并行处理其实是给顺序处理增加了开销，
    因为它还要把每次求和的操作分配到下次一个不同的线程，因此本质上iterate仍是顺序的。(我的理解)

  注意事项:
  错用并行流而产生错误的首要原因，就是使用的算法改变了某些共享状态
  留意装箱 .可以的话尽量使用原始类型的流
  要考虑流背后的数据结构是否易于分解成子块
  一些集合的可分解性
*/
}
