package lambdasinaction.chap09;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Stream;

/**
 * 使用日志调试Lambda----使用peek查看Stream流中的数据流的值
 */
public class Peek {


  public static void main(String[] args) {
    List<Integer> result = Stream.of(2, 3, 4, 5)
        .peek(x -> System.out.println("taking from stream: " + x))
        .map(x -> x + 17)
        .peek(x -> System.out.println("after map: " + x)) //输出map操作的结果
        .filter(x -> x % 2 == 0)
        .peek(x -> System.out.println("after filter: " + x)) //输出经过filter操作之后，剩下的元素
        .limit(3)
        .peek(x -> System.out.println("after limit: " + x))
        .collect(toList());

    /*
      通过peek操作能清楚地了解流水线操作中每一步的输出结果
      输出:
      taking from stream: 2
      after map: 19
      taking from stream: 3
      after map: 20
      after filter: 20
      after limit: 20
      taking from stream: 4
      after map: 21
      taking from stream: 5
      after map: 22
      after filter: 22
      after limit: 22
     */
  }

}
