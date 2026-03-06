package practice_5.pet;

public class Cat extends Pet{
    @Override
    public void eat() {
        System.out.println("Cat eat wet food");
    }

    @Override
    public void interact() {
        System.out.println("Cat play");
    }
}
