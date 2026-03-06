package practice_5.farm;

public class Chicken extends DomesticAnimal {
    @Override
    public void produce() {
        System.out.println("Chicken produce eggs");
    }

    @Override
    public void care() {
        System.out.println("Chicken need grain feed");
    }
}
