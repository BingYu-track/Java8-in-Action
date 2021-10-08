package lambdasinaction.chap13;

/**
 * 改变大小的接口
 */
public interface Resizable extends Drawable {

  int getWidth();
  int getHeight();
  void setWidth(int width);
  void setHeight(int height);
  void setAbsoluteSize(int width, int height);
  //TODO: 增加方法后，所有实现类都会编译错误
  //void setRelativeSize(int widthFactor, int heightFactor);

  /**
   * 为了以兼容的方式改进这个库（即使用该库的用户不需要修改他们实现了Resizable 的类），可以使用默
   * 认方法，提供setRelativeSize 的默认实现：
   * @param wFactor
   * @param hFactor
   */
  default void setRelativeSize(int wFactor, int hFactor){
    setAbsoluteSize(getWidth() / wFactor, getHeight() / hFactor);
  }

}
