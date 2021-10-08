package lambdasinaction.chap13.conflictrule;

/**
 * @version 1.0
 * @Description:
 * @author: bingyu
 * @date: 2021/10/8
 */
public class C1 extends D implements B, A {

    public static void main(String[] args) {
        C1 c1 = new C1();
        c1.hello(); //Hello from B

        /**
         依据规则(1)，类中声明的方法具有更高的优先级。D并未覆盖hello 方法，可是它实现了
         接口A。所以它就拥有了接口A 的默认方法。规则(2)说如果类或者父类没有对应的方法，那么就
         应该选择提供了最具体实现的接口中的方法。因此，编译器会在接口A 和接口B 的hello 方法
         之间做选择。因为B 更加具体，所以程序会再次打印输出“Hello from B”。

         总结：先找类和父类有没有重写覆盖默认方法，如果没有再看接口里的哪个是子接口
         */
    }
}
