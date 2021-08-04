package lambdasinaction.chap08;

import java.util.HashMap;
import java.util.Map;

import static java.util.Map.entry;

/**
 * @version 1.0
 * @Description: Map的merge方法
 * @author: bingyu
 * @date: 2021/8/3
 */
public class MergeOnMaps {

    public static void main(String[] args) {
        MergeOnMaps m = new MergeOnMaps();
        m.mergingMaps();
    }


    //合并临时的两个Map
    private void mergingMaps() {
        //第1个Map
        Map<String, String> family = Map.ofEntries(
                entry("Teo", "Star Wars"),
                entry("Cristina", "James Bond"));
        //第2个Map
        Map<String, String> friends = Map.ofEntries(entry("Raphael", "Star Wars"));

        System.out.println("--> Merging the old way");
        //1.使用传统方式合并Map
        Map<String, String> everyone = new HashMap<>(family);
        everyone.putAll(friends); //将两个Map合并到了一起
        System.out.println(everyone);

        //2.使用Map的merge()方法来更灵活的进行合并，该方法使用BiFunction方法处理重复的键
        Map<String, String> friends2 = Map.ofEntries(
                entry("Raphael", "Star Wars"),
                entry("Cristina", "Matrix")); //friends2和family存在重复的键Cristina
        System.out.println("--> Merging maps using merge()");
        Map<String, String> everyone2 = new HashMap<>(family);
        friends2.forEach((k, v) ->
                //BiFunction<? super V, ? super V, ? extends V>
                //k、v是friends2的键值对，movie1和movie2分表是everyone2和friends2重复键的值
                everyone2.merge(k, v, (movie1, movie2) -> movie1 + " & " + movie2)); //如果存在重复的键，就连接两个值，将两值合并为一个值
        System.out.println(everyone2); //{Raphael=Star Wars, Cristina=James Bond & Matrix, Teo=Star Wars}

        //3.merge方法处理空值逻辑，
        Map<String, String> family3 = new HashMap<>();
        family3.put("Teo", "Star Wars");
        family3.put("Cristina", null);
        Map<String, String> friends3 = new HashMap<>();
        friends3.put("Raphael", "Star Wars");
        friends3.put("Cristina", "Matrix");
        Map<String, String> everyone3 = new HashMap<>(family3);
        System.out.println("merge方法处理空值----------");
        friends3.forEach((k, v) ->
                //everyone3这个调用方的value作为旧值，如果为null，则以friends3中的value作为新的值进行代替，如果新的value值也为空就会删除该键值对
                everyone3.merge(k, v, (movie1, movie2) -> movie1 + " & " + movie2)); //这里k、v是不能为空的，方法内部会进行非空校验，但是调用merge方法
        //的map的value可以为空
        System.out.println(everyone3); //{Raphael=Star Wars, Cristina=Matrix, Teo=Star Wars}

        //4.
        Map<String, Long> moviesToCount = new HashMap<>();
        String movieName = "James Bond";
        moviesToCount.merge(movieName, 1L, (key, count) -> count + 1L);
        System.out.println(moviesToCount);
    }
}
