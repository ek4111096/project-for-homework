package practice_5.botanicalGarden;

public class Cactus extends Plant{
    @Override
    public void watering() {
        System.out.println("Cactus need infrequent watering");
    }

    @Override
    public void provideLight() {
        System.out.println("Cactus need lots of light");
    }
}
