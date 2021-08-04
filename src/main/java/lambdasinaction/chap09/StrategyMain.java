package lambdasinaction.chap09;

/**
 * 策略模式
 */
public class StrategyMain {

  public static void main(String[] args) {
    //传统代码，需要创建不同类的对象IsNumeric、IsAllLowerCase
    Validator v1 = new Validator(new IsNumeric());
    System.out.println(v1.validate("aaaa"));
    Validator v2 = new Validator(new IsAllLowerCase());
    System.out.println(v2.validate("bbbb"));

    // with lambdas使用lambdas不再需要创建IsNumeric类和IsAllLowerCase类了，更加方便
    //Lambda 表达式避免了采用策略设计模式时僵化的模板代码
    Validator v3 = new Validator((String s) -> s.matches("\\d+"));
    System.out.println(v3.validate("aaaa"));
    Validator v4 = new Validator((String s) -> s.matches("[a-z]+"));
    System.out.println(v4.validate("bbbb"));
  }

  /**
   * 定义校验策略接口
   */
  interface ValidationStrategy {
    boolean execute(String s);
  }

  /**
   * 必须是小写
   */
  static private class IsAllLowerCase implements ValidationStrategy {

    @Override
    public boolean execute(String s) {
      return s.matches("[a-z]+");
    }

  }

  /**
   * 必须是数字
   */
  static private class IsNumeric implements ValidationStrategy {

    @Override
    public boolean execute(String s) {
      return s.matches("\\d+");
    }

  }

  /**
   *
   */
  static private class Validator {

    private final ValidationStrategy strategy;

    public Validator(ValidationStrategy v) {
      strategy = v;
    }

    public boolean validate(String s) {
      return strategy.execute(s);
    }
  }

}
