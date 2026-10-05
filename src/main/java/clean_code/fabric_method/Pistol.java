package clean_code.fabric_method;

public class Pistol implements Attackable {
    @Override
    public void createWeapon() {
        System.out.println("Пистолет создан");
    }
}
