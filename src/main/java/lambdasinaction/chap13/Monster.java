package lambdasinaction.chap13;

/**
 * @version 1.0
 * @Description:  Monster类通过实现Rotatable, Moveable, Resizable接口，可以移动、缩放和旋转
 *   Monster类自动继承了Rotatable, Moveable, Resizable接口的默认方法，这个例子中是继承了了
 *   rotateBy、moveHorizontally、moveVertically 和setRelativeSize的实现。
 * @author: bingyu
 * @date: 2021/10/8
 */
public class Monster implements Rotatable, Moveable, Resizable{


    public static void main(String[] args) {
        Monster monster = new Monster();
        monster.rotateBy(180); //旋转180度
        monster.moveVertically(10); //垂直移动10间距
    }

    @Override
    public void draw() {

    }

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
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public void setWidth(int width) {

    }

    @Override
    public void setHeight(int height) {

    }

    @Override
    public void setAbsoluteSize(int width, int height) {

    }

    @Override
    public void setRotationAngle(int angleInDegrees) {

    }

    @Override
    public int getRotationAngle() {
        return 0;
    }
}
