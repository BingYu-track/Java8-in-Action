package lambdasinaction.chap05;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @Description:
 * @author: bingyu
 * @date: 2021/7/19
 */
public class TestTrade {

    public static void main(String[] args) {
        //1.找出2011 年发生的所有交易，并按交易额排序（从低到高)
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario","Milan");
        Trader alan = new Trader("Alan","Cambridge");
        Trader brian = new Trader("Brian","Cambridge");
        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );
        List<Transaction> list1 = transactions.stream().filter(transaction -> transaction.getYear() == 2011)
                .sorted((a, b) -> a.getValue() - b.getValue()).collect(Collectors.toList());
        System.out.println("1.找出2011 年发生的所有交易，并按交易额排序（从低到高)");
        System.out.println(list1);

        //2.交易员都在哪些不同的城市工作过？
        List<String> list2 = transactions.stream().map(transaction -> transaction.getTrader().getCity()).distinct()
                .collect(Collectors.toList());
        System.out.println("2.交易员都在哪些不同的城市工作过？");
        System.out.println(list2);

        //3.查找所有来自于剑桥的交易员，并按姓名排序
        List<Trader> list3 = transactions.stream().map(Transaction::getTrader)
                .filter(trader -> trader.getCity().equals("Cambridge"))
                .distinct()  //
                //.sorted((t1, t2) -> t1.getName().compareTo(t2.getName())) 和下面是等效的
                .sorted(Comparator.comparing(Trader::getName))
                .collect(Collectors.toList());
        System.out.println("3.查找所有来自于剑桥的交易员，并按姓名排序: ");
        System.out.println(list3); //[Trader:Alan in Cambridge, Trader:Brian in Cambridge, Trader:Raoul in Cambridge]

        //4.返回所有交易员的姓名字符串，按字母顺序排序
        String str = transactions.stream().map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted() //对姓名按字母顺序排序
                .reduce("", (a, b) -> a + b); //注意: reduce没有初始值时会返回一个Optional对象
        System.out.println("4.返回所有交易员的姓名字符串，按字母顺序排序: ");
        System.out.println(str);

        //5.有没有交易员是在米兰工作的？
        boolean isMilanWork = transactions.stream().map(Transaction::getTrader)
                .anyMatch(trader -> trader.getCity().equals("Milan"));
        System.out.println("5.有没有交易员是在米兰工作的？");
        System.out.println(isMilanWork);

        //6.打印生活在剑桥的交易员的所有交易额
        System.out.println("6.打印生活在剑桥的交易员的所有交易额");
        transactions.stream().filter(transaction ->  transaction.getTrader().getCity().equals("Cambridge"))
                    .map(Transaction::getValue)
                    .forEach(e -> System.out.println(e));

        //7.所有交易中，最高的交易额是多少？
        //Mark: transactions.stream().reduce(0,(a,b) -> Integer.max(a.getValue(),b.getValue());
        //报错: Bad return type in lambda expression: int cannot be converted to Transaction
        //返回类型不一致，从T reduce(T identity, BinaryOperator<T> accumulator);   -》  interface BinaryOperator<T> extends BiFunction<T,T,T>
        // -》 BiFunction的抽象方法 : R apply(T t, U u);  从而知道该lambda表达式的返回值和当前Stream中的泛型类型是一致的，因此不能这样
        //reduce的lambda表达式类型: (T,T) ->T

        int max = transactions.stream().map(Transaction::getValue)
                             .reduce(0,(a,b) -> Integer.max(a,b));
        System.out.println("7.所有交易中，最高的交易额是多少？");
        System.out.println(max);

        //8.找到交易额最小的交易。
        Optional<Integer> op = transactions.stream().map(Transaction::getValue)
                .reduce((a, b) -> Integer.min(a, b));
        System.out.println("8.找到交易额最小的交易");
        System.out.println(op.get());
    }
}
