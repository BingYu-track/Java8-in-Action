package lambdasinaction.chap17.rxjava;

import io.reactivex.Observable;
import lambdasinaction.chap17.TempInfo;

public class Main {

  public static void main(String[] args) {
    Observable<TempInfo> observable = TempObservable.getTemperature("New York");
    observable.subscribe(new TempObserver());

    try {
      Thread.sleep(10000L);
    }
    catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

}
