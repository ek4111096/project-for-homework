package practice_5.zoo;

public class Elephant extends Animal{
    @Override
    void makeSound() {
        System.out.println("Trumpet");
    }

    @Override
    void makeMove() {
        System.out.println("Walk");
    }
}
