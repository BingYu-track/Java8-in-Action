package lambdasinaction.chap09.lambdadelay;

import java.util.Random;
import java.util.function.Supplier;
import java.util.logging.Logger;

/**
 * @version 1.0
 * @Description: 以日志为案例体现lambda的延迟执行，从而不会造成性能浪费，提升性能
 * @author: bingyu
 * @date: 2021/8/4
 */
public class DemoLogger {

    private int level;

    public DemoLogger() {
    }

    public DemoLogger(int level) {
        this.level = level;
    }

    public void log(String msg) {
        System.out.println(msg);
    }

    public void log(int level,String msg) {
        if (this.level==level) {
            System.out.println(msg);
        }
    }

    //使用lambda重载log方法
    public void log(int level, Supplier<String> msgSupplier){
        if(this.isLoggable(level)){
            log(level, msgSupplier.get()); //msgSupplier.get()这里才会真正调用generateDiagnostic()方法
        }
    }

    public boolean isLoggable(int level) {
        if (this.level==level) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        DemoLogger logger = new DemoLogger(Level.ERROR);
        //1.最差的一种日志方式 控制语句被混杂在业务逻辑代码之中。典型的情况包括进行安全性检查以及日志输出
        //问题：
        // 日志器的状态通过isLoggable 方法暴露给了客户端代码。
        // 为什么要在每次输出一条日志之前都去查询日志器对象的状态
        if (logger.isLoggable(Level.FINER)){
            logger.log("Problem: " + generateDiagnostic());
        }

        /*
        2.这种方法更好的原因是你不再需要在代码中插入那些条件判断，与此同时日志器的状态也不再被暴露出去。不过，
        这段代码依旧存在一个问题：无论级别level是否满足要求，log 方法的第二个参数，generateDiagnostic()方法都会被调用
        然后再传入log方法内进行级别判断。
        a.如果判断通过，那么好，这种情况不会对性能产生影响；
        b.如果判断不通过，那么我们之前在传入参数时调用的generateDiagnostic()方法不就是白做了吗，因为又不需要打印，
          用在调用generateDiagnostic()方法的时间就等于白白浪费了，这也就造成了性能浪费。
         */
        System.out.println("重载log方法................");
        logger.log(Level.FINER, "Problem: " + generateDiagnostic()); //执行了generateDiagnostic()方法

        //3.使用Lambda的进行延迟调用
        System.out.println("lambda重载log方法............");
        logger.log(Level.FINER, () -> "Problem: " + generateDiagnostic()); //没有执行generateDiagnostic()方法
    }

    //假设该方法是一个诊断电脑程序的方法，且很耗费性能
    public static String generateDiagnostic() {
        System.out.println("当前generateDiagnostic()方法被调用............");
        int length = 10;
        String str = "zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
        //由Random生成随机数
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        //长度为几就循环几次
        for (int i = 0; i < length; ++i) {
            //产生0-61的数字
            int number = random.nextInt(62);
            //将产生的数字通过length次承载到sb中
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }



}
