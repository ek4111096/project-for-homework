package clean_code.builder.game_character;

public class Main {
    public static void main(String[] args) {
        Character warrior = new Character.Builder()
                .setHealth(100)
                .setDamage(80)
                .setArmor(90)
                .setMagic(10)
                .build();

        Character mage = new Character.Builder()
                .setHealth(60)
                .setDamage(30)
                .setArmor(20)
                .build();

        System.out.println(warrior);
        System.out.println(mage);
    }
}
