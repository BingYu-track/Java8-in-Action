package lambdasinaction.chap12;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.time.temporal.TemporalAdjusters.nextOrSame;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.chrono.JapaneseDate;
import java.time.chrono.MinguoDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeExamples {

  private static final ThreadLocal<DateFormat> formatters = new ThreadLocal<DateFormat>() {

    @Override
    protected DateFormat initialValue() {
      return new SimpleDateFormat("dd-MMM-yyyy");
    }
  };

  public static void main(String[] args) {
    useOldDate();
    useLocalDate();
    useTemporalAdjuster();
    useDateFormatter();
  }

  private static void useOldDate() {
    Date date = new Date(114, 2, 18);
    System.out.println(date);

    System.out.println(formatters.get().format(date));

    Calendar calendar = Calendar.getInstance();
    calendar.set(2014, Calendar.FEBRUARY, 18);
    System.out.println(calendar);
  }


  private static void useLocalDate() {
    /**
     * 1.使用LocalDate，LocalDate只包含日期，不包含当天的时间信息
     */
    LocalDate date = LocalDate.of(2014, 3, 18);
    int year = date.getYear(); // 2014
    Month month = date.getMonth(); // MARCH
    int day = date.getDayOfMonth(); // 18
    DayOfWeek dow = date.getDayOfWeek(); // TUESDAY
    int len = date.lengthOfMonth(); // 31 (days in March)
    boolean leap = date.isLeapYear(); // false (not a leap year)，判断是否是闰年
    System.out.println(date);

    //使用工厂方法now从系统时钟中获取当前的日期:
    LocalDate today = LocalDate.now(); //2021-09-17

    //TemporalField的实现类ChronoField，用来读取LocalDate的值
    int y = date.get(ChronoField.YEAR);
    int m = date.get(ChronoField.MONTH_OF_YEAR);
    int d = date.get(ChronoField.DAY_OF_MONTH);

    /**
     * 2. LocalTime只用来表示一天中的时间
     */
    LocalTime time = LocalTime.of(13, 45, 20); // 13:45:20
    int hour = time.getHour(); // 13
    int minute = time.getMinute(); // 45
    int second = time.getSecond(); // 20
    System.out.println(time);

    //LocalDate 和LocalTime 都可以通过解析代表它们的字符串创建。使用静态方法parse，
    LocalDate ld = LocalDate.parse("2017-09-21");
    LocalTime lt = LocalTime.parse("13:45:20");


    /**
     * 3.LocalDateTime，是合并了LocalDate和LocalTime的集合体，它同时表示了日期和时间，但不带时区信息
     */
    //直接创建LocalDateTime
    LocalDateTime dt1 = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45, 20); // 2014-03-18T13:45
    //通过合并LocalDate和LocalTime创建
    LocalDateTime dt2 = LocalDateTime.of(date, time);
    LocalDateTime dt3 = date.atTime(13, 45, 20); //向LocalDate传递时间合并成LocalDateTime
    LocalDateTime dt4 = date.atTime(time); //向LocalDate传递LocalTime合并成LocalDateTime
    LocalDateTime dt5 = time.atDate(date); //向LocalTime传递LocalDate合并成LocalDateTime
    System.out.println(dt1);

//  前面展示了向LocalDate 传递一个时间对象，或者向LocalTime 传递一个日期对象的方式，你可以创建一个LocalDateTime 对象。
//  下面的示例展示了，你也可以使用toLocalDate 或者toLocalTime 方法，从LocalDateTime 中提取LocalDate或者LocalTime 组件:
    LocalDate date1 = dt1.toLocalDate();
    System.out.println(date1);
    LocalTime time1 = dt1.toLocalTime();
    System.out.println(time1);

    /**
     * Instant是为了便于机器处理的日期和时间格式
     */
    Instant instant = Instant.ofEpochSecond(44 * 365 * 86400); //从1970年1月1日零点开始后的365天*44所对应的时间，是一个unix时间戳
    Instant now = Instant.now();
    Instant.ofEpochSecond(3);
    Instant.ofEpochSecond(3, 0);
    Instant.ofEpochSecond(2, 1_000_000_000); //2 秒之后再加上10亿纳秒（1 秒）
    Instant.ofEpochSecond(4, -1_000_000_000); //4 秒之前的10亿纳秒（1 秒）

    /**
     * 4.Duration和Period
     */
    //(1)、Duration是用于以'秒'和'纳秒'来衡量2个时间点的时间长短的，所以不能仅向between方法传递一个LocalDate，必须有time才行
    Duration d1 = Duration.between(LocalTime.of(13, 45, 10), time); //13:45:10和13:45:20之间的时长
    Duration d2 = Duration.between(instant, now); //instant和现在的时间时长
    System.out.println(d1.getSeconds()); //相隔10秒
    System.out.println(d2.getSeconds()); //相隔244734651秒

    //(2)、如果需要以年、月、日的方式衡量，可以使用Period，使用该类的between方法，你可以得到两个LocalDate之间的时长
    Period tenDays = Period.between(LocalDate.of(2017, 9, 11),
                                    LocalDate.of(2017, 9, 21));


    Duration threeMinutes = Duration.of(3, ChronoUnit.MINUTES); //ChronoUnit是表示Period的时间单位，这里表示是3分钟
    System.out.println(threeMinutes);

    threeMinutes = Duration.ofMinutes(3); //3分钟
    tenDays = Period.ofDays(10);  //10天
    Period threeWeeks = Period.ofWeeks(3); //3周
    Period twoYearsSixMonthsOneDay = Period.of(2, 6, 1);

    JapaneseDate japaneseDate = JapaneseDate.from(date); //日本日历系统
    MinguoDate minguoDate = MinguoDate.from(date); //中华民国日历系统
    System.out.println(japaneseDate);
  }

  private static void useTemporalAdjuster() {
    LocalDate date = LocalDate.of(2014, 3, 18);
    date = date.with(nextOrSame(DayOfWeek.SUNDAY));
    System.out.println(date);
    date = date.with(lastDayOfMonth());
    System.out.println(date);

    date = date.with(new NextWorkingDay());
    System.out.println(date);
    date = date.with(nextOrSame(DayOfWeek.FRIDAY));
    System.out.println(date);
    date = date.with(new NextWorkingDay());
    System.out.println(date);

    date = date.with(nextOrSame(DayOfWeek.FRIDAY));
    System.out.println(date);
    date = date.with(temporal -> {
      DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
      int dayToAdd = 1;
      if (dow == DayOfWeek.FRIDAY) {
        dayToAdd = 3;
      }
      if (dow == DayOfWeek.SATURDAY) {
        dayToAdd = 2;
      }
      return temporal.plus(dayToAdd, ChronoUnit.DAYS);
    });
    System.out.println(date);
  }

  private static class NextWorkingDay implements TemporalAdjuster {

    @Override
    public Temporal adjustInto(Temporal temporal) {
      DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
      int dayToAdd = 1;
      if (dow == DayOfWeek.FRIDAY) {
        dayToAdd = 3;
      }
      if (dow == DayOfWeek.SATURDAY) {
        dayToAdd = 2;
      }
      return temporal.plus(dayToAdd, ChronoUnit.DAYS);
    }
  }

  private static void useDateFormatter() {
    LocalDate date = LocalDate.of(2014, 3, 18);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter italianFormatter = DateTimeFormatter.ofPattern("d. MMMM yyyy", Locale.ITALIAN);

    System.out.println(date.format(DateTimeFormatter.ISO_LOCAL_DATE));
    System.out.println(date.format(formatter));
    System.out.println(date.format(italianFormatter));

    DateTimeFormatter complexFormatter = new DateTimeFormatterBuilder()
        .appendText(ChronoField.DAY_OF_MONTH)
        .appendLiteral(". ")
        .appendText(ChronoField.MONTH_OF_YEAR)
        .appendLiteral(" ")
        .appendText(ChronoField.YEAR)
        .parseCaseInsensitive()
        .toFormatter(Locale.ITALIAN);

    System.out.println(date.format(complexFormatter));
  }

}
