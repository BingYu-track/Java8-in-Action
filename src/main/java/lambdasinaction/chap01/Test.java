package lambdasinaction.chap01;

import java.io.File;
import java.io.FileFilter;

/**
 * @version 1.0
 * @Description:
 * @author: bingyu
 * @date: 2021/7/6
 */
public class Test {

    public static void main(String[] args) {
//        File[] files = new File(".").listFiles(new FileFilter() {
//            @Override
//            public boolean accept(File file) {
//                return file.isHidden();
//            }
//        });

        File[] files = new File(".").listFiles(File::isHidden); //使用::语法，即把这个方法作为值传给listFiles()方法
        //疑问：listFiles()方法要求的参数是FileFilter类型，为什么用File::isHidden这种方式能直接传入呢？
        //具体解答可以看第三章的知识解决
        //答：因为在java8里写下File::isHidden的时候，就已经创建了"一个方法引用"，你同样可以将它传递给listFiles()方法里？
    }
}
