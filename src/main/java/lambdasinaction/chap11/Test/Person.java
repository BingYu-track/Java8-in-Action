package lambdasinaction.chap11.Test;

import java.util.Optional;

/**
 * @version 1.0
 * @Description:
 * @author: bingyu
 * @date: 2021/9/7
 */
public class Person {
    private Car car;
    public Car getCar() {
        return car;
    }

    //获取当前人的车险公司名
    public String getCarInsuranceName(Person person) {
        return person.getCar().getInsurance().getName();
    }


}
