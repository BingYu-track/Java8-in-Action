package lambdasinaction.chap09;

/**
 * @version 1.0
 * @Description: 匿名类和lambda表达式的区别
 * @author: bingyu
 * @date: 2021/8/4
 */
public class AnonymousAndLambdaDifferent {

    public static void main(String[] args) {
        //第一个不同点: 匿名类可以屏蔽外部的的变量，而Lambda表达式不能（它们会导致编译错误），
        int a = 10;
        Runnable r1 = () -> {
            //int a = 2; //这里编译报错，无法屏蔽外面已经定义的变量a
            System.out.println(a);
        };

        Runnable r2 = new Runnable(){
            public void run(){
                int a = 2; //可以屏蔽外面已经定义的变量a
                System.out.println(a);
            }
        };

        AnonymousAndLambdaDifferent test = new AnonymousAndLambdaDifferent();
        Thread t1 = new Thread(test.ra);
        t1.start(); //class lambdasinaction.chap09.AnonymousAndLambdaDifferent

        Thread t2 = new Thread(test.rb);//内部类，文件名格式：主类名$内部类名.class（如果匿名内部类，这内部类名为数字）
        t2.start(); //class lambdasinaction.chap09.AnonymousAndLambdaDifferent$2
    }

    //第二个不同点:lambda表达式最终会返回一个实现了指定接口的实例，看上去和内部匿名类很像,但是匿名类和Lambda表达式中的this的含义其实是不同的。
    //在匿名类中，this指向的是匿名类本身;但是在Lambda中，this指向的是当前lambda所在的当前类，本例就是AnonymousAndLambdaDifferent类
    Runnable ra = ()->System.out.println("lambda的this指向当前的Test类: "+this.getClass() + '\n' + "lambda的super: " + super.getClass());

    Runnable rb = new Runnable() {
        @Override
        public void run() {
            System.out.println("匿名内部类这里的this指向匿名类: "+this.getClass()+ '\n' + "匿名内部类的super: " + super.getClass());
        }
    };

    //对当前的class进行反编译javap -s -p(输出类中方法的签名和所有类和成员)
    /*
       Compiled from "AnonymousAndLambdaDifferent.java"
        public class lambdasinaction.chap09.AnonymousAndLambdaDifferent {
              java.lang.Runnable ra;
                descriptor: Ljava/lang/Runnable;
              java.lang.Runnable rb;
                descriptor: Ljava/lang/Runnable;
              public lambdasinaction.chap09.AnonymousAndLambdaDifferent();
                descriptor: ()V

              public static void main(java.lang.String[]);
                descriptor: ([Ljava/lang/String;)V

              private void lambda$new$1();
                descriptor: ()V

              private static void lambda$main$0(int);
                descriptor: (I)V
        }


        Mark:从反编译的结果我们可以看出lambda表达式里面，会把lambda表达式在本类中生成一个以lambda$+数字 的方法，该方法不一定是static的方法，
        是static还是非static，取决于lambda表达式里面是否引用了this。这就是为什么lambda表达式里面的this指向的是本地，
        因为他在本类里面创建了一个方法，然后把lambda表达式里面的代码放进去

        Mark:还有要注意的是自动生成的lambda方法是否带参数取决于lambda是否有参数以及是否使用了外部的变量，例子中表达式没有参数（箭头左边是空的），
        但是引用了外部变量a,所以自动生成lambda方法是有一个int参数。
     */


}
