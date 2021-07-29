package lambdasinaction.chap07;

import java.util.stream.LongStream;

import static lambdasinaction.chap07.ParallelStreamsHarness.measurePerf;

/**
 * @version 1.0
 * @Description: 正确的使用并行流
 * @author: bingyu
 * @date: 2021/7/29
 */
public class UserParallelStreamCorrect {


    /**
     *
     * 这种代码非常普遍，特别是对那些熟悉指令式编程范式的程序员来说。这段代码和你习惯的
     * 那种指令式迭代数字列表的方式很像：初始化一个累加器，一个个遍历列表中的元素，把它们和
     * 累加器相加。那这种代码又有什么问题呢？不幸的是，它真的无可救药，因为它在本质上就是顺序的。每
     * 次访问total都会出现数据竞争。如果你尝试用同步来修复，那就完全失去并行的意义了
     */
    public static long sideEffectSum(long n) {
        UserParallelStreamCorrect.Accumulator accumulator = new UserParallelStreamCorrect.Accumulator();
        LongStream.rangeClosed(1, n).forEach(accumulator::add);
        return accumulator.total;
    }

    /*
      这里将上一个方法改为并行
     */
    public static long sideEffectParallelSum(long n) {
        UserParallelStreamCorrect.Accumulator accumulator = new UserParallelStreamCorrect.Accumulator();
        LongStream.rangeClosed(1, n).parallel().forEach(accumulator::add);
        return accumulator.total;
    }

    public static void main(String[] args) {
        //从1开始求和一直加到1千万为止，预期结果为50000005000000
        System.out.println("SideEffect parallel sum done in: " +
                        measurePerf(ParallelStreams::sideEffectParallelSum, 10_000_000L) + "msecs" );
    }
    /*
    实际结果:
    Result: 12683933160847
    Result: 7324900614932
    Result: 8520249440795
    Result: 7205297159146
    Result: 6662832848411
    Result: 1381101112431
    Result: 8368105736816
    Result: 7833132736958
    Result: 7381444646445
    Result: 5469410764016
    SideEffect parallel sum done in: 1msecs

    从实际输出来看，都不是正确的结果，这是由于多个线程在同时访问累加器，执行total += value，而
    这一句虽然看似简单，却不是一个原子操作。问题的根源在于，forEach 中调用的方法有副作用，它会改变
    多个线程共享的对象的可变状态。要是你想用并行Stream又不想引发类似的意外，就必须避免这种情况
    共享可变状态会影响并行流以及并行计算。现在，记住要避免共享可变状态，确保并行Stream 得到正确的结果

    */

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
