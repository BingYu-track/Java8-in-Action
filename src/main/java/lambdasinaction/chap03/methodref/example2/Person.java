package lambdasinaction.chap03.methodref.example2;

/**
 * @version 1.0
 * @Description:
 * @author: bingyu
 * @date: 2021/7/13
 */
public class Person {

    private String name;

    private Integer age;

    private String gender;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAgeAndName(String name ,Integer age) {
        this.name = name;
        this.age = age;
    }
}
