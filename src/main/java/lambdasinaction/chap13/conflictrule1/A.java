package lambdasinaction.chap13.conflictrule1;

/**
 * @version 1.0
 * @Description:
 * @author: bingyu
 * @date: 2021/10/8
 */
public interface A {

    default void hello(){
        System.out.println("Hello from A");
    }
}
