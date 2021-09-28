package lambdasinaction.chap12;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.*;

/**
 * @version 1.0
 * @Description: 自定义下一个工作日调整类
 * @author: bingyu
 * @date: 2021/9/27
 */
public class NextWorkingDay implements TemporalAdjuster {

    @Override
    public Temporal adjustInto(Temporal temporal) {
        DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK)); //读取当前日期
        int dayToAdd = 1; //正常加一天
        if (dow == DayOfWeek.FRIDAY) { //如果是周五，加3天
            dayToAdd = 3;
        }
        if (dow == DayOfWeek.SATURDAY) { //如果是周6，加2天
            dayToAdd = 2;
        }
        return temporal.plus(dayToAdd, ChronoUnit.DAYS);
    }

    public static void main(String[] args) {
        //使用Lambda表达式实现TemporalAdjuster接口
        //推荐使用TemporalAdjuster类的静态工厂方法ofDateAdjuster
        TemporalAdjuster nextWorkingDay = TemporalAdjusters.ofDateAdjuster(
                temporal -> {
                    DayOfWeek dow =
                            DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
                    int dayToAdd = 1;
                    if (dow == DayOfWeek.FRIDAY) dayToAdd = 3;
                    else if (dow == DayOfWeek.SATURDAY) dayToAdd = 2;
                    return temporal.plus(dayToAdd, ChronoUnit.DAYS);
                });
        LocalDate date = LocalDate.of(2014, 3, 18);
        date = date.with(nextWorkingDay);
    }
}
