package lambdasinaction.chap06;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Collector
 */
public class CollectorHarness {

  public static void main(String[] args) {
    System.out.println("Partitioning done in: " + execute(PartitionPrimeNumbers::partitionPrimes) + " msecs");
    System.out.println("Partitioning done in: " + execute(PartitionPrimeNumbers::partitionPrimesWithCustomCollector) + " msecs");

    Consumer<Integer> primePartitionerc = (Integer i) -> {
      PartitionPrimeNumbers.partitionPrimesWithCustomCollector(i);  //注意虽然调用的方法是有返回值，但是整个lambda表达式是没有返回值的
    };
  }

  private static long execute(Consumer<Integer> primePartitioner) {
    long fastest = Long.MAX_VALUE;
    for (int i = 0; i < 10; i++) { //运行测试10次
      long start = System.nanoTime();
      primePartitioner.accept(1_000_000); //将前一百万个自然数按质数和非质数分区
      long duration = (System.nanoTime() - start) / 1_000_000; //取运行时间的毫秒值
      if (duration < fastest) { //检查这个执行是否是最快的一个
        fastest = duration;
      }
      System.out.println("done in " + duration);
    }
    return fastest;
  }

  /*
    output:
    done in 382
    done in 371
    done in 573
    done in 532
    done in 590
    done in 548
    done in 522
    done in 519
    done in 629
    done in 455
    Partitioning done in: 371 msecs

    done in 284
    done in 338
    done in 226
    done in 375
    done in 328
    done in 361
    done in 408
    done in 224
    done in 327
    done in 231
    Partitioning done in: 224 msecs
   */

}
