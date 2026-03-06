package practice_5.restaurantMenu;

public class Main {
    /*
    В меню ресторана добавляется блюдо.
    У каждого блюда свои параметры отображения:
    Горячее блюдо — с температурой
    Напиток — с объёмом
    Нужно спроектировать систему, в которой можно добавлять блюда и выводить их описание с учётом характеристик.

    Entities: Dish, MainCourse, Drink, Menu

    Properties, actions, connections:
        MainCourse - temperature;
        Drink - volume;
        Dish - showDescription();
        Menu - addDish(); showDescription();
     */

    public static void main(String[] args) {
        Menu menu = new Menu();
        MainCourse hotMeat = new MainCourse(180);
        menu.addDish(hotMeat);
        menu.showDescription();

        Drink popStar = new Drink(150);
        menu.addDish(popStar);
        menu.showDescription();
    }


}
