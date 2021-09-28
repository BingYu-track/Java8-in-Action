package lambdasinaction.chap12;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

/**
 * @version 1.0
 * @Description:
 * @author: bingyu
 * @date: 2021/9/26
 */
public class ParseLocalDateExamples {

    public static void main(String[] args) {
        /**
         *  1.withAttribute 方法会创建对象的一个副本，并按照需要修改它的属性。注意，下面的这段代码中所有的方法都返回
         *  一个修改了属性的对象。"它们都不会修改原来的对象"！
         */
        LocalDate date1 = LocalDate.of(2017, 9, 21); //2017-09-21
        LocalDate date2 = date1.withYear(2011);  //创建date1的副本且修改年  2011-09-21
        LocalDate date3 = date2.withDayOfMonth(25); //创建date2的副本，且修改日期  2011-09-25

        //2.使用更加通用的with方法创建date3的副本，且修改月
        LocalDate date4 = date3.with(ChronoField.MONTH_OF_YEAR, 2); //2011-02-25

        //3.以相对方式修改LocalDate 对象的属性
        LocalDate dateA = LocalDate.of(2017, 9, 21); //2017-09-21
        LocalDate dateB = dateA.plusWeeks(1);  //2017-09-28
        LocalDate dateC = dateB.minusYears(6); //减去6年 2011-09-28
        LocalDate dateD = dateC.plus(6, ChronoUnit.MONTHS); //加上6个月 2012-03-28

        /**
         * 4.如果需要更加复杂的操作，比如将日期调整到下个周日、下个工作日，或者是本月的最后一天。这时，你可
         *   以使用重载版本的with方法，向其传递一个提供了更多定制化选择的TemporalAdjuster对象，更加灵活地处理日期。
         *   ,通过TemporalAdjusters类的静态工厂方法可以访问
         */
        LocalDate d1 = LocalDate.of(2014, 3, 18); //2014-03-18
        LocalDate d2 = d1.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)); //将日期调整到下个周日 2014-03-23
        LocalDate d3 = d2.with(TemporalAdjusters.lastDayOfMonth()); //将日期调整到这个月的最后一天 2014-03-31
    }
}
