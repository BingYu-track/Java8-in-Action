package lambdasinaction.chap13.conflictrule;

/**
 * @version 1.0
 * @Description:
 * @author: bingyu
 * @date: 2021/10/8
 */
public class C implements B, A {

    public static void main(String[] args) {
        C c = new C();
        c.hello(); //Hello from B  按照规则2中，会调用比A接口更具体的B接口的默认方法
    }
}
