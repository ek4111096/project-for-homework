package practice_5.botanicalGarden;

public class BotanicalGarden {
    private Plant plant;

    public void addPlant(Plant plant) {
        this.plant = plant;
        System.out.println("Plant was added");
    }

    public void showWatering() {
        this.plant.watering();
    }

    public void showNeddedLight() {
        this.plant.provideLight();
    }
}
