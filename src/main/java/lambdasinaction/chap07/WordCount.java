package lambdasinaction.chap07;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 *
 */
public class WordCount {

  //句子里添加了一些额外的随机空格，以演示这个迭代实现即使在两个词之间存在多个空格时也能正常工作
  public static final String SENTENCE =
      " Nel   mezzo del cammin  di nostra  vita "
      + "mi  ritrovai in una  selva oscura"
      + " che la  dritta via era   smarrita ";

  public static void main(String[] args) {
    //System.out.println("Found " + countWordsIteratively(SENTENCE) + " words");
    System.out.println("Found " + countWords(SENTENCE) + " words");
  }

  /**
   * 单词计数器：判断给定的字符串有多少个单词(根据空格区分的)
   * @param s 字符串
   * @return
   */
  public static int countWordsIteratively(String s) {
    int counter = 0; //单词计数器
    boolean lastSpace = true;
    for (char c : s.toCharArray()) { //逐个遍历字符串中的所有字符
      if (Character.isWhitespace(c)) {
        lastSpace = true;
      } else {
        if (lastSpace) {
          counter++; //上一个字符是空格，而当前遍历的字符不是空格时，将单词计数器加一
        }
        lastSpace = Character.isWhitespace(c);
      }
    }
    return counter;
  }

  /**
   * 函数式风格重写单词计数器
   * @param s
   * @return
   */
  public static int countWords(String s) {
    System.out.println(s.length()); //109个字符
    Stream<Character> stream = IntStream.range(0, s.length()) //遍历从0开始到字符串的长度
            .mapToObj(SENTENCE::charAt) //将数字映射成字符
            .parallel();
//    Spliterator<Character> spliterator = new WordCounterSpliterator(s);
//    Stream<Character> stream = StreamSupport.stream(spliterator, true);

    return countWords(stream);
  }

  /**
   * 统计
   * @param stream
   * @return
   */
  private static int countWords(Stream<Character> stream) {
    WordCounter wordCounter = stream.reduce(new WordCounter(0, true), WordCounter::accumulate, WordCounter::combine);
    return wordCounter.getCounter();
  }

  private static class WordCounter { //这是一个Immutable class，因为我们无法改变这个类的对象的状态

    private final int counter;
    private final boolean lastSpace;

    public WordCounter(int counter, boolean lastSpace) {
      this.counter = counter;
      this.lastSpace = lastSpace;
    }

    //和迭代算法一样，accumulate方法一个个遍历Character
    public WordCounter accumulate(Character c) {
      if (Character.isWhitespace(c)) { //判断当前字符是否是空格
        return lastSpace ?
                this :  //上一个字符是空格，当前遍历的字符也是空格
                new WordCounter(counter, true); //上一个字符不是空格，当前遍历的字符是空格
      } else {
        //执行到这里，说明当前字符不是空格
        return lastSpace ?
                new WordCounter(counter + 1, false) :  //上一个字符是空格，而当前遍历的字符不是空格时，将单词计数器加一
                this; //上一个字符不是空格，当前遍历的字符也不是空格
      }

    }

    //合并两个WordCounter，把其计数器加起来
    public WordCounter combine(WordCounter wordCounter) {
      return new WordCounter(counter + wordCounter.counter, wordCounter.lastSpace);
    }

    public int getCounter() {
      return counter;
    }

  }

  /**
   * 基于单词计数器的可分迭代器
   */
  private static class WordCounterSpliterator implements Spliterator<Character> {

    private final String string;
    private int currentChar = 0;

    private WordCounterSpliterator(String string) {
      this.string = string;
    }

    @Override
    public boolean tryAdvance(Consumer<? super Character> action) {
      action.accept(string.charAt(currentChar++));
      return currentChar < string.length();
    }

    @Override
    public Spliterator<Character> trySplit() {
      int currentSize = string.length() - currentChar;
      if (currentSize < 10) {
        return null;
      }
      for (int splitPos = currentSize / 2 + currentChar; splitPos < string.length(); splitPos++) {
        if (Character.isWhitespace(string.charAt(splitPos))) {
          Spliterator<Character> spliterator = new WordCounterSpliterator(string.substring(currentChar, splitPos));
          currentChar = splitPos;
          return spliterator;
        }
      }
      return null;
    }

    @Override
    public long estimateSize() {
      return string.length() - currentChar;
    }

    @Override
    public int characteristics() {
      return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
    }

  }

}
