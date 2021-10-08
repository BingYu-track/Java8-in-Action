package lambdasinaction.chap13.conflictrule2;

/**
 * @version 1.0
 * @Description:
 * @author: bingyu
 * @date: 2021/10/8
 */
public class C implements B, A{

    //这时会发现编译器报错了"C inherits unrelated defaults for hello() from types B and A"
    //要想解决这个问题，只能显示的选择调用哪一个方法，java8中引入了新的语法X.super.m()其中X是你希望调用的m方法所在的父接口

    @Override
    public void hello() {
        B.super.hello(); //显示选择调用B接口的默认方法
    }

    public static void main(String[] args) {
        C c = new C();
        c.hello(); //Hello from B

        //总结：先按照规则(1)找类和父类有没有重写覆盖默认方法，如果没有再按照规则(2)看接口里的哪个是子接口，如果还不能判断，就只能按照
        //规则(3)显示调用选择哪个接口的默认方法了
    }
}
