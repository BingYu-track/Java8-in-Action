package lambdasinaction.chap04;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static lambdasinaction.chap04.Dish.menu;
/**
 * @version 1.0
 * @Description:
 * @author: bingyu
 * @date: 2021/7/14
 */
public class Test {

    public static void main(String[] args) {
//        List<String> highCaloricDishes = new ArrayList<>();
//        Iterator<Dish> iterator = menu.iterator();
//        while(iterator.hasNext()) {
//            Dish dish = iterator.next();
//            if(dish.getCalories() > 300) {
//                highCaloricDishes.add(dish.getName());
//            }
//        }
//
//        highCaloricDishes = menu.stream().filter((dish -> dish.getCalories() > 300))
//                     .map(Dish::getName).collect(toList());

        List<String> names =
                menu.stream()
                        .filter(dish -> {
                            System.out.println("filtering:" + dish.getName());
                            return dish.getCalories() > 300;
                        })
                        .map(dish -> {
                            System.out.println("mapping:" + dish.getName());
                            return dish.getName();
                        })
                        .limit(3)
                        .collect(toList());
        System.out.println(names);

        /*
        输出结果: (从结果可以发现，尽管filter 和map 是两个独立的操作，但它们合并到同一次遍历中了我们把这种技术叫作"循环合并")
        filtering:pork
        mapping:pork
        filtering:beef
        mapping:beef
        filtering:chicken
        mapping:chicken
        [pork, beef, chicken]

        */
    }
}
