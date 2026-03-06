package practice_5.botanicalGarden;

public class Main {
    /*
    В ботаническом саду содержится одно растение.
    У разных растений — разные требования к уходу.
    Примеры:
    Орхидея — высокая влажность и тень
    Кактус — много света, редкий полив
    Нужно спроектировать систему, в которой можно добавить растение и обеспечить нужный уход.

    Entities:
    BotanicalGarden, Plant, Orchid, Cactus
    Properties, actions, connections:
        Plant: watering(); provideLight();
        Orchid: high watering(); need shade();
        Cactus: infrequent watering(); need lots of light();


     */

    public static void main(String[] args) {
        BotanicalGarden myGarden = new BotanicalGarden();
        Orchid orchid = new Orchid();
        myGarden.addPlant(orchid);
        myGarden.showNeddedLight();
        myGarden.showWatering();
        Cactus cactus = new Cactus();
        myGarden.addPlant(cactus);
        myGarden.showWatering();
        myGarden.showNeddedLight();
    }
}
