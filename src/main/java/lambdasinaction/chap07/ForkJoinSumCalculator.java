package lambdasinaction.chap07;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;
import static lambdasinaction.chap07.ParallelStreamsHarness.measurePerf;
/**
 * fork/join框架
 */
public class ForkJoinSumCalculator extends RecursiveTask<Long> { //继承RecursiveTask来创建可以用于fork/join框架的任务

  public static final long THRESHOLD = 10_000; //将任务分解为子任务的阈值大小

  private final long[] numbers; //要求和的数字数组
  private final int start; //子任务处理子数组的起始位置
  private final int end; //子任务处理子数组的终止位置

  public ForkJoinSumCalculator(long[] numbers) { //公共构造函数用于创建主任务
    this(numbers, 0, numbers.length);
  }

  private ForkJoinSumCalculator(long[] numbers, int start, int end) { //私有构造函数用于以递归方式为主任务创建子任务
    this.numbers = numbers;
    this.start = start;
    this.end = end;
  }

  @Override
  protected Long compute() { //重写RecursiveTask抽象方法
    int length = end - start; //该任务负责求和的子数组大小
    if (length <= THRESHOLD) {
      return computeSequentially(); //如果子数组大小小于或等于阈值，就顺序计算结果
    }
    //创建一个子任务来为数组的前一半求和
    ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, start, start + length / 2);
    //利用ForkJoinPool的另一个线程异步地执行新创建的leftTask子任务
    leftTask.fork();
    //创建一个子任务来为数组的后一半求和
    ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, start + length / 2, end);
    //Mark:递归调用第二个子任务，获取第二个子任务的最终结果，这行代码相当于rightTask.fork(); Long rightResult = rightTask.join();
    Long rightResult = rightTask.compute();
    //Mark:读取第一个子任务的结果，如果尚未完成就等待,这行代码和上面的leftTask.fork()可以替换成Long leftResult = leftTask.compute();
    Long leftResult = leftTask.join(); //读取第一个子任务的结果，如果尚未完成就等待
    return leftResult + rightResult; //将2个子任务的结果进行合并
  }

  //该方法是主任务被分解成最小子任务后要顺序执行的方法
  private long computeSequentially() {
    long sum = 0;
    for (int i = start; i < end; i++) {
      sum += numbers[i];
    }
    return sum;
  }

  /**
   * 现在可以使用自定义的ForkJoinSumCalculator对前n个自然数求和
   * @param n
   * @return
   */
  public static long forkJoinSum(long n) {
    long[] numbers = LongStream.rangeClosed(1, n).toArray();
    ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
    return new ForkJoinPool().invoke(task); //返回的就是ForkJoinSumCalculator类定义的任务结果
    //return ParallelStreamsHarness.FORK_JOIN_POOL.invoke(task);
  }
  /*
    当把ForkJoinSumCalculator任务传给ForkJoinPool 时，这个任务就由池中的一个线程执行，这个线程会调用任务的compute 方法。
    该方法会检查任务是否小到足以顺序执行，如果不够小则会把要求和的数组分成两半，分给两个新的ForkJoinSumCalculator，而它们也
    由ForkJoinPool 安排执行。因此，这一过程可以递归重复，把原任务分为更小的任务，直到满足不可能再进一步拆分的条件
    (本例中是求和的项目数小于等于10000)。这时会顺序计算每个任务的结果，然后由分支过程创建的（隐含的）任务二叉树遍历回到它的根。接下来
    会合并每个子任务的部分结果，从而得到总任务的结果

   */

  public static void main(String[] args) {
    System.out.println("ForkJoin sum done in: " + measurePerf(ForkJoinSumCalculator::forkJoinSum, 10_000_000l) + " msecs" );
  }
  /*
   output:

    Result: 50000005000000
    Result: 50000005000000
    Result: 50000005000000
    Result: 50000005000000
    Result: 50000005000000
    Result: 50000005000000
    Result: 50000005000000
    Result: 50000005000000
    Result: 50000005000000
    Result: 50000005000000
    ForkJoin sum done in: 26 msecs

   */
}
