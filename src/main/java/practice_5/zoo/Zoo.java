package practice_5.zoo;

public class Zoo {
    private Animal animal;

    public void addAnimal(Animal animal) {
        this.animal = animal;
        System.out.println("Animal " + animal + "was added into Zoo");
    }

    public void forceMakeSound() {
        this.animal.makeSound();
    }

    public void forceMakeMove() {
        this.animal.makeMove();
    }

    public void removeAnimal() {
        System.out.println("Animal " + this.animal + "was removed from Zoo");
        this.animal = null;
    }
}
