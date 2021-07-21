package lambdasinaction.chap05;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 构建流
 */
public class BuildingStreams {

  public static void main(String... args) throws Exception {
    //1.通过Stream.of用数值来创建流
    Stream<String> stream = Stream.of("Java 8", "Lambdas", "In", "Action");
    stream.map(String::toUpperCase).forEach(System.out::println);

    // Stream.empty调用empty方法创建空的流
    Stream<String> emptyStream = Stream.empty();

    //2.空对象创建流
    String homeValue = System.getProperty("home");
    Stream<String> homeValueStream = homeValue == null ? Stream.empty() : Stream.of(homeValue);

    //借助于java9中的Stream.ofNullable方法，这段代码可以改写得更加简洁：
    Stream<String> homeValueStream2 = Stream.ofNullable(System.getProperty("home"));


    //3.Arrays.stream数组创建流
    int[] numbers = { 2, 3, 5, 7, 11, 13 };
    System.out.println(Arrays.stream(numbers).sum());

    //4. Files.lines文件生成流
    long uniqueWords = 0;
    //流会自动关闭，因此不需要执行额外的try-finally 操作
    try(Stream<String> lines = Files.lines(Paths.get("lambdasinaction/chap5/data.txt"), Charset.defaultCharset())){
      uniqueWords = lines.flatMap(line -> Arrays.stream(line.split(" ")))//生成单词流
                        .distinct()
                        .count();
      System.out.println("There are " + uniqueWords + " unique words in data.txt");
    }
    catch(IOException e){ //如果打开文件时出现异常则加以处理

    }


    /**
     * 5.函数生成流
     */

    // 5.1 Stream.iterate 产生的值有序且连续，由于会不断计算，产生的值没有边界，因此使用该方法时必须使用limit进行限制
    Stream.iterate(0, n -> n + 2)
        .limit(10)
        .forEach(System.out::println);

    // Fibonacci with iterate
    Stream.iterate(new int[] { 0, 1 }, t -> new int[] { t[1], t[0] + t[1] }) //这里lambda表达式里的t就是前面初始值数组，后续会不断计算
        .limit(10)
        .forEach(t -> System.out.printf("(%d, %d)", t[0], t[1]));

    Stream.iterate(new int[] { 0, 1 }, t -> new int[] { t[1], t[0] + t[1] })
        .limit(10)
        .map(t -> t[0])
        .forEach(System.out::println);

    //java9中对Stream的iterate方法进行了增强，可以支持predicate操作，作为第二个参数传入，在这里就是判断大于100时停止内部迭代！
    IntStream.iterate(0, n -> n < 100, n -> n + 4)
            .forEach(System.out::println);

    //5.2 random stream of doubles with Stream.generate
    Stream.generate(Math::random)
        .limit(10)
        .forEach(System.out::println);

    // stream of 1s with Stream.generate
    IntStream.generate(() -> 1)
        .limit(5)
        .forEach(System.out::println);

    IntStream.generate(new IntSupplier() {
      @Override
      public int getAsInt() {
        return 2;
      }
    }).limit(5).forEach(System.out::println);

    IntSupplier fib = new IntSupplier() {

      private int previous = 0;
      private int current = 1;

      @Override
      public int getAsInt() {
        int nextValue = previous + current;
        previous = current;
        current = nextValue;
        return previous;
      }

    };
    IntStream.generate(fib)
        .limit(10)
        .forEach(System.out::println);


  }

}
