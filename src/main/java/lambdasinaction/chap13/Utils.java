package lambdasinaction.chap13;

import java.util.List;

public class Utils {

  public static void paint(List<Resizable> l) {
    l.forEach(r -> {
      //调用每个形状自己的setAbsoluteSize方法
      r.setAbsoluteSize(42, 42);
    });

    //TODO: uncomment, read the README for instructions
    //l.forEach(r -> { r.setRelativeSize(2, 2); });
  }

}
