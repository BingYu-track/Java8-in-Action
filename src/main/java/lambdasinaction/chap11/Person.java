package lambdasinaction.chap11;


import java.util.Optional;

public class Person {

  private Optional<Car> car;
  private int age;

  public Optional<Car> getCar() {
    return car;
  }

  public int getAge() {
    return age;
  }

  public void setCar(Optional<Car> car) {
    this.car = car;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public String getCarInsuranceName2(Optional<Person> person) {

    //从Optional<Person>提取car属性，需要用到map方法，但是因为Person里的car属性也是一个Optional就会导致嵌套Optional，因此需要使用flatMap方法打平后提取
    Optional<Car> car = person.flatMap(Person::getCar);

    //TODO:这是错误示范
    Optional<Optional<Car>> car2 = person.map(Person::getCar);

    //同理这里Car提取Insurance也要使用flatMap
    Optional<Insurance> insurance = car.flatMap(Car::getInsurance);
    //TODO:这是错误示范
    Optional<Optional<Insurance>> insurance1 = car.map(Car::getInsurance);


    //insurance.flatMap(Insurance::getName);  编译器报错，因为insurance.flatMap()中lambda式是
    //Function<? super T, ? extends Optional<? extends U>>  ，返回要求是Optional类型
    Optional<String> s = insurance.map(Insurance::getName);
    return person.flatMap(Person::getCar) //Person::getCar返回的是Optional<Car>，放进之前的Optional后，就会有2层Optional，所以需要flatMap打平
            .flatMap(Car::getInsurance) //同上
            .map(Insurance::getName) //Insurance::getName只返回String，放进之前的Optional后就是Optional<String>，因此直接map方法就行了
            .orElse("Unknown");
  }

  public static void main(String[] args) {
    Insurance insurance = new Insurance();
    insurance.setName("太平洋保险");
    Car car = new Car();
    car.setInsurance(Optional.of(insurance));

    Person person = new Person();
    person.setAge(5);
    person.setCar(Optional.of(car));
    Optional<Person> ops = Optional.ofNullable(person);
    person.getCarInsuranceName2(ops); //获取保险公司名称
  }

}
