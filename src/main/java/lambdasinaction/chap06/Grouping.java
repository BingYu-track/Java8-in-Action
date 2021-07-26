package lambdasinaction.chap06;

import static java.util.Arrays.asList;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;
import static lambdasinaction.chap06.Dish.dishTags;
import static lambdasinaction.chap06.Dish.menu;

import java.util.*;
import java.util.stream.Collector;

/**
 * 分组
 */
public class Grouping {

  enum CaloricLevel { DIET, NORMAL, FAT };



  public static void main(String ... args) {
      System.out.println("Dishes grouped by type: " + groupDishesByType());
      System.out.println("Dishes grouped by caloric level: " + groupDishesByCaloricLevel());
      System.out.println("Caloric dishes grouped by type: " + groupCaloricDishesByType());
      System.out.println("Dish names grouped by type: " + groupDishNamesByType());
      System.out.println("Dish tags grouped by type: " + groupDishTagsByType());
      System.out.println("Dishes grouped by type and caloric level: " + groupDishedByTypeAndCaloricLevel());
      System.out.println("Count dishes in groups: " + countDishesInGroups());
      System.out.println("Most caloric dishes by type: " + mostCaloricDishesByType());
      System.out.println("Most caloric dishes by type: " + mostCaloricDishesByTypeWithoutOprionals());
      System.out.println("Sum calories by type: " + sumCaloriesByType());
      System.out.println("Caloric levels by type: " + caloricLevelsByType());

  }

  // 1.普通分组
  // 根据菜的type分组
  private static Map<Dish.Type, List<Dish>> groupDishesByType() {
    return menu.stream().collect(groupingBy(Dish::getType));
  }
  //output: {MEAT=[pork, beef, chicken], OTHER=[french fries, rice, season fruit, pizza], FISH=[prawns, salmon]}


  //按照菜的热量进行分类,400 卡路里的菜划为“低热量”（diet），把热量在400
  //到700 卡路里之间的菜划为“普通”（normal），而把高于700 卡路里的菜划为“高热量”（fat）
    private static Map<CaloricLevel, List<Dish>> groupDishesByCaloricLevel() {
        return menu.stream().collect(
                groupingBy(dish -> {
                    if (dish.getCalories() <= 400) {
                        return CaloricLevel.DIET; //把分组函数返回的值作为映射的键
                    }
                    else if (dish.getCalories() <= 700) {
                        return CaloricLevel.NORMAL;
                    }
                    else {
                        return CaloricLevel.FAT;
                    }
                })
        );
    }
    //output: {FAT=[pork], DIET=[chicken, rice, season fruit, prawns], NORMAL=[beef, french fries, pizza, salmon]}


    //2.对分组后的元素进行操作
    //执行完分组操作后，你往往还需要"对每个分组中的元素执行操作"，例如，找出那些热量大于500 卡路里的菜肴
    private static Map<Dish.Type, List<Dish>> groupCaloricDishesByType() {
        //2.1 在分组之前执行过滤谓词
        //发现没有任何一道类型是FISH 的菜符合我们的过滤谓词，这个键在结果映射中完全消失了
        //return menu.stream().filter(dish -> dish.getCalories() > 500).collect(groupingBy(Dish::getType));
        //output: {MEAT=[pork, beef], OTHER=[french fries, pizza]}

        //2.2 为了解决上面这个问题，Collectors 类重载了工厂方法groupingBy，除了常见的分类函数，它的第二变量也接受一个Collector类型的参数。
        //通过这种方式，我们把过滤谓词挪到了第二个Collector中
        //Collector<Dish, ?, List<Dish>> filtering = filtering(dish -> dish.getCalories() > 500, toList());
        return menu.stream().collect(
                groupingBy(Dish::getType,
                        filtering(dish -> dish.getCalories() > 500, toList())));

        //output: {MEAT=[pork, beef], OTHER=[french fries, pizza], FISH=[]}
    }

    //2.3 操作分组元素的另一种常见做法---使用一个映射函数对它们进行转换
    private static Map<Dish.Type, List<String>> groupDishNamesByType() {
    return menu.stream().collect(
        groupingBy(Dish::getType,
            mapping(Dish::getName, toList())));
        //output: {MEAT=[pork, beef, chicken], OTHER=[french fries, rice, season fruit, pizza], FISH=[prawns, salmon]}
    }

    //3.提取出每组菜肴对应的标签，使用flatMapping Collector 可以实现
    private static Map<Dish.Type, Set<String>> groupDishTagsByType() {
    return menu.stream().collect(
        groupingBy(Dish::getType,
            flatMapping(dish -> dishTags.get(dish.getName()).stream(), toSet())));
            //这里使用Set的目的是避免同一类型的多道菜由于关联了同样的标签而导致标签重复出现在结果集中
    }
    //output: {MEAT=[salty, greasy, roasted, fried, crisp], OTHER=[salty, greasy, natural, light, tasty, fresh, fried], FISH=[roasted, tasty, fresh, delicious]}



    //4.多级分组
    private static Map<Dish.Type, Map<CaloricLevel, List<Dish>>> groupDishedByTypeAndCaloricLevel() {
//        Collector<Dish, ?, Map<CaloricLevel, List<Dish>>> dishMapCollector = groupingBy((Dish dish) -> {  //二级分类函数
//            if (dish.getCalories() <= 400) {
//                return CaloricLevel.DIET;
//            } else if (dish.getCalories() <= 700) {
//                return CaloricLevel.NORMAL;
//            } else {
//                return CaloricLevel.FAT;
//            }
//        });
        return menu.stream().collect(
            groupingBy(Dish::getType,  //一级分类函数 ，先是根据菜的类型进行分组
                groupingBy((Dish dish) -> {  //二级分类函数，再在前面第一次分组的基础上根据卡路里再次进行分组
                  if (dish.getCalories() <= 400) {
                    return CaloricLevel.DIET;
                  }
                  else if (dish.getCalories() <= 700) {
                    return CaloricLevel.NORMAL;
                  }
                  else {
                    return CaloricLevel.FAT;
                  }
                })
            )
        );
    }
    //output: { MEAT={FAT=[pork], DIET=[chicken], NORMAL=[beef]},
    //          OTHER={DIET=[rice, season fruit],NORMAL=[french fries, pizza]},
    //          FISH={DIET=[prawns], NORMAL=[salmon]}
    //        }
    //这里的外层Map的键就是第一级分类函数生成的值：“fish, meat, other”，而这个Map 的值又是一个Map，
    //键是二级分类函数生成的值：“normal, diet, fat”;最后，第二级Map 的值是流中元素构成的List


    //5. 按子组收集数据

    //5.1 传递给第一个groupingBy的第二个收集器可以是任何类型,这里是找 每个类型有多少个菜
    private static Map<Dish.Type, Long> countDishesInGroups() {
        return menu.stream().collect(groupingBy(Dish::getType, counting()));
    }
    //output: {MEAT=3, OTHER=4, FISH=2}


    //5.2 根据类型查找菜单中热量最高的菜肴
    private static Map<Dish.Type, Optional<Dish>> mostCaloricDishesByType() {
        return menu.stream().collect(
            groupingBy(Dish::getType,
                maxBy((comparingInt(Dish::getCalories)))));
    }
    //output: {MEAT=Optional[pork], OTHER=Optional[pizza], FISH=Optional[salmon]}


    //5.3 根据类型查找菜单中热量最高的菜肴，
    //为了去掉Optional，可以把收集器返回的结果转换为另一种类型，你可以使用Collectors.collectingAndThen 工厂方法返回的收集器
    private static Map<Dish.Type, Dish> mostCaloricDishesByTypeWithoutOprionals() {
        Map<Dish.Type, Dish> mostCaloricByType =
                menu.stream()   //groupingBy第一个收集器
                        .collect(groupingBy(Dish::getType, //分类函数
                                collectingAndThen( //第二个collectingAndThen收集器
                                        maxBy(comparingInt(Dish::getCalories)), //第三个被包装起来的maxBy归约收集器
                                        Optional::get))); //转换函数，将返回的Optional中的值提取出来
        return mostCaloricByType;
    }
    //output: {MEAT=pork, OTHER=pizza, FISH=salmon}
    // 收集器用虚线表示，因此groupingBy 是最外层，根据菜肴的类型把菜单流分组，得到三个子流。
    // groupingBy 收集器包裹着collectingAndThen 收集器，因此分组操作得到的每个子流都用这第二个收集器做进一步归约。
    // collectingAndThen 收集器又包裹着第三个收集器maxBy。
    // 随后由归约收集器进行子流的归约操作，然后包含它的collectingAndThen收集器会对其结果应用Optional::get转换函数。
    // 对三个子流分别执行这一过程并转换而得到的三个值，也就是各个类型中热量最高的Dish，将成为groupingBy 收集器返回的Map
    //   中与各个分类键（Dish 的类型）相关联的值。


    //6. 与groupingBy联合使用的其他收集器
    //6.1 summingInt收集器与groupingBy联合使用
    //安照type对Dish进行分组，并且对每一组Dish求和
    private static Map<Dish.Type, Integer> sumCaloriesByType() {
        return menu.stream().collect(groupingBy(Dish::getType,
            summingInt(Dish::getCalories)));
    }
    //output: {MEAT=1900, OTHER=1550, FISH=850}


    //6.2 mapping收集器与groupingBy联合使用
    private static Map<Dish.Type, Set<CaloricLevel>> caloricLevelsByType() {
        return menu.stream().collect(
            groupingBy(Dish::getType, mapping(
                dish -> {
                  if (dish.getCalories() <= 400) {
                    return CaloricLevel.DIET;
                  }
                  else if (dish.getCalories() <= 700) {
                    return CaloricLevel.NORMAL;
                  }
                  else {
                    return CaloricLevel.FAT;
                  }
                }, //参数1: 一个转换函数，对流中的元素做变换
                toSet() //参数2: 将变换的结果对象收集起来的一个收集器
            ))
        );
    }
    //output: {MEAT=[FAT, DIET, NORMAL], OTHER=[DIET, NORMAL], FISH=[DIET, NORMAL]}



    Map<Dish.Type, Set<CaloricLevel>> caloricLevelsByType =
            menu.stream().collect(
                    groupingBy(Dish::getType, mapping(dish -> {
                                if (dish.getCalories() <= 400) {
                                    return CaloricLevel.DIET;
                                }
                                else if (dish.getCalories() <= 700) {
                                    return CaloricLevel.NORMAL;
                                }
                                else {
                                    return CaloricLevel.FAT;
                                }
                             },
                            toCollection(HashSet::new) ))); //传递一个构造函数引用来要求用HashSet

}
