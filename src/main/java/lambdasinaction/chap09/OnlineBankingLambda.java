package lambdasinaction.chap09;

import java.util.function.Consumer;

/**
 * lambda实现模板方法设计模式
 */
public class OnlineBankingLambda {

  public static void main(String[] args) {
    //通过传递Lambda 表达式，直接插入不同的行为，不再需要再继承OnlineBanking类了
    //佐证了Lambda表达式能帮助你解决设计模式与生俱来的设计僵化问题
    new OnlineBankingLambda().processCustomer(1337, (Customer c) -> System.out.println("Hello!"));
    new OnlineBankingLambda().processCustomer(1337, (Customer c) -> System.out.println("Hello " + c.getName()));
  }

  public void processCustomer(int id, Consumer<Customer> makeCustomerHappy) {
    Customer c = Database.getCustomerWithId(id);
    makeCustomerHappy.accept(c);
  }

  // dummy Customer class
  static private class Customer {
    private String name;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }

  // dummy Database class
  static private class Database {

    static Customer getCustomerWithId(int id) {
      return new Customer();
    }

  }

}
