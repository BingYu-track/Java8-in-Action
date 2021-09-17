package lambdasinaction.chap11;

import java.util.Optional;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

/**
 * @version 1.0
 * @Description: 所有Optional内容总结
 * @author: bingyu
 * @date: 2021/9/17
 */
public class Summary {




    public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty("a", "5");
        props.setProperty("b", "true");
        props.setProperty("c", "-3");

        assertEquals(5, readDuration(props, "a")); //如果属性是a，readDuration 方法就返回5(因为该属性对应的字符串能映射到一个正数)
        assertEquals(0, readDuration(props, "b")); //对于属性b，方法的返回值是0，因为它对应的值不是一个数字
        assertEquals(0, readDuration(props, "c")); //对于c，方法的返回值是0，因为虽然它对应的值是个数字，但它是个负数
        assertEquals(0, readDuration(props, "d")); //对于d，方法的返回值是0，因为并不存在该名称对应的属性
    }

    //命令行的方式编写代码(最终的实现既复杂又不具备可读性，呈现为多个由if 语句及try/catch块构成的嵌套条件。花几分钟时间思考一下如何重构改进)
    public static int readDuration(Properties props, String name) {
        String value = props.getProperty(name);
        if (value != null) { //确保名称对应的属性存在
            try {
                int i = Integer.parseInt(value); //将String属性转换为数字类型
                if (i > 0) { //检查返回的数字是否为正数
                    return i;
                }
            } catch (NumberFormatException nfe) { }
        }
        return 0; //如果前述的条件都不满足，返回0
    }

    //练习:尝试使用Optional类提供的特性及OptionalUtility中提供的工具方法，通过一条精炼的语句重构上面代码
    public static int readDuration2(Properties props, String name) {
                //获取属性值，如果为空就返回空的Optional
        return Optional.ofNullable(props.getProperty(name))
                //在将Optional里的String转为Integer
                .flatMap(OptionalUtility::stringToInteger)
                //最后过滤负数
                .filter(i -> i > 0)
                //否则，返回0
                .orElse(0);
    }


//    如果需要访问的属性值不存在，Properties.getProperty(String)方法的返
//    回值就是一个null，使用ofNullable 工厂方法可以方便地将该值转换为Optional 对象。
//    接着，你可以向它的flatMap 方法传递代码清单11-7 中实现的OptionalUtility.
//    stringToInt 方法的引用，将Optional<String>转换为Optional<Integer>。最后，你
//    非常轻易地就可以过滤掉负数。这种方式下，如果任何一个操作返回一个空的Optional 对
//    象，该方法都会返回orElse 方法设置的默认值0；否则就返回封装在Optional 对象中的正
//    整数。下面就是这段简化的实现：



}
