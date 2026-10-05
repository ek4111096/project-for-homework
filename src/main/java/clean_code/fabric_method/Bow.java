package clean_code.fabric_method;

public class Bow implements Attackable {
    @Override
    public void createWeapon() {
        System.out.println("Лук создан");
    }
}
