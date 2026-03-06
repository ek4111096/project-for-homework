package practice_5.museum;

public class Main {
    /*
    Условие:
    В музее выставлен экспонат.
    У каждого экспоната своя история и условия хранения.
    Примеры:
    Манускрипт — требует контролируемой влажности
    Скульптура — нуждается в реставрации
    Нужно создать систему, которая управляет экспонатом и предоставляет информацию о нём.
    Entities:
    Exhibit, Manuscript, Sculpture, Museum
    Properties, actions, connections:
        Exhibit: describe(); preserve();
        Manuscript: description, preserve();
        Sculpture: description, preserve();

     */

    public static void main(String[] args) {
        Museum museum = new Museum();
        Manuscript manuscript = new Manuscript();
        museum.setExhibit(manuscript);
        museum.showInfo();
        museum.showPreservation();
        Sculpture sculpture = new Sculpture();
        museum.setExhibit(sculpture);
        museum.showInfo();
        museum.showPreservation();
    }
}
