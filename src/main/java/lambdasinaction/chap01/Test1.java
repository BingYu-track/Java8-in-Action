package lambdasinaction.chap01;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static lambdasinaction.chap01.FilteringApples.Apple;
/**
 * @version 1.0
 * @Description: 流
 * @author: bingyu
 * @date: 2021/7/7
 */
public class Test1 {

    /*
        java8之前
        //货币->交易 的map映射
        Map<Currency, List<Transaction>> transactionsByCurrencies = new HashMap<>();
        for (Transaction transaction : transactions) {
            if(transaction.getPrice() > 1000){  //筛选金额大于1000
                Currency currency = transaction.getCurrency();
                List<Transaction> transactionsForCurrency = transactionsByCurrencies.get(currency);
                if (transactionsForCurrency == null) {
                    transactionsForCurrency = new ArrayList<>();
                    transactionsByCurrencies.put(currency, transactionsForCurrency);
                }
                transactionsForCurrency.add(transaction);
            }
        }

        同样的功能，java8后使用stream进行处理
        import static java.util.stream.Collectors.groupingBy;
        Map<Currency, List<Transaction>> transactionsByCurrencies = transactions.stream()
                                    .filter((Transaction t) -> t.getPrice() > 1000) //筛选金额大于1000
                                    .collect(groupingBy(Transaction::getCurrency)); //按货币分组
         解释：for-each循环属于外部迭代，Stream 属于内部迭代，我们根本不用管循环的事情
     */

    public static void main(String[] args) {
        List<Apple> inventory = Arrays.asList(
                new Apple(80, "green"),
                new Apple(155, "green"),
                new Apple(120, "red")
        );
        //顺序处理：
        long l1 = System.currentTimeMillis();
        List<Apple> heavyApples = inventory.stream().filter((Apple a) -> a.getWeight() > 150)
                        .collect(toList());
        long l2 = System.currentTimeMillis();
        long l = l2 - l1;
        System.out.println("顺序处理花费时间："+ l + "毫秒"); //

        //并行处理：
        l1 = System.currentTimeMillis();
        List<Apple> heavyApples2 = inventory.parallelStream().filter((Apple a) -> a.getWeight() > 150)
                        .collect(toList());
        l2 = System.currentTimeMillis();
        l = l2 - l1;
        System.out.println("并行处理花费时间："+ l + "毫秒"); //
    }






}
