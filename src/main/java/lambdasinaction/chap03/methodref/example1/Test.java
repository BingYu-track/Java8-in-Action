package lambdasinaction.chap03.methodref.example1;

import java.util.function.Function;

/**
 * @version 1.0
 * @Description:
 * @author: bingyu
 * @date: 2021/7/13
 */
public class Test {

    public static void main(String[] args) {
        MyInter<Person,String> inter = (Person p) -> p.getName();
        MyInter<Person, String> getName = Person::getName;
        MyInter<Person, Integer> getAge = Person::getAge;
    }
    //MyInter接口的抽象方法U show(T t);比getName和getAge实例方法多一个参数，且lambda的第一个参数就是getName和getAge的调用者，
    //因此可以使用类名::实例方法名
}
