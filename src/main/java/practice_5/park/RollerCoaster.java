package practice_5.park;

public class RollerCoaster extends Attraction{
    @Override
    public void info() {
        System.out.println("RollerCoaster is thrill");
    }

    @Override
    public void maintain() {
        System.out.println("RollerCoaster need safety inspections");
    }
}
