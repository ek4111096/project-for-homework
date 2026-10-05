package clean_code.fabric_method;

public class Car implements Creatable{
    @Override
    public void createTransport() {
        System.out.println("Машина создана");
    }
}
