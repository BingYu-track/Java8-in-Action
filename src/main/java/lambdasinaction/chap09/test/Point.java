package lambdasinaction.chap09.test;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @version 1.0
 * @Description: 测试lambda表达式
 * @author: bingyu
 * @date: 2021/8/5
 */
public class Point {

    /*
    要对代码中的Lambda函数进行测试实际上比较困难，因为你无法通过函数名的方式调用它们,但有些时候你可以借助某个字段访问Lambda函数，
    这种情况，你可以利用这些字段，通过它们对封装在Lambda 函数内的逻辑进行测试。假设你在Point类中添加了静态字段compareByXAndThenY，
    通过该字段，使用方法引用你可以访问Comparator对象
     */
    public final static Comparator<Point> compareByXAndThenY = comparing(Point::getX).thenComparing(Point::getY);//比较x和y

    private final int x;
    private final int y;

    public Point() {
        x = 2;
        y = 3;
    }

    private Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX() { return x; }
    public int getY() { return y; }

    public Point moveRightBy(int x){
        return new Point(this.x + x, this.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Test //该代码存在问题，由于没有无参构造，单元测试会报错
    public void testMoveRightBy() throws Exception {
        Point p1 = new Point(5, 5);
        Point p2 = p1.moveRightBy(10);
        assertEquals(15, p2.getX());
        assertEquals(5, p2.getY());
    }


    /*
     Lambda 表达式会生成函数接口的一个实例。由此，你可以测试该实例的行为。这个例子中，我们可以使用不同的参数，
     对Comparator对象类型实例compareByXAndThenY的compare 方法进行调用，验证它们的行为是否符合预期：
     */
    @Test
    public void testComparingTwoPoints() throws Exception {
        Point p1 = new Point(10, 15);
        Point p2 = new Point(10, 20);
        int result = Point.compareByXAndThenY.compare(p1 , p2);
        assertTrue(result < 0);
    }

    //将点的横坐标移动x距离
    public static List<Point> moveAllPointsRightBy(List<Point> points, int x){
        return points.stream()
                .map(p -> new Point(p.getX() + x, p.getY()))
                .collect(toList());
    }

    /*
        没必要对Lambda 表达式p -> new Point(p.getX() + x,p.getY())进行测试，它
        只是moveAllPointsRightBy 内部的实现细节。我们更应该关注的是方法moveAllPoints-RightBy 的行为
     */
    @Test
    public void testMoveAllPointsRightBy() throws Exception{
        List<Point> points = Arrays.asList(new Point(5, 5), new Point(10, 5));
        //预期的Point列表
        List<Point> expectedPoints = Arrays.asList(new Point(15, 5), new Point(20, 5));
        //将横坐标移动10
        List<Point> newPoints = Point.moveAllPointsRightBy(points, 10);
        System.out.println(expectedPoints);
        System.out.println(newPoints);
        System.out.println(expectedPoints.equals(newPoints));
        assertEquals(expectedPoints, newPoints); //内部调用的equals方法，Point类恰当地实现equals方法很重要
    }


}
