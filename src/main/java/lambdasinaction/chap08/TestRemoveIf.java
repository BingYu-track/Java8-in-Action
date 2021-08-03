package lambdasinaction.chap08;
import java.util.*;

/**
 * @version 1.0
 * @Description: removeIf
 * @author: bingyu
 * @date: 2021/8/3
 */
public class TestRemoveIf {
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
        //1.for循环去除第一个字符为数字的元素
//        for (Transaction transaction : transactions) {
//            if(Character.isDigit(transaction.getReferenceCode().charAt(0))) {
//                transactions.remove(transaction);
//            }
//        }//报错: java.util.ConcurrentModificationException

        //2.上面for-each 循环在底层实现上，使用了一个迭代器对象，所以代码的执行会像下面这样：
        for (Iterator<Transaction> iterator = transactions.iterator(); iterator.hasNext(); ) {
            Transaction transaction = iterator.next();
            if (Character.isDigit(transaction.getReferenceCode().charAt(0))) {
                transactions.remove(transaction);
            }
        } //也报错: java.util.ConcurrentModificationException
        //因为，迭代器对象的状态没有与集合对象的状态同步，反之亦然。为了解决这个问题，你只能显式地使用Iterator 对象，并通过它调用remove()方法：


        //3.使用Iterator的remove方法才不会报错，单代码太繁琐
        for (Iterator<Transaction> iterator = transactions.iterator(); iterator.hasNext(); ) {
            Transaction transaction = iterator.next();
            if(Character.isDigit(transaction.getReferenceCode().charAt(0))) {
                iterator.remove(); //
            }
        }
        System.out.println(transactions);


        //4.使用removeIf，代码会更简洁(其内部实现其实还是用的iterator.remove)
        transactions.removeIf(transaction -> Character.isDigit(transaction.getReferenceCode().charAt(0)));

    }
}
