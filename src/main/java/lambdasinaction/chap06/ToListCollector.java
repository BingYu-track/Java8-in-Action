package lambdasinaction.chap06;

import static java.util.stream.Collector.Characteristics.CONCURRENT;
import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;
import static lambdasinaction.chap06.Dish.menu;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * 实现Collector接口，自定义Collector收集器
 * @param <T>
 */
public class ToListCollector<T> implements Collector<T, List<T>, List<T>> {

  @Override
  public Supplier<List<T>> supplier() {
    return () -> new ArrayList<T>(); //创建一个空的ArrayList累加器实例，供数据收集过程使用
  }

  @Override
  public BiConsumer<List<T>, T> accumulator() {
    return (list, item) -> list.add(item); //返回执行归约操作的函数: list是累加器  item是遍历流的当前元素
  }

  @Override
  public Function<List<T>, List<T>> finisher() {
    //return i -> i;
    return Function.identity();  //返回在累积过程的最后要调用的一个函数(可以看做接口的实例),以便将累加器对象转换为整个集合操作的最终结果
    //Function接口的静态identity()返回的就是输入的参数
  }


  //combiner方法会返回一个供归约操作使用的函数，它定义了对流的各个子部分进行并行处理时，各个子部分归约所得的累加器要如何合并
  @Override
  public BinaryOperator<List<T>> combiner() {
    return (list1, list2) -> { //list1:子累加器1   list2:子累加器2
      list1.addAll(list2); //合并
      return list1;
    };
  }

  @Override
  public Set<Characteristics> characteristics() {
    //为收集器添加IDENTITY_FINISH和CONCURRENT标志
    return Collections.unmodifiableSet(EnumSet.of(IDENTITY_FINISH, CONCURRENT));
  }

  //测试自定义收集器
  public static void main(String[] args) {
    List<Dish> dishes = menu.stream()
                      .filter(Dish::isVegetarian)
                      .collect(new ToListCollector<Dish>());
    System.out.println(dishes); //[french fries, rice, season fruit, pizza]
  }


}
