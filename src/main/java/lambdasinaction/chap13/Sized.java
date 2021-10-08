package lambdasinaction.chap13;

/**
 * @version 1.0
 * @Description:
 * @author: bingyu
 * @date: 2021/9/30
 */
public interface Sized {

    int size();

    default boolean isEmpty() { //默认方法，这样任何一个实现了Sized 接口的类都会自动继承isEmpty 的实现。因此，向提供了默认实现的接口添加方法就不是源码兼容的
        return size() == 0;
    }
}
