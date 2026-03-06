package practice_5.zoo;

public class Main {

    /*
    В зоопарке есть одно животное, но оно может быть разным: птица или слон.
    Каждое животное издаёт уникальные звуки и передвигается по-своему.
    Например, слон трубит и ходит, а птица чирикает и летает.
    Нужно спроектировать систему, которая может работать с любым животным, добавлять его в зоопарк и демонстрировать его поведение.

    Entities: Animal, Bird, Elephant, Zoo
    common methods: makeSound(); move();

    class meneger - Zoo: addAnimal(); removaAnimal(); animal; forceMakeSound(); forceMakeMove();
    Animal - makeSound(); move();
    Bird - no properties, makeSound(); move();
    Elephant - no properties, makeSound(); move();


     */
    public static void main(String[] args) {
        Zoo zooOfMoscow = new Zoo();

        Bird pigeon = new Bird();

        zooOfMoscow.addAnimal(pigeon);
        zooOfMoscow.forceMakeSound();
        zooOfMoscow.forceMakeMove();

        Elephant elephant = new Elephant();
        zooOfMoscow.removeAnimal();
        zooOfMoscow.addAnimal(elephant);
        zooOfMoscow.forceMakeSound();
        zooOfMoscow.forceMakeMove();
    }



}


