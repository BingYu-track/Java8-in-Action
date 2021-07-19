package lambdasinaction.chap05;

import lambdasinaction.chap04.Dish;

import java.util.OptionalInt;
import static lambdasinaction.chap04.Dish.menu;
/**
 * @version 1.0
 * @Description: Optional基本类型
 * @author: bingyu
 * @date: 2021/7/19
 */
public class TestOptional {

    public static void main(String[] args) {
        OptionalInt maxCalories = menu.stream()
                .mapToInt(Dish::getCalories)
                .max();
        int asInt = maxCalories.getAsInt();
        System.out.println(asInt); //800
        int i = maxCalories.orElse(1);  //没有值就会返回默认值1
        System.out.println(i); //800
    }
}
