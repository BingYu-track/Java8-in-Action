package lambdasinaction.chap11;

import static java.util.stream.Collectors.toSet;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class OptionalMain {

  public String getCarInsuranceNameNullSafeV1(PersonV1 person) {
    if (person != null) {
      CarV1 car = person.getCar();
      if (car != null) {
        Insurance insurance = car.getInsurance();
        if (insurance != null) {
          return insurance.getName();
        }
      }
    }
    return "Unknown";
  }

  public String getCarInsuranceNameNullSafeV2(PersonV1 person) {
    if (person == null) {
      return "Unknown";
    }
    CarV1 car = person.getCar();
    if (car == null) {
      return "Unknown";
    }
    Insurance insurance = car.getInsurance();
    if (insurance == null) {
      return "Unknown";
    }
    return insurance.getName();
  }

  // Doesn't compile:
  // - In (1), we try to invoke map(Person::getCar) on an Optional<Person>.
  //   Switching to flatMap() solves this issue.
  // - Then in (2), we try to invoke map(Car::getInsurance) on an Optional<Car>.
  //   Switching to flatMap() solves this issue.
  // There is no need to further "flatMap" since Insurance::getName returns
  // a plain String.
  /*public String getCarInsuranceName(Person person) {
    Optional<Person> optPerson = Optional.of(person);
    Optional<String> name = optPerson.map(Person::getCar) // (1)
        .map(Car::getInsurance) // (2)
        .map(Insurance::getName);
    return name.orElse("Unknown");
  }*/

  //11-5.使用Optional 获取car 的insurance 名称
  public String getCarInsuranceName(Optional<Person> person) {
    return person.flatMap(Person::getCar)
        .flatMap(Car::getInsurance)
        .map(Insurance::getName)
        .orElse("Unknown");
  }

  /**
   * 操纵Optional对象构成的Stream
   * @param persons
   * @return
   */
  //11-6.找出person 列表所使用的保险公司名称（不含重复项）
  public Set<String> getCarInsuranceNames(List<Person> persons) {
    return persons.stream()
         //1.将person列表转换为Optional<Car>组成的流，car是列表中person 名下的汽车
        .map(Person::getCar)

         //2.对每个Optional<Car>执行flatMap 操作，将其转换成对应的Optional<Insurance>对象
        .map(optCar -> optCar.flatMap(Car::getInsurance))

         //3.将每个Optional<Insurance>映射成包含对应保险公司名字的Optional<String>
        .map(optInsurance -> optInsurance.map(Insurance::getName))

         //4.将Stream<Optional<String>>转换为Stream<String>对象，只保留流中那些存在保险公司名的对象
        .flatMap(Optional::stream)

         //5.收集处理的结果字符串，将其保存到一个不含重复值的Set 中
        .collect(toSet());
  }

  /**
   * 接受一个Person 和一个Car 对象，并以此为条件对外部提供的服务进行查询，通过一些复杂的业务逻辑，
   * 试图找到满足该组合的最便宜的保险公司
   * @param person
   * @param car
   * @return
   */
  public Optional<Insurance> nullSafeFindCheapestInsurance(Optional<Person> person, Optional<Car> car) {
//    该方法的具体实现和你之前曾经null非空判断太相似了：方法接受一个Person和一个Car对象作为参数，
//    而二者都有可能为null。利用Optional类提供的特性，有没有更好或更地道的方式来实现这 个方法呢?
    if (person.isPresent() && car.isPresent()) { //对Optional进行了解包，判断Optional<Person>里的值是否为空
      return Optional.of(findCheapestInsurance(person.get(), car.get()));
    } else {
      return Optional.empty();
    }
  }

  public Insurance findCheapestInsurance(Person person, Car car) {
    // queries services provided by the different insurance companies
    // compare all those data
    Insurance cheapestCompany = new Insurance();
    return cheapestCompany;
  }

  //优化方法：以不解包的方式组合两个Optional对象
  public Optional<Insurance> nullSafeFindCheapestInsuranceQuiz(Optional<Person> person, Optional<Car> car) {
    //重要: 注意这里的p是Person对象，不是Optional<Person>，你可以把Optional也可以看成一个容器，类似List，每次操作都是操作里面的Person对象
    //同理cad.map也是这样理解就行；另外使用flatMap()和map可以避免我们手写非空判断代码
    return person.flatMap(p -> car.map(c -> findCheapestInsurance(p, c)));

//    在这段代码中，你对第一个Optional对象调用flatMap 方法，如果它是个空值，传
//    递给它的Lambda表达式就不会执行，这次调用会直接返回一个空的Optional对象。反之，
//    如果person 对象存在，这次调用就会将其作为函数Function 的输入，并按照与flatMap
//    方法的约定返回一个Optional<Insurance> 对象这个函数的函数体会对第二个
//    Optional对象执行map 操作，如果第二个对象不包含car，函数Function就返回一个空
//    的Optional对象，整个nullSafeFindCheapestInsurance 方法的返回值也是一个空的
//    Optional 对象。最后，如果person 和car 对象都存在，那么作为参数传递给map 方法的
//    Lambda 表达式就能够使用这两个值安全地调用原始的findCheapestInsurance 方法，完
//    成期望的操作。
  }

  /**
   * 找出年龄大于或者等于minAge 参数的Person 所对应的保险公司列表
   * @param person
   * @param minAge
   * @return
   */
  public String getCarInsuranceName(Optional<Person> person, int minAge) {
    return person.filter(p -> p.getAge() >= minAge)
     .flatMap(Person::getCar)
     .flatMap(Car::getInsurance)
     .map(Insurance::getName)
     .orElse("Unknown");
  }



}
