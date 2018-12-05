package codesquad.practice;

public class User {
    private String name;
    private int age;
    private boolean visible;

    public User(String name, int age, boolean visible) {
        this.name = name;
        this.age = age;
        this.visible = visible;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
