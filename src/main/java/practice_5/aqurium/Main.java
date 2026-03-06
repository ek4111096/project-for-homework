package practice_5.aqurium;

public class Main {
    /*
    В аквариуме может находиться одно морское существо.
    В зависимости от существа, оно ведёт себя по-разному.
    Примеры:
    Акула плавает быстро и агрессивно
    Морская звезда медленно ползает
    Нужно спроектировать систему, в которой можно добавить существо и продемонстрировать его поведение.

    Entities:
    Aquarium, Sea creature, Shark, Sea Star
    Properties, actions, connections:
        Sea creature - move();
        Shark - swim fast and aggresive;
        Sea Star - crowl slowly;
        Aquarium - addSeaCreature();
                 - showBehavior();
     */

    public static void main(String[] args) {
        Aquarium aquarium = new Aquarium();
        Shark shark = new Shark();
        aquarium.addSeaCreature(shark);
        aquarium.showBehaviour();

        SeaStar seaStar = new SeaStar();
        aquarium.addSeaCreature(seaStar);
        aquarium.showBehaviour();
    }
}
