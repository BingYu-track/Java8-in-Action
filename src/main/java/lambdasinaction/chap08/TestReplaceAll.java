package lambdasinaction.chap08;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @Description: replaceAll
 * @author: bingyu
 * @date: 2021/8/3
 */
public class TestReplaceAll {

    public static List<Transaction> transactions = new ArrayList();

    static {
        transactions.add(new Transaction("hw123001"));
        transactions.add(new Transaction("123111"));
        transactions.add(new Transaction("12FOG"));
        transactions.add(new Transaction("123456"));
        transactions.add(new Transaction("BGMWL"));
        transactions.add(new Transaction("CHINA"));
        transactions.add(new Transaction("LGT2021"));
        transactions.add(new Transaction("LTCJDK11"));
        transactions.add(new Transaction("V1"));
        transactions.add(new Transaction("2thrd"));
        transactions.add(new Transaction("hh"));
    }

    public static void main(String[] args) {
        List<String> referenceCodes = new ArrayList<>();
        referenceCodes.add("a12");
        referenceCodes.add("c14");
        referenceCodes.add("b13");

        //将首个字母转为大写

        //1.使用stream实现
        referenceCodes.stream()
                    .map(code -> Character.toUpperCase(code.charAt(0)) + code.substring(1))
                    .collect(Collectors.toList())
                    .forEach(System.out::println);
        /*
        output: A12
                C14
                B13

         */

        //2.用ListIterator 对象（该对象提供了set()方法，其可以替换集合中的元素）：
        for (ListIterator<String> iterator = referenceCodes.listIterator(); iterator.hasNext(); ) {
            String code = iterator.next();
            iterator.set(Character.toUpperCase(code.charAt(0)) + code.substring(1));
        }
        System.out.println(referenceCodes);

        //3.使用replaceAll使代码更简洁(内部仍是使用的ListIterator的set()方法)
        referenceCodes.replaceAll(code -> Character.toUpperCase(code.charAt(0)) + code.substring(1));

    }
}
