package lambdasinaction.chap13;

/**
 * @version 1.0
 * @Description: sun可以旋转和移动，但是不能缩放
 * @author: bingyu
 * @date: 2021/10/8
 */
public class Sun implements Rotatable, Moveable{
    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public void setX(int x) {

    }

    @Override
    public void setY(int y) {

    }

    @Override
    public void setRotationAngle(int angleInDegrees) {

    }

    @Override
    public int getRotationAngle() {
        return 0;
    }
}
