package lambdasinaction.chap03;

import java.util.HashMap;
import java.util.concurrent.Callable;

public class Apple {

  private Integer weight = 0;
  private String country;
  private Color color;

  public Apple() {

  }

  public Apple(Integer weight) {
    this.weight = weight;
  }

  public Apple(Integer weight, Color color) {
    this.weight = weight;
    this.color = color;
  }

  public Integer getWeight() {
    return weight;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public void setWeight(Integer weight) {
    this.weight = weight;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  @SuppressWarnings("boxing")
  @Override
  public String toString() {
    return String.format("Apple{color=%s, weight=%d}", color, weight);
  }

  public Callable<String> fetch() {
    return () -> "Tricky example ;-)";
  }

}