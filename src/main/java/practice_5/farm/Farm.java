package practice_5.farm;

public class Farm {
    private DomesticAnimal domesticAnimal;

    public void addDomesticAnimal(DomesticAnimal domesticAnimal) {
        this.domesticAnimal = domesticAnimal;
        System.out.println("Animal was added");
    }

    public void showProduction() {
        this.domesticAnimal.produce();
    }

    public void showNeddedCare() {
        this.domesticAnimal.care();
    }
}
