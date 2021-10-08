package lambdasinaction.chap13.conflictrule1;

/**
 * @version 1.0
 * @Description:
 * @author: bingyu
 * @date: 2021/10/8
 */
public class C extends D implements B, A{

    public static void main(String... args) {
        new C().hello(); //Hello from D
    }
}
