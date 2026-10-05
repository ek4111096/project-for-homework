package clean_code.fabric_method;

public class Sword implements Attackable {
    @Override
    public void createWeapon() {
        System.out.println("Меч создан");
    }
}
