package lambdasinaction.chap09;

/**
 * 传统的模板方法设计模式
 * 模板方法（Template Method）模式的定义如下：定义一个操作中的算法骨架，而将算法的一些步骤延迟到子类中，使得子类可以不改变该算法结构的情况下
 * 重定义该算法的某些特定步骤。它是一种类行为型模式。
 */
abstract class OnlineBanking {

  public void processCustomer(int id) {
    Customer c = Database.getCustomerWithId(id);
    makeCustomerHappy(c); //使客户满意
  }

  abstract void makeCustomerHappy(Customer c); //用来给子类重新实现

  // dummy Customer class 用户信息
  static private class Customer {}

  // dummy Database class 数据库
  static private class Database {
    //根据用户id得到用户信息
    static Customer getCustomerWithId(int id) {
      return new Customer();
    }

  }

}
