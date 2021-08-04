package lambdasinaction.chap03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * 环绕执行
 */
public class ExecuteAround {

  private static final String FILE = ExecuteAround.class.getResource("./data.txt").getFile();

  public static void main(String... args) throws IOException {
    // method we want to refactor to make more flexible
    String result = processFileLimited();
    System.out.println(result);

    System.out.println("---");

    String oneLine = processFile((BufferedReader b) -> b.readLine()); //传入一个Lambda表达式
    System.out.println(oneLine);

    //读2行
    String twoLines = processFile((BufferedReader b) -> b.readLine() + b.readLine()); ////传入另一个Lambda表达式
    System.out.println(twoLines);
  }

  public static String processFileLimited() throws IOException {
    try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
      return br.readLine();
    }
  }

  public static String processFile(BufferedReaderProcessor p) throws IOException {
    try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
      return p.process(br); //处理BufferedReader对象。而传递过来的p就是行为参数
    }
  }

  @FunctionalInterface //表示这个接口是函数式接口
  public interface BufferedReaderProcessor {

    String process(BufferedReader b) throws IOException;

  }

}
