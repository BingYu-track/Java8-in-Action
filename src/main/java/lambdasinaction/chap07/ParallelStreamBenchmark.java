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
 * 注意: 使用maven package打包后才会在target下生成benchmarks.jar的JAR文件
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime) //测量用于执行基准测试目标方法所花费的平均时间
@OutputTimeUnit(TimeUnit.MILLISECONDS) //以毫秒为单位，打印输出基准测试的结果
@Fork(value = 2, jvmArgs = { "-Xms4G", "-Xmx4G" }) //采用4Gb 的堆，执行基准测试两次以获得基准测试的 更可靠的结果
@Warmup(iterations = 3) //预热3次
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

}
