package practice_12.task2_userValidator;

public abstract class User {
    private boolean validationEnabled;
    private String name;
    private int age;
    private String email;

    public User(String email, int age, String name) {

        this.email = email;
        this.age = age;
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public int getAge() {
        return this.age;
    }

    public String getName() {
        return this.name;
    }

    public boolean getValidationEnabled() {
        return this.validationEnabled;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }
}
