package clean_code.builder.game_character;

public class Character {
    private int health;
    private int damage;
    private int armor;
    private int magic;

    private Character(Builder builder) {
        this.health = builder.health;
        this.damage = builder.damage;
        this.armor = builder.armor;
        this.magic = builder.magic;
    }

    @Override
    public String toString() {
        return "Character{" +
                "health=" + health +
                ", damage=" + damage +
                ", armor=" + armor +
                ", magic=" + magic +
                '}';
    }

    static class Builder {

        private int health;
        private int damage;
        private int armor;
        private int magic;

        public Builder setHealth(int health) {
            this.health = health;
            return this;
        }

        public Builder setDamage(int damage) {
            this.damage = damage;
            return this;
        }

        public Builder setArmor(int armor) {
            this.armor = armor;
            return this;
        }

        public Builder setMagic(int magic) {
            this.magic = magic;
            return this;
        }

        public Character build() {
            return new Character(this);
        }
    }
}
