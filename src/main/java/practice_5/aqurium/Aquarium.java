package practice_5.aqurium;

public class Aquarium {
    private SeaCreature seaCreature;

    public void addSeaCreature(SeaCreature seaCreature) {
        this.seaCreature = seaCreature;
        System.out.println("SeaCreature was added to aquarium");
    }

    public void showBehaviour() {
        this.seaCreature.move();
    }
}
