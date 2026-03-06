package practice_5.park;

public class Main {
    /*
    В парке есть аттракцион, каждый из которых предлагает свои ощущения и требует обслуживания.
    Примеры:
    Американские горки — проверка безопасности
    Карусель — техническое обслуживание
    Нужно создать систему, которая управляет аттракционом и выводит информацию для посетителей.

    Entities:
    AmusementPark, Attraction, Carousel, RollerCoaster
    Properties, actions, connections:
        Attraction: info(); maintain();
        Carousel: calm, frequent maintanance;
        RollerCoaster: thrills, safety inspections;


     */

    public static void main(String[] args) {
        AmusementPark park = new AmusementPark();
        Carousel carousel = new Carousel();
        park.setAttraction(carousel);
        park.infoAboutAttraction();
        park.operateAttraction();
        RollerCoaster rollerCoaster = new RollerCoaster();
        park.setAttraction(rollerCoaster);
        park.infoAboutAttraction();
        park.operateAttraction();
    }
}
