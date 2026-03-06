package practice_5.farm;

public class Main {
    /*
    На ферме содержится одно домашнее животное.
    У каждого животного свои функции и нужды.
    Примеры:
    Корова даёт молоко, нуждается в выпасе
    Курица несёт яйца, требует зерно
    Нужно создать систему, которая позволяет управлять животным с учётом его потребностей.

    Entities: Farm, DomesticAnimal, Cow, Chicken

    Properties, actions, connections:
        common: produce(); care();
        Cow: produce milk(); need to be grazed();
        Chicken: produce eggs(); need grain ffed();
        Farm: addAnimal(); showProduction(); showNeddedCare();

     */

    public static void main(String[] args) {
        Farm moscowFarm = new Farm();
        Cow cow = new Cow();
        moscowFarm.addDomesticAnimal(cow);
        moscowFarm.showNeddedCare();
        moscowFarm.showProduction();
        Chicken chicken = new Chicken();
        moscowFarm.addDomesticAnimal(chicken);
        moscowFarm.showProduction();
        moscowFarm.showNeddedCare();
    }
}
