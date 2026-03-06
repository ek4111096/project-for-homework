package practice_5.restaurantMenu;

public class Drink extends Dish {
    private int volume;

    public Drink(int volume) {
        this.volume = volume;
    }

    private int getVolume() {
        return this.volume;
    }

    @Override
    public void showDescription() {
        System.out.println("Drink has volume - " + this.volume);
    }
}
