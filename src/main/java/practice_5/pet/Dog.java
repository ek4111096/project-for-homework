package practice_5.pet;

public class Dog extends Pet{

    @Override
    public void eat() {
        System.out.println("Dog eat dry food");
    }

    @Override
    public void interact() {
        System.out.println("Dog walk");
    }
}
