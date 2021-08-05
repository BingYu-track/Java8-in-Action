package lambdasinaction.chap09;

import java.util.Arrays;
import java.util.List;

/**
 * 查看栈跟踪帮助调试lambda表达式的
 */
public class Debugging {

  public static int divideByZero(int n){
    return n / 0;
  }

  public static void main(String[] args) {
    List<Point> points = Arrays.asList(new Point(12, 2), null);
    points.stream().map(p -> p.getX()).forEach(System.out::println);
    /*
      异常信息:
       Exception in thread "main" java.lang.NullPointerException
	      at lambdasinaction.chap09.Debugging.lambda$main$0(Debugging.java:17)
          .............

        因为Lambda 表达式没有名字，所以编译器只能为它们指定一个名字。在这个例子中，它的名字是lambda$main$0，看起来非常不直观
     */

    //但是，如果方法引用指向的是同一个类中声明的方法，那么它的名称是可以在栈跟踪中显示的。
    List<Integer> numbers = Arrays.asList(1, 2, 3);
    numbers.stream().map(Debugging::divideByZero).forEach(System
            .out::println);
    /*
        异常信息:
        Exception in thread "main" java.lang.ArithmeticException: / by zero
	      at lambdasinaction.chap09.Debugging.divideByZero(Debugging.java:12)  正确的看到了抛异常的方法信息
	      .................
     */
  }

  private static class Point {

    private int x;
    private int y;

    private Point(int x, int y) {
      this.x = x;
      this.y = y;
    }

    public int getX() {
      return x;
    }

    public void setX(int x) {
      this.x = x;
    }

  }

}
