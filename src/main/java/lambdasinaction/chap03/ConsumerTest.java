package lambdasinaction.chap03;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * @version 1.0
 * @Description: Consumer<T> 消费型接口
 * @author: bingyu
 * @date: 2021/7/11
 */
public class ConsumerTest {

    public <T> void forEach(List<T> list, Consumer<T> c){ //Consumer定义了一个accept的抽象方法
        for(T i: list){
            c.accept(i);
        }
    }

    public static void main(String[] args) {
        ConsumerTest consumerTest = new ConsumerTest();
        consumerTest.forEach(
                Arrays.asList(1,2,3,4,5),
                (Integer i) -> System.out.println(i)
        );
    }
}
