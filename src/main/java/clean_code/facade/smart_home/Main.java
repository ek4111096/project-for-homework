package clean_code.facade.smart_home;

public class Main {
    public static void main(String[] args) {
        SmartHomeFacade smartHome = new SmartHomeFacade();
        smartHome.turnOnEverything();
        System.out.println();
        smartHome.turnOffEverything();
    }
}
