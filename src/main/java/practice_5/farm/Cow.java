package practice_5.farm;

public class Cow extends DomesticAnimal {
    @Override
    public void produce() {
        System.out.println("Cow produce milk");
    }

    @Override
    public void care() {
        System.out.println("Cow need to be grazed");
    }
 }
