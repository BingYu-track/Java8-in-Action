package lambdasinaction.chap08;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import static java.util.Map.entry;

/**
 * @version 1.0
 * @Description: Map的各种模式
 * @author: bingyu
 * @date: 2021/8/3
 */
public class PatternsOnMap {

    Map<String, byte[]> dataToHash = new HashMap<>();
    MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

    public PatternsOnMap() throws NoSuchAlgorithmException {

    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        PatternsOnMap p = new PatternsOnMap();
        p.computingOnMaps(); //计算模式
        p.removingFromMaps(); //删除模式
        p.replacingInMaps(); //替换模式
    }

    private byte[] calculateDigest(String key) {
        return messageDigest.digest(key.getBytes(StandardCharsets.UTF_8));
    }

    //Mark: 1.计算模式 computeIfAbsent()——如果指定的键没有对应的值（没有该键或者该键对应的值是空），那么使用该键计算新的值，并将其添加到Map中；
    //参数1: Map里原来的键。  参数2: Function的lambda表达式，用来使用原键生成新的值将其放入Map中
    private void computingOnMaps() {

        //1.1
        List<String> lines = List.of("123","hxw","555");//存储的键
        lines.forEach(line -> //line 是Map中查找的键
                dataToHash.computeIfAbsent(line, this::calculateDigest)); //如果键不存在,就调用calculateDigest方法生成新的值，
                                                                          // 并将该键值对存入dataToHash中


        //1.2
        Map<String, List<String>> friendsToMovies = new HashMap<>();
        String friend = "Raphael";
        List<String> movies = friendsToMovies.get(friend);
        if (movies == null) {
            movies = new ArrayList<>();
            friendsToMovies.put(friend, movies);
        }
        movies.add("Star Wars");
        System.out.println(friendsToMovies); //{Raphael=[Star Wars]}

        //使用computeIfAbsent实现上面的功能
        friendsToMovies.clear();
        friendsToMovies.computeIfAbsent("Raphael", name -> new ArrayList<>())
                .add("Star Wars");
        System.out.println(friendsToMovies); //{Raphael=[Star Wars]}
    }


    //Mark: 2.删除模式  remove(key, value)方法----当Map中key和value都存在时才删除该映射
    private void removingFromMaps() {
        //创建一个可变的Map
        Map<String, String> favouriteMovies = new HashMap<>();
        favouriteMovies.put("Raphael", "Jack Reacher 2");
        favouriteMovies.put("Cristina", "Matrix");
        favouriteMovies.put("Olivia", "James Bond");


        String key = "Raphael";
        String value = "Jack Reacher 2";
        //2.1
        System.out.println("--> Removing an unwanted entry the cumbersome way");
        boolean result = remove(favouriteMovies, key, value);
        System.out.printf("%s [%b]%n", favouriteMovies, result); //{Olivia=James Bond, Cristina=Matrix} [true]

        // Put back the deleted entry for the second test
        favouriteMovies.put("Raphael", "Jack Reacher 2");
        favouriteMovies.put("hxw","pla");

        //2.2 使用重载的remove(key, value)方法
        System.out.println("--> Removing an unwanted the easy way");
        favouriteMovies.remove(key, value);
        favouriteMovies.remove("hxw","123"); //将键对应的值修改
        System.out.printf("%s [%b]%n", favouriteMovies, result); //{Olivia=James Bond, hxw=pla, Cristina=Matrix} [true]
        //发现hxw键值对没有被删除
    }

    /**
     * 当key和value映射在Map对象favouriteMovies里都存在的话，就移除该映射并返回true，否则返回false
     * @param favouriteMovies
     * @param key
     * @param value
     * @return
     */
    private <K, V> boolean remove(Map<K, V> favouriteMovies, K key, V value) {
        if (favouriteMovies.containsKey(key) && Objects.equals(favouriteMovies.get(key), value)) {
            favouriteMovies.remove(key);
            return true;
        }
        return false;
    }


    //Mark: 3.替换模式 replaceAll——通过BiFunction替换Map中每个项的值。该方法的工作模式类似于之前介绍过的List的replaceAll 方法
    private void replacingInMaps() {
        Map<String, String> favouriteMovies = new HashMap<>();
        favouriteMovies.put("Raphael", "Star Wars");
        favouriteMovies.put("Olivia", "james bond");

        System.out.println("--> Replacing values in a map with replaceAll()");
        favouriteMovies.replaceAll((friend, movie) -> movie.toUpperCase()); //将Map里的所有value转为大写
        System.out.println(favouriteMovies); //{Olivia=JAMES BOND, Raphael=STAR WARS}
    }




}
