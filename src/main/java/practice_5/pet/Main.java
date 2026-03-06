package practice_5.pet;

public class Main {
    /*
    Условие:
    В системе может быть один домашний питомец, у каждого вида свои особенности ухода.
    Примеры:
    Собака гуляет и ест сухой корм
    Кошка играет и ест влажный корм
    Нужно спроектировать систему, которая позволяет взаимодействовать с любым питомцем с учётом его особенностей.

    Entities: Pet, Cat, Dog, PetManager
    Properties and actions: food, walk, play, manage;

     */
    public static void main(String[] args) {
        PetManger petManger = new PetManger();
        Cat cat = new Cat();
        petManger.addPet(cat);
        petManger.forceEat();
        petManger.forceInteract();
        petManger.removePet();

        Dog dog = new Dog();
        petManger.addPet(dog);
        petManger.forceEat();
        petManger.forceInteract();
    }
}
