package lambdasinaction.chap11.Test;

import lambdasinaction.chap11.Car;
import lambdasinaction.chap11.Insurance;
import lambdasinaction.chap11.Person;

import java.util.Optional;

/**
 * @version 1.0
 * @Description:从Optional中提取和转换值
 * @author: bingyu
 * @date: 2021/9/7
 */
public class ExtractingAndTransformingFromOptionals {

    public static void main(String[] args) {
        lambdasinaction.chap11.Insurance insurance = new lambdasinaction.chap11.Insurance();
        Optional<lambdasinaction.chap11.Insurance> optInsurance = Optional.ofNullable(insurance);
        Optional<String> name = optInsurance.map(Insurance::getName); //提取"名字"

        //flatMap
        lambdasinaction.chap11.Person person = new lambdasinaction.chap11.Person();
        Optional<lambdasinaction.chap11.Person> optPerson = Optional.of(person);
        //这样直接使用map是不行的
        Optional<Optional<Car>> car = optPerson.map(Person::getCar);

    }

}
