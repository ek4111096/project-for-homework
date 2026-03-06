package practice_5.restaurantMenu;

public class MainCourse extends Dish {
    private int temperature;

    public MainCourse(int temperature) {
        this.temperature = temperature;
    }

    public int getTemperature() {
        return this.temperature;
    }


    @Override
    public void showDescription() {
        System.out.println("MainCourse has temperature - " + this.temperature);
    }
}
