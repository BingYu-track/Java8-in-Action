package lambdasinaction.chap07;

/**
 * @version 1.0
 * @Description: 有关JMH基准测试的打印信息详解
 * @author: bingyu
 * @date: 2021/7/28
 */
public class JMHTestInfo {

 /*
    JMH常用注解:
    Mark: 注解@BenchmarkMode
    @BenchmarkMode用于指定当前 Benchmark 方法使用哪种模式测试。JMH 提供了4种不同的模式，用于输出不同的结果指标
        1.Throughput(吞吐量模式)，简称: thrpt，单位: ops/time。测量的是单位时间执行操作的次数。
          通过连续调用Benchmark方法，计算出所有工作线程的吞吐量总和。
        2.AverageTime(平均时间模式)，简称: avgt，单位: time/op。测量的是执行每个操作的平均时间。
          通过连续调用Benchmark方法，计算出所有工作线程上的平均调用时间。
        3.SampleTime(采样时间模式)，简称: sample，单位: time/op。测量的是每个操作的时间。通过连续调用Benchmark方法，
          并随机采样调用所需的时间。该模式是基于时间的测试，将会持续运行直到到达迭代截止时间。该模式会自动调整采样的频率，
          由于丢失采样可能会忽略一些暂停时间。
        4.SingleShotTime(单次调用时间模式)，简称: ss，单位: time/op。测量的是单次执行操作的时间。只调用一次
          Benchmark方法，测量其运行时间。

        注意: 前面的几种测试模式，都会先进行热身，然后再进行测试。这样，可以防止受启动和运行波动的影响。
             而单次调用时间模式则不同，测试时并不会进行热身。该模式，对于想在启动期间、运行不稳定的情况下
             测量操作时间，或者测量每一次操作时间时很有用。

    Mark: 注解@OutputTimeUnit
    @OutputTimeUnit来指定时间单位，可以精确到纳秒级别

    Mark: 注解@Warmup和Measurement
    @Warmup和Measurement分别用于配置预热迭代和测试迭代，它们有四种同样样的属性
    iterations 用于指定迭代次数
    time 指定每次迭代要花的时间
    timeUnit 指定每次迭代的时间单位
    batchSize  指定每次操作需要调用目标方法的次数
    为什么需要预热?因为 JVM 的 JIT 机制的存在，如果某个函数被调用多次之后，JVM 会尝试将其编译成为机器码从而提高执行速度。
    为了让Benchmark的结果更加接近真实情况就需要进行预热

    Mark: 注解@State
    @State用来指定指测试范围
    Scop.Benchmark   集准测试范围(Benchmark),所有的Benchmark线程一起共享实例
    Scop.Group    同一个Group的线程可以享有同样的实例
    Scop.Thread  默认的State，会为每个测试线程分配一个实例，相互隔离，线程之间不会产生影响

    Mark: 注解@Fork
    @Fork用来表示fork多少个线程,如果fork数是2的话，则JMH会fork出两个进程来进行测试。

    Mark: 注解@Setup
    方法注解，会在执行 benchmark 之前被执行，正如其名，主要用于初始化

    Mark: @TearDown
    方法注解，与@Setup 相对的，会在所有 benchmark 执行结束以后执行，主要用于资源的回收等
    @Setup/@TearDown注解使用Level参数来指定何时调用fixture:

    Level.Trial 默认level。全部benchmark运行(一组迭代)之前/之后
    Level.Iteration 一次迭代之前/之后(一组调用)
    Level.Invocation 每个方法调用之前/之后(不推荐使用，除非你清楚这样做的目的)
  */



/*
JMH会在每个标有@Benchmark注解的方法上打印信息，我们以ParallelStreamBenchmark类的parallelSum()方法为例
1.头部分打印信息主要是此次运行的环境和配置，包括JDK、JMH版本，基准测试的配置
# JMH version: 1.21
# VM version: JDK 11.0.11, Java HotSpot(TM) 64-Bit Server VM, 11.0.11+9-LTS-194
# VM invoker: C:\Program Files\Java\jdk-11.0.11\bin\java.exe
# VM options: -Xms4G -Xmx4G
# Warmup: 3 iterations, 10 s each  ---------------预热3次迭代，每次迭代10s
# Measurement: 2 iterations, 10 s each  ------------正式测试2次迭代，每次迭代10s
# Timeout: 10 min per iteration -------------------每次迭代的超时时间10min
# Threads: 1 thread, will synchronize iterations  --使用1个线程测试
# Benchmark mode: Average time, time/op  ------------使用"每次操作所需时间"作为测试指标
# Benchmark: lambdasinaction.chap07.ParallelStreamBenchmark.parallelSum  ----当前测试的方法

2.第二部分则是每次运行的报告输出
# Run progress: 40.00% complete, ETA 00:05:03
# Fork: 1 of 2
# Warmup Iteration   1: 124.816 ms/op  ---------这里开始是预热迭代
# Warmup Iteration   2: 112.327 ms/op
# Warmup Iteration   3: 112.277 ms/op
Iteration   1: 112.320 ms/op        ---------这里开始是正式测试迭代
Iteration   2: 111.637 ms/op


# Run progress: 50.00% complete, ETA 00:04:13
# Fork: 2 of 2
# Warmup Iteration   1: 122.870 ms/op
# Warmup Iteration   2: 114.314 ms/op
# Warmup Iteration   3: 116.270 ms/op
Iteration   1: 116.195 ms/op
Iteration   2: 116.536 ms/op

3.第三部分是汇总的报告，包括最小值、平均值、最大值。
Result "lambdasinaction.chap07.ParallelStreamBenchmark.parallelSum":
  114.172 ±(99.9%) 16.491 ms/op [Average]
  (min, avg, max) = (111.637, 114.172, 116.536), stdev = 2.552
  CI (99.9%): [97.681, 130.663] (assumes normal distribution)

4.
*/

}
