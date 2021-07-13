package lambdasinaction.chap03.methodref.example2;

/**
 * @version 1.0
 * @Description: 类型::实例方法名
 * @author: bingyu
 * @date: 2021/7/13
 */
public class Test {

    public static void main(String[] args) {
        //可以看到MyInter接口的抽象方法void show(T t,U u,P p);去掉第一个参数后，后面的参数和Person对象的setAgeAndName方法参数是一样的
        //返回值是一样的，因此可以使用类名::实例方法名
        MyInter<Person,String,Integer> inter = Person::setAgeAndName;
        //等效于
        MyInter<Person,String,Integer> inter1 = (p,name,age) -> p.setAgeAndName(name,age);

        //这里报错，因为现在抽象方法void show(T t,U u,P p);去掉第一个参数后，后面的参数和Person对象的setAgeAndName方法参数就不一样了
        //因此不能使用类名::实例方法名
        //MyInter<String,Person,Integer> inter2 = Person::setAgeAndName;

        //报错因为现在抽象方法void show(T t,U u,P p);去掉第一个参数后，后面的参数个数是2，Person对象的setAge方法参数只有一个，不一致
        //因此不能使用类名::实例方法名
        //MyInter<String,Person,Integer> inter3 = Person::setAge;
    }
}
