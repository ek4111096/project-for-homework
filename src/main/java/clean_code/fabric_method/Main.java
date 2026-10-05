package clean_code.fabric_method;

public class Main {
    public static void main(String[] args) {
        CreationService velik = new CreationService(new Bysicle());
        velik.create();
        CreationService car = new CreationService(new Car());
        car.create();

        WeaponService sword = new WeaponService(new Sword());
        sword.create();
        WeaponService bow = new WeaponService(new Bow());
        bow.create();
        WeaponService pistol = new WeaponService(new Pistol());
        pistol.create();
    }


}
