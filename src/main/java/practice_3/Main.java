package practice_3;

public class Main {
    public static void main(String[] args) {
        Company company1 = new Company(1, "Vanya");
        Company company2 = new Company(2, "Petya");
        Company.companyName = "Romashka";
        Company.printCompanyName();
        //company1.employeeID = 5;

        //MathConstants
        System.out.println("CircleArea is " + MathConstants.calculateCircleArea(5)); //outprint CircleArea
        System.out.println("Circumference is " + MathConstants.calculateCircumference(9)); //outprint Circumference

        Library lib1 = new Library();
        lib1.getBookTitle();

        //University
        University student1 = new University(1, "Petya");
        University student2 = new University(2, "Katya");
        University student3 = new University(3, "Olya");

        University.changeUniversityName("MGTU");
        student1.printStudentInfo();
        student2.printStudentInfo();
        student3.printStudentInfo();

        //GameSettings
        GameSettings game1 = new GameSettings("FirstGame", 3);
        GameSettings game2 = new GameSettings("SecondGame", 4);
        GameSettings.setMaxPlayers(9);
        game1.addPlayer();
        game2.addPlayer();
        game1.printGameStatus();
        game2.printGameStatus();

        //Person
        Person person1 = new Person("Ivan", "Ivanov", "123-45-6789");
        Person person2 = new Person("Olga", "Ivanova", "321-54-6789");
        person2.setLastName("Petrova");
        person1.printPersonInfo();
        person2.printPersonInfo();

    }
}
