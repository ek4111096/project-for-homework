package practice_12.task1_universalEntityManager;

import java.util.Objects;

public abstract class Entity {
    private int age;
    private String name;
    private boolean isActive;

    public Entity(int age, String name, boolean isActive) {

        if (age < 10 || age > 120) {
            throw new IllegalArgumentException(
                    "Age must be between 10 and 120"
            );
        }

        this.age = age;
        this.name = name;
        this.isActive = isActive;
    }

    public Entity(int age, String name) {
        if (age < 10 || age > 120) {
            throw new IllegalArgumentException(
                    "Age must be between 10 and 120"
            );
        }
        this.age = age;
        this.name = name;
    }

    public int getAge() {

        return this.age;
    }

    public String getName() {

        return this.name;
    }

    public boolean IsActive() {

        return isActive;
    }

    public void setAge(int age) {
        if (age > 0 ) {
            this.age = age;
        } else System.out.println("Вы ввели невалидный возраст");
    }

    public void setName(String name) {

        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return age == entity.age && isActive == entity.isActive && Objects.equals(name, entity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, name, isActive);
    }
}
