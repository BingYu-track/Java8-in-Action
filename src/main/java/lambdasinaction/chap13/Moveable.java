package lambdasinaction.chap13;

/**
 * @version 1.0
 * @Description:
 * @author: bingyu
 * @date: 2021/10/8
 */
public interface Moveable {

    int getX();
    int getY();
    void setX(int x);
    void setY(int y);

    //水平移动
    default void moveHorizontally(int distance){
        setX(getX() + distance);
    }
    //垂直移动
    default void moveVertically(int distance){
        setY(getY() + distance);
    }
}
