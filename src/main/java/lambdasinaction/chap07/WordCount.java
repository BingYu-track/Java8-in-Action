package lambdasinaction.chap07;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * 自定义实现Spliterator
 */
public class WordCount {

  //句子里添加了一些额外的随机空格，以演示这个迭代实现即使在两个词之间存在多个空格时也能正常工作
  public static final String SENTENCE =
      " Nel   mezzo del cammin  di nostra  vita "
      + "mi  ritrovai in una  selva oscura"
      + " che la  dritta via era   smarrita ";

  public static void main(String[] args) {
    //1.传统迭代
    long startTime = System.nanoTime();
    System.out.println("Found " + countWordsIteratively(SENTENCE) + " words"); //output: Found 19 words
    System.out.println("传统迭代器: Spend " + 1.0 * (System.nanoTime() - startTime) / 1_000_000 + " msecs"); //Spend 14.2122 msecs

    //2. 函数式风格重写单词计数器(非并行)
    long startTime2 = System.nanoTime();
    Stream<Character> stream = IntStream.range(0, SENTENCE.length())
            .mapToObj(SENTENCE::charAt); //将字符串里的每个字符遍历转换成字符所在的index
    System.out.println("Found " + countWords(stream) + " words"); //output: Found 19 words
    System.out.println("顺序流式处理: Spend " + 1.0 * (System.nanoTime() - startTime2) / 1_000_000 + " msecs"); //Spend 3.0606 msecs

    //3. 函数式风格重写单词计数器(并行)
    long startTime3 = System.nanoTime();
    stream = IntStream.range(0, SENTENCE.length())
            .mapToObj(SENTENCE::charAt);
    System.out.println("Found " + countWords(stream.parallel()) + " words"); //output: Found 51 words
    System.out.println("并行流式处理: Spend " + 1.0 * (System.nanoTime() - startTime3) / 1_000_000 + " msecs"); //Spend 5.115699 msecs
    //结果明显不正确 。因为默认的Spliterator每次是拆分一半出去，所以有时一个词会可能被分为两个词，然后遍历了两次。这就说明，拆分流会影响结果，而把
    //顺序流换成并行流就可能使结果出错。

    //4.为了解决上面会任意拆分一半出去，我们就是要确保String不是在随机位置拆开的，而只能在词尾拆开。要做到这一点，必须为Character实现一个Spliterator，
    //它只能在两个词之间拆开String，见下面的WordCounterSpliterator

    //使用WordCounterSpliterator自定义分解迭代器创建并行流进行单词计数
    long startTime4 = System.nanoTime();
    Spliterator<Character> spliterator = new WordCounterSpliterator(SENTENCE);
    stream = StreamSupport.stream(spliterator, true); //从分解迭代器里创建一个并行流
    System.out.println("Found: " + countWords(stream) + " words"); //output: Found 19 words
    System.out.println("自定义分解迭代器生成的并行流处理: Spend " + 1.0 * (System.nanoTime() - startTime4) / 1_000_000 + " msecs"); //Spend 0.8167 msecs
  }

  /**
   * 1.传统的单词计数器：判断给定的字符串有多少个单词(根据空格区分的)
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
   * 3.归约统计单词数量，从0和空白单词开始作为初始值，然后持续对单词总数进行累加
   * @param stream
   * @return
   */
  private static int countWords(Stream<Character> stream) {
    WordCounter wordCounter = stream.reduce(new WordCounter(0, true), //初始值
                                            WordCounter::accumulate, //累加器，等效于 (WordCounter e,Character c) -> e.accumulate(c)，用来累加counter
                                            WordCounter::combine); //组合器(注意: 该组合器只有并行时才会执行)
    return wordCounter.getCounter();
  }

  private static class WordCounter { //这是一个Immutable class，因为我们无法改变这个类的对象的状态

    private final int counter; //用来计算到目前为止数过的字数
    private final boolean lastSpace; //用来记得上一个遇到的Character是不是空格

    public WordCounter(int counter, boolean lastSpace) {
      this.counter = counter;
      this.lastSpace = lastSpace;
    }
    //每次遍历到Stream中的一个新的Character 时，就会调用accumulate 方法
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
   * 基于单词计数器的可拆分迭代器
   */
  private static class WordCounterSpliterator implements Spliterator<Character> {

    private final String string; //待测试的字符串
    private int currentChar = 0; //当前字符在整个字符串中的index，从0开始

    private WordCounterSpliterator(String string) {
      this.string = string;
    }

    @Override
    public boolean tryAdvance(Consumer<? super Character> action) {
      action.accept(string.charAt(currentChar++));  //处理当前字符
      return currentChar < string.length(); //如果还有字符要处理，则返回true
    }

    @Override
    public Spliterator<Character> trySplit() {
      int currentSize = string.length() - currentChar; //得到当前剩下要解析的字符数
      if (currentSize < 10) { //如果剩下的字符数小于10，则不再拆分
        return null; //返回null 表示要解析的String已经足够小，可以顺序处理
      }
      for (int splitPos = currentSize / 2 + currentChar; //将拆分位置设定为剩下要解析的String的中间
           splitPos < string.length(); splitPos++) {
        if (Character.isWhitespace(string.charAt(splitPos))) { //直接从拆分位置开始前进，直到遇见下一个空格
          //创建一个新WordCounter-Spliterator来解析String从开始位置到当前的拆分位置的部分作为分块，
          //这时这个子串可能包含多个单词，但是这个子串的开头和末尾一定都是空格
          Spliterator<Character> spliterator = new WordCounterSpliterator(string.substring(currentChar, splitPos));
          currentChar = splitPos; //将当前的拆分位置作为起始位置
          return spliterator; //发现一个空格并创建了新的Spliterator，所以退出循环
        }
      }
      return null;
    }

    @Override
    public long estimateSize() {
      return string.length() - currentChar; //字符串长度减去当前字符的index，就是剩下未处理的字符数量
    }

    @Override
    public int characteristics() {
      return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
    }

  }

}
