package lambdasinaction.chap12;

import java.time.LocalDate;
import java.time.Month;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.time.chrono.JapaneseDate;
import java.util.Locale;

/**
 * @version 1.0
 * @Description: 历法
 * @author: bingyu
 * @date: 2021/9/28
 */
public class CalendarExample {

    public static void main(String[] args) {
        LocalDate date = LocalDate.of(2014, Month.MARCH, 18);
        JapaneseDate japaneseDate = JapaneseDate.from(date);

        /**
         * 你还可以为某个Locale 显式地创建日历系统，接着创建该Locale对应的日期的实
         * 例.新的日期和时间API 中，Chronology 接口建模了一个日历系统，使用它的静态工厂方法
         * ofLocale，可以得到它的一个实例，代码如下：
         */
        Chronology japaneseChronology = Chronology.ofLocale(Locale.JAPAN);
        ChronoLocalDate now = japaneseChronology.dateNow();
    }
}
