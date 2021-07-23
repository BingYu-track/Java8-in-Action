package lambdasinaction.chap06;
import static java.util.stream.Collectors.joining;
import static lambdasinaction.chap06.Dish.menu;
/**
 * @version 1.0
 * @Description: 连接字符串
 * @author: bingyu
 * @date: 2021/7/23
 */
public class JoinString {

    public static void main(String[] args) {
        String shortMenu = menu.stream().map(Dish::getName).collect(joining()); //joining方法内部使用的StringBuild将字符串连接在一起
        System.out.println(shortMenu); //porkbeefchickenfrench friesriceseason fruitpizzaprawnssalmon

        //上面输出的结果可读性很差，我们加上分隔符
        shortMenu = menu.stream().map(Dish::getName).collect(joining(", "));
        System.out.println(shortMenu); //pork, beef, chicken, french fries, rice, season fruit, pizza, prawns, salmon
    }
}
