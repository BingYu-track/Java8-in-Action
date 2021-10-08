package lambdasinaction.chap13.conflictrule2;


/**
 * @version 1.0
 * @Description: 现在B不再继承A
 * @author: bingyu
 * @date: 2021/10/8
 */
public interface B {

    default void hello() {
        System.out.println("Hello from B");
    }

}
