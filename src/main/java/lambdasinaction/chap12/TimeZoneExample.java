package lambdasinaction.chap12;

import java.time.*;
import java.util.TimeZone;

/**d
 * @version 1.0
 * @Description: 时区
 * @author: bingyu
 * @date: 2021/9/28
 */
public class TimeZoneExample {


    public static void main(String[] args) {
        /**
         * 1.通过调用ZoneId的getRules()得到指定时区的规则。每个特定的ZoneId对象都由一个地区ID标识
         *   ZoneId都为“{区域}/{城市}”的格式，这些地区集合的设定都由因特网编号分配机构（IANA）的时区数据库提供
         */
        ZoneId romeZone = ZoneId.of("Europe/Rome");

        //2.以通过Java 8的新方法toZoneId将老的时区对象TimeZone转化成ZoneId
        ZoneId zoneId = TimeZone.getDefault().toZoneId();

        /**
         * 3.一旦得到一个ZoneId对象，你就可以将它与LocalDate、LocalDateTime 或者是Instant
         *   对象整合起来，构造为一个ZonedDateTime 实例，它代表了相对于指定时区的时间点
         */
        LocalDate date = LocalDate.of(2014, Month.MARCH, 18);
        ZonedDateTime zdt1 = date.atStartOfDay(romeZone);
        LocalDateTime dateTime = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45);
        ZonedDateTime zdt2 = dateTime.atZone(romeZone);
        Instant instant = Instant.now();
        ZonedDateTime zdt3 = instant.atZone(romeZone);

        //4.通过ZoneId，你还可以将LocalDateTime 转换为Instant:
        ZoneId z = ZoneId.of("Europe/Rome");
        dateTime = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45);
        Instant instantFromDateTime = dateTime.toInstant(ZoneOffset.UTC);

        //你也可以通过反向的方式得到LocalDateTime对象
        Instant nt = Instant.now();
        LocalDateTime timeFromInstant = LocalDateTime.ofInstant(nt, romeZone);
        System.out.println();
    }
}
