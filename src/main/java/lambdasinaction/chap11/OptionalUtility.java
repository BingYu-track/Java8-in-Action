package lambdasinaction.chap11;

import java.util.Optional;
import java.util.OptionalInt;

/**
 * @version 1.0
 * @Description: Optional应用实例
 * @author: bingyu
 * @date: 2021/9/17
 */
public class OptionalUtility {

    public static Optional<Integer> stringToInteger(String s) {
        //Integer.parseInt(s); //抛出异常
        try {
            return Optional.of(Integer.parseInt(s)); //如果String能转换为对应的Integer，会将其封装在Optional对象中返回，否则直接返回空的Optional，且不会抛出异常
        } catch (NumberFormatException e) {
            return Optional.empty(); //否则返回一个空的Optional对象
        }
    }

    //Optional 对象无法由基础类型的Optional 组合构成,且基础的Optional不支持很多操作m，比如map、filter，因此
//    public static OptionalInt stringToInt(String s) {
//        //Integer.parseInt(s); //抛出异常
//        try {
//            return Optional.of(Integer.parseInt(s)); //如果String能转换为对应的Integer，会将其封装在Optional对象中返回，否则直接返回空的Optional，且不会抛出异常
//        } catch (NumberFormatException e) {
//            return Optional.empty(); //否则返回一个空的Optional对象
//        }
//    }


    public static void main(String[] args) {
        Optional<Integer> dadsa = stringToInteger("dadsa");
        //System.out.println(dadsa.get());
    }
}
