package lambdasinaction.chap07;

/**
 * @version 1.0
 * @Description:
 * @author: bingyu
 * @date: 2021/7/28
 */
public class ForkJoinPool {

    public static void main(String[] args) {
        int i = Runtime.getRuntime().availableProcessors();
        System.out.println(i);
    }
}
