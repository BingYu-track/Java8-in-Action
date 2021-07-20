package lambdasinaction.chap05;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @version 1.0
 * @Description:
 * @author: bingyu
 * @date: 2021/7/20
 */
public class TestRange {

    public static void main(String[] args) {
        //在1到100的数中有多少个数能整数2
        IntStream evenNumbers = IntStream.rangeClosed(1, 100)
                                         .filter(n -> n % 2 == 0);
        System.out.println(evenNumbers.count());


        /**
         * 问题：从1到100中找出所有组的勾股数
          */

        //问题分解1: 筛选成立的组合(假设我们给出了a值)
        //Math.sqrt()方法是对传入的参数进开方，Math.sqrt(a*a + b*b) % 1 == 0 这里是判断a,b的平方和，开方后是不是正整数
        IntStream.rangeClosed(1, 100).filter(b -> Math.sqrt(a*a + b*b) % 1 == 0);

        //问题分解2: 生成三元勾股数组
        IntStream intStream = IntStream.rangeClosed(1, 100).filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
                .map(b -> new int[]{a, b, Math.sqrt(a * a + b * b)});
        //注意，上面虽然我们map映射的是数组，但是我们用的是IntStream数值流，最终返回的还是数值流，因此我们要想办法转换成对象流
        //修改后，这样才是返回的是数组流:
        Stream<int[]> stream = IntStream.rangeClosed(1, 100).filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
                .boxed().map(b -> new int[]{a, b, Math.sqrt(a * a + b * b)});

        //或者这样修改
        Stream<int[]> stream1 = IntStream.rangeClosed(1, 100).filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
                .mapToObj(b -> new int[]{a, b, Math.sqrt(a * a + b * b)});

        //问题分解3: 上面就差一个a值
        Stream<int[]> stream2 = IntStream.rangeClosed(1, 100).boxed()
                .flatMap(a ->  //下面操作是返回的Stream<int[]>，如果不用flatMap扁平化，最终返回的就是一个Stream<Stream<int[]>>
                        IntStream.rangeClosed(a, 100) //a到100
                                .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
                                .mapToObj(b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)})
                );
        //输出所有1到100中的勾股数对
        stream2.forEach(t -> System.out.println(t[0] + "," + t[1] + "," + t[2]));


    }
}
