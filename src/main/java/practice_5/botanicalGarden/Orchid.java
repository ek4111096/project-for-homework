package practice_5.botanicalGarden;

public class Orchid extends Plant{

    @Override
    public void watering() {
        System.out.println("Orchid need high watering");
    }

    @Override
    public void provideLight() {
        System.out.println("Orchid need shade");
    }
}
