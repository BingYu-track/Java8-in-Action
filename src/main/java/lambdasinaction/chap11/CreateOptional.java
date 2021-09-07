package lambdasinaction.chap11;

import java.util.Optional;

/**
 * @version 1.0
 * @Description: 创建Optional
 * @author: bingyu
 * @date: 2021/9/7
 */
public class CreateOptional {

    public static void main(String[] args) {
        Car car = new Car();
        //1.创建空的Optional
        Optional<Car> opt = Optional.empty();

        //2.使用非空值创建Optional ----如果这里car是null，会立即抛出空指针异常，而不是等你试图访问car时才返回错误
        Optional<Car> ops = Optional.of(car);

        //3.接收null的Optional，使用静态工厂方法ofNullable可以创建允许null值的Optional对象，如果car是一个null，得到的Optional就是一个空对象
        Optional<Car> s = Optional.ofNullable(car); //这里car可能为null
    }
}
