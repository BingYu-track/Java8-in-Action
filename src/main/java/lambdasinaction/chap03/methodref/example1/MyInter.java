package lambdasinaction.chap03.methodref.example1;

/**
 * @version 1.0
 * @Description:
 * @author: bingyu
 * @date: 2021/7/13
 */
@FunctionalInterface
public interface MyInter<T,U> {

     U show(T t);
}
