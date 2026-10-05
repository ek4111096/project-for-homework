package clean_code.fabric_method;

public class Bysicle implements Creatable{
    @Override
    public void createTransport() {
        System.out.println("Велосипед создан");
    }
}
