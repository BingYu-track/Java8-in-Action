package lambdasinaction.chap09;

import java.util.ArrayList;
import java.util.List;

/**
 * 观察者模式: 指多个对象间存在一对多的依赖关系，当一个对象的状态发生改变时，所有依赖于它的对象都得到通知并被自动更新。
 *            这种模式有时又称作发布-订阅模式、模型-视图模式，它是对象行为型模式 concrete
 */
public class ObserverMain {

  public static void main(String[] args) {
    //1.传统方式实现观察者模式
    Feed f = new Feed();
    //注册了三家外媒
    f.registerObserver(new NYTimes());
    f.registerObserver(new Guardian());
    f.registerObserver(new LeMonde());
    //将消息传递给所有外媒
    f.notifyObservers("The queen said her favourite book is Java 8 & 9 in Action!");
    //输出: Yet another news in London... The queen said her favourite book is Java 8 & 9 in Action!


    //2.lambda表示实现观察者模式
    Feed feedLambda = new Feed();
      //传入lambda注册观察者
    feedLambda.registerObserver((String tweet) -> {
      if (tweet != null && tweet.contains("money")) {
        System.out.println("Breaking news in NY! " + tweet);
      }
    });

    feedLambda.registerObserver((String tweet) -> {
      if (tweet != null && tweet.contains("queen")) {
        System.out.println("Yet another news in London... " + tweet);
      }
    });

    feedLambda.notifyObservers("Money money money, give me money!");
    //输出: Breaking news in NY! Money money money, give me money!

    /*
    Mark:重要注意事项
    是否随时随地都可以使用Lambda 表达式呢？答案是否定的！前面介绍的例子中，Lambda适配得很好，那是因为需要执行的动作都很简单，
    因此才能很方便地消除僵化代码。但是，观察者的逻辑有可能十分复杂，它们可能还持有状态，抑或定义了多个方法，诸如此类。在
    这些情形下，你还是应该继续使用类的方式
     */
  }

  /**
   * 观测者接口，不同观察者收到通知后会触发不同的行为
   */
  interface Observer {
    void notify(String tweet); //通知
  }

  /**
   * Subject(主题) 它提供了一个用于保存观察者对象的聚集类和增加、删除观察者对象的方法，以及通知所有观察者的抽象方法
   */
  interface Subject {
    void registerObserver(Observer o); //注册观察者
    void notifyObservers(String tweet); //通知观察者
  }

  /**
   * 观察者1:美国的《纽约时报》，对"金钱“敏感
   */
  static private class NYTimes implements Observer {

    @Override
    public void notify(String tweet) {
      if (tweet != null && tweet.contains("money")) {
        System.out.println("Breaking news in NY!" + tweet);
      }
    }

  }

  /**
   * 观察者2: 英国的《卫报》，对”皇后“敏感
   */
  static private class Guardian implements Observer {

    @Override
    public void notify(String tweet) {
      if (tweet != null && tweet.contains("queen")) {
        System.out.println("Yet another news in London... " + tweet);
      }
    }

  }

  /**
   * 观察者3: 法国的《世界报》对"红酒"敏感
   */
  static private class LeMonde implements Observer {

    @Override
    public void notify(String tweet) {
      if (tweet != null && tweet.contains("wine")) {
        System.out.println("Today cheese, wine and news! " + tweet);
      }
    }

  }

  /**
   *
   */
  static private class Feed implements Subject {

    private final List<Observer> observers = new ArrayList<>();

    @Override
    public void registerObserver(Observer o) {
      observers.add(o);
    }

    @Override
    public void notifyObservers(String tweet) { //遍历所有的观察者并将信息传给它们
      observers.forEach(o -> o.notify(tweet));
    }
  }

}
