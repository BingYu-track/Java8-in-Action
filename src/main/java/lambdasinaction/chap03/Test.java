package lambdasinaction.chap03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @version 1.0
 * @Description: lambda使用局部变量的限制
 * @author: bingyu
 * @date: 2021/7/11
 */
public class Test {



    public static void main(String[] args) {
        Integer portNumber = 1337;
        Runnable r = () -> System.out.println(portNumber);
        //portNumber = 31337; //这行代码一旦放开，就会报错Variable used in lambda expression should be final or effectively final
        //因此局部变量必须是final的才能被lambda使用
    }
}
