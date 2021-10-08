package lambdasinaction.chap13;

/**
 * @version 1.0
 * @Description: 可旋转接口
 * @author: bingyu
 * @date: 2021/10/8
 */
public interface Rotatable {

    //设置旋转角度
    void setRotationAngle(int angleInDegrees);
    //获取旋转角度
    int getRotationAngle();

    //默认的旋转方法
    default void rotateBy(int angleInDegrees){
        setRotationAngle((getRotationAngle () + angleInDegrees) % 360);
    }
}
