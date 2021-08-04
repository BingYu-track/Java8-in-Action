package lambdasinaction.chap09;

import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * 责任链模式：为了避免请求发送者与多个请求处理者耦合在一起，于是将所有请求的处理者通过前一对象记住其下一个对象的引用而连成一条链；当有请求发生时，可将请求沿着这条链传递，直到有对象处理它为止
 */
public class ChainOfResponsibilityMain {

  public static void main(String[] args) {
    //1.传统方式实现责任链
    ProcessingObject<String> p1 = new HeaderTextProcessing();
    ProcessingObject<String> p2 = new SpellCheckerProcessing();
    p1.setSuccessor(p2); //先执行p1里的handle，再将结果传给p2里的handle
    String result1 = p1.handle("Aren't labdas really sexy?!!");
    System.out.println(result1); //output: From Raoul, Mario and Alan: Aren't lambdas really sexy?!!

    UnaryOperator<String> headerProcessing = (String text) -> "From Raoul, Mario and Alan: " + text;
    UnaryOperator<String> spellCheckerProcessing = (String text) -> text.replaceAll("labda", "lambda");
    Function<String, String> pipeline = headerProcessing.andThen(spellCheckerProcessing); //将2个方法结合起来
    String result2 = pipeline.apply("Aren't labdas really sexy?!!"); //output: From Raoul, Mario and Alan: Aren't lambdas really sexy?!!
    System.out.println(result2);
  }

  //抽象处理者
  private static abstract class ProcessingObject<T> {

    protected ProcessingObject<T> successor; //后继对象

    public void setSuccessor(ProcessingObject<T> successor) {
      this.successor = successor;
    }

    //handle方法
    public T handle(T input) {
      T r = handleWork(input);
      if (successor != null) {
        return successor.handle(r);
      }
      return r;
    }

    //抽象处理方法
    abstract protected T handleWork(T input);

  }

  //具体处理者
  private static class HeaderTextProcessing extends ProcessingObject<String> {

    @Override
    public String handleWork(String text) {
      return "From Raoul, Mario and Alan: " + text;
    }

  }

  private static class SpellCheckerProcessing extends ProcessingObject<String> {

    @Override
    public String handleWork(String text) {
      return text.replaceAll("labda", "lambda");
    }

  }

}
