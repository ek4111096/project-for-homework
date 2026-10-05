package clean_code.facade.smart_home;

public class SmartHomeFacade {
    private final Light light;
    private final AirConditioner airConditioner;
    private final SecuritySystem securitySystem;

    public SmartHomeFacade() {
        light = new Light();
        airConditioner = new AirConditioner();
        securitySystem = new SecuritySystem();
    }

    public void turnOnEverything() {
        light.turnOn();
        airConditioner.turnOn();
        securitySystem.turnOn();
    }

    public void turnOffEverything() {
        light.turnOff();
        airConditioner.turnOff();
        securitySystem.turnOff();
    }
}
