package practice_5.restaurantMenu;

public class Menu {
    private Dish dish;

    public void addDish(Dish dish) {
        this.dish = dish;
        System.out.println(this.dish + " was added to the menu");
    }

    public void showDescription() {
        this.dish.showDescription();
    }
}
