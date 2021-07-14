package lambdasinaction.chap04;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static lambdasinaction.chap04.Dish.menu;
/**
 * @version 1.0
 * @Description: 外部迭代和内部迭代
 * @author: bingyu
 * @date: 2021/7/14
 */
public class ExternalVSInternalIteration {

    public static void main(String[] args) {
        List<String> names = new ArrayList<>();
        //外部迭代
        for(Dish dish: menu) {
            names.add(dish.getName());
        }

        //注意上面的for-each还隐藏了迭代中的一些复杂性。for-each 结构是一个语法糖，它背后的东西用Iterator 对象表达出来会更丑陋
        //for-each真正的内部实现
        Iterator<Dish> iterator = menu.iterator();
        while(iterator.hasNext()) {
            Dish dish = iterator.next();
            names.add(dish.getName());
        }


        //内部迭代，简洁
        names = menu.stream()
                .map(Dish::getName)
                .collect(toList());
    }


}
