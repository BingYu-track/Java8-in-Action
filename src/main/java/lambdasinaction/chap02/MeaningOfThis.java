package lambdasinaction.chap02;

/**
 * 匿名类谜题
 */
public class MeaningOfThis {

  public final int value = 4;

  public void doIt() {
    int value = 6;
    Runnable r = new Runnable() {
      public final int value = 5;
      @Override
      public void run() {
        int value = 10;
        System.out.println(this.value); //这里输出的是5，因为匿名类里的this指的是Runnable接口的内部实现类
      }
    };
    r.run();
  }

  public static void main(String... args) {
    MeaningOfThis m = new MeaningOfThis();
    m.doIt(); // ???
  }

}
