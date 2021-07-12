package lambdasinaction.chap03;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

/**
 * @version 1.0
 * @Description: 供给型
 * @author: bingyu
 * @date: 2021/7/12
 */
public class SupplierTest {

    //需求：产生指定个数的整数，并放入集合
    public List<Integer> getNumList(int num, Supplier<Integer> sup) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0;i < num; i++) {
            Integer n = sup.get();
            list.add(n);
        }
        return list;
    }

    public static void main(String[] args) {
        SupplierTest supplierTest = new SupplierTest();
        List<Integer> numList = supplierTest.getNumList(5, () -> (int) (Math.random() * 100));
        System.out.println(numList);
    }
}
