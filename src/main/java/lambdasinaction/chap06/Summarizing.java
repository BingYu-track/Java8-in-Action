package lambdasinaction.chap06;

import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.Optional;

import static lambdasinaction.chap06.Dish.menu;
import static java.util.stream.Collectors.*; //导入Collectors类的所有静态工厂方法

/**
 * @version 1.0
 * @Description: 汇总
 * @author: bingyu
 * @date: 2021/7/23
 */
public class Summarizing {

    public static void main(String[] args) {
        //1.查找最大值
        Comparator<Dish> dishCaloriesComparator = Comparator.comparingInt(Dish::getCalories);
        Optional<Dish> mostCalorieDish = menu.stream().collect(maxBy(dishCaloriesComparator));
        System.out.println(mostCalorieDish.get()); //pork

        //2.查找最小值
        Optional<Dish> minCalorieDish = menu.stream().collect(minBy(dishCaloriesComparator));
        System.out.println(minCalorieDish.get()); //season fruit

        //3.求菜单总热量
        int totalCalories = menu.stream().collect(summingInt(Dish::getCalories));
        System.out.println(totalCalories); //4300

        //4.计算菜单的平均热量
        double avgCalories = menu.stream().collect(averagingInt(Dish::getCalories));
        System.out.println(avgCalories); //477.77777777777777

        //5.通过一次summarizing 操作你就可以数出菜单中元素的个数，并得到菜肴热量总和、平均值、最大值和最小值：
        IntSummaryStatistics menuStatistics = menu.stream().collect(summarizingInt(Dish::getCalories));
        System.out.println(menuStatistics); //IntSummaryStatistics{count=9, sum=4300, min=120, average=477.777778, max=800}
        //当然还有summarizingLong和summarizingDouble方法，对应LongSummaryStatistics和DoubleSummaryStatistics

        //6.

    }
}
