package lambdasinaction.chap09;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 工厂模式
 */
public class FactoryMain {

  public static void main(String[] args) {
    //1.传统方式生产产品
    Product p1 = ProductFactory.createProduct("loan");
    System.out.printf("p1: %s%n", p1.getClass().getSimpleName());

    //2.使用指定产品的构造引用得到生产者函数式接口对象，生产贷款
    Supplier<Product> loanSupplier = Loan::new;
    Product p2 = loanSupplier.get();
    System.out.printf("p2: %s%n", p2.getClass().getSimpleName());

    //3.使用lambda的构造引用，更灵活的生产各种产品
    Product p3 = ProductFactory.createProductLambda("loan");
    System.out.printf("p3: %s%n", p3.getClass().getSimpleName());
  }

  /**
   * 工厂类
   */
  static private class ProductFactory {

    public static Product createProduct(String name) {
      switch (name) {
        case "loan":  //贷款
          return new Loan();
        case "stock":  //股票
          return new Stock();
        case "bond":  //债券
          return new Bond();
        default:
          throw new RuntimeException("No such product " + name);
      }
    }

    public static Product createProductLambda(String name) {
      Supplier<Product> p = map.get(name); //根据产品名称获取对应产品的构造引用
      if (p != null) {
        return p.get();
      }
      throw new RuntimeException("No such product " + name);
    }
  }

  static private interface Product {}
  static private class Loan implements Product {}
  static private class Stock implements Product {}
  static private class Bond implements Product {}

  final static private Map<String, Supplier<Product>> map = new HashMap<>(); //map里存储的是产品和各个产品的构造引用
  static {
    map.put("loan", Loan::new);
    map.put("stock", Stock::new);
    map.put("bond", Bond::new);
  }

}
