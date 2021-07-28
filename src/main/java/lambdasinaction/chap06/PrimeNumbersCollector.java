package lambdasinaction.chap06;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;

/**
 * @version 1.0
 * @Description: 自定义质数收集器
 * @author: bingyu
 * @date: 2021/7/27
 */
public class PrimeNumbersCollector implements Collector<Integer, Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> {

    //创建累加器
    @Override
    public Supplier<Map<Boolean, List<Integer>>> supplier() {
        return () -> new HashMap<Boolean, List<Integer>>() {{  //不建议使用双花括号进行初始化，因为内部会创建一个内部类，内部类会会持有外部类的引用，导致内存泄漏
            put(true, new ArrayList<Integer>());
            put(false, new ArrayList<Integer>());
        }};
    }


    @Override
    public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {
        return (Map<Boolean, List<Integer>> acc, Integer candidate) -> { //注意这里第一次执行时，candidate=2，因为调用它的Stream就是从2开始的范围流
            List<Integer> primes = acc.get(true); //获取质数列表(第一次执行为空列表)
            //System.out.println("质数列表是否为空列表: "+primes.isEmpty());
            boolean isPrime = PartitionPrimeNumbers.isPrime(primes, candidate); //判断candidate是否是质数
            acc.get(isPrime).add(candidate); //根据isPrime的结果获取对应的质数列表或非质数列表，再将candidate添加到对应列表中
        };
    }

    //合并子累加器(可以不实现的，因为判断质数的算法本身就是顺序的，这意味着永远都不会调用combiner方法)
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
        return Function.identity(); //收集过程最后无须转换，因此用identity函数收尾
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(IDENTITY_FINISH));
    }

}
