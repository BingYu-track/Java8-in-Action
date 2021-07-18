package lambdasinaction.chap05;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @version 1.0
 * @Description: dropWhile
 * @author: bingyu
 * @date: 2021/7/15
 */
public class DropWhileTest {

    public static void main(String[] args) {
        List<String> list = Stream.of("a", "b", "c", "", "e", "f").dropWhile(s -> {
            System.out.println(s);
            return !s.isEmpty();  //s为a时，当前返回的就是true，后面继续进行了遍历
        }).collect(toList());
        System.out.println("---------------------");
        list.forEach(System.out::println);

        /*输出结果:
        a
        b
        c

        ---------------------

        e
        f

         */
    }
}
