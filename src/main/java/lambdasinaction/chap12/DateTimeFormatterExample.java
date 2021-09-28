package lambdasinaction.chap12;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;

/**
 * @version 1.0
 * @Description: 打印输出及解析日期–时间对象
 * @author: bingyu
 * @date: 2021/9/28
 */
public class DateTimeFormatterExample {


    public static void main(String[] args) {
        LocalDate date = LocalDate.of(2014, 3, 18);

        /**
         * 1.BASIC_ISO_DATE和ISO_LOCAL_DATE这样的常量是DateTimeFormatter 类的预定义实例
         * 所有的DateTimeFormatter 对象都能用于以一定的格式创建代表特定日期或时间的字符串
         * 下面这个例子使用两个不同的格式器生成了字符串：
         */
        String s1 = date.format(DateTimeFormatter.BASIC_ISO_DATE); //20140318
        String s2 = date.format(DateTimeFormatter.ISO_LOCAL_DATE); //2014-03-18

        //2.也可以通过解析代表日期或时间的字符串重新创建该日期对象
        LocalDate date1 = LocalDate.parse("20140318", DateTimeFormatter.BASIC_ISO_DATE);
        LocalDate date2 = LocalDate.parse("2014-03-18", DateTimeFormatter.ISO_LOCAL_DATE);

        //3.可以使用ofPattern()方法按照指定的格式创建DateTimeFormatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        date1 = LocalDate.of(2014, 3, 18);
        String formattedDate = date1.format(formatter); // 18/03/2014
        date2 = LocalDate.parse(formattedDate, formatter);

        //4. 创建一个本地化的DateTimeFormatter
        //(1)这里是使用的意大利当地的格式
        DateTimeFormatter italianFormatter = DateTimeFormatter.ofPattern("d. MMMM yyyy", Locale.ITALIAN);
        date1 = LocalDate.of(2014, 3, 18);
        String fd = date.format(italianFormatter); // 18. marzo 2014
        date2 = LocalDate.parse(fd, italianFormatter);

        //(2)使用中国格式
        DateTimeFormatter chineseFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd", Locale.SIMPLIFIED_CHINESE);
        date1 = LocalDate.of(2014, 3, 18);
        fd = date.format(chineseFormatter); //
        date2 = LocalDate.parse(fd, chineseFormatter);

        /**
         * 5. 如果需要更细粒度的控制，可以使用DateTimeFormatterBuilder类，它提供了更复杂的格式器
         *    你可以选择恰当的方法，一步一步地构造自己的格式器。另外，它还提供了非常强大的解析功能，比如
         *    区分大小写的解析、柔性解析（允许解析器使用启发式的机制去解析输入，不精确地匹配指定的模式）、填充，以及在格式器中指定可选节
         */
        italianFormatter = new DateTimeFormatterBuilder()
                .appendText(ChronoField.DAY_OF_MONTH)
                .appendLiteral(". ")
                .appendText(ChronoField.MONTH_OF_YEAR)
                .appendLiteral(" ")
                .appendText(ChronoField.YEAR)
                .parseCaseInsensitive()
                .toFormatter(Locale.ITALIAN);
        System.out.println(italianFormatter);

    }
}
