package clean_code.fabric_method;

public class WeaponService {
    private final Attackable weapon;

    public WeaponService(Attackable weapon) {
        this.weapon = weapon;
    }

    public void create() {
        weapon.createWeapon();
    }
}
