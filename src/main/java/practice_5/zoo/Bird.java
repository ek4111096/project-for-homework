package practice_5.zoo;

public class Bird extends Animal{
    @Override
    void makeSound() {
        System.out.println("Tweet");
    }

    @Override
    void makeMove() {
        System.out.println("Fly");
    }
}
