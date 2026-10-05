package clean_code.factory.furniture;

public class ModernChair implements Chair{
    @Override
    public void createChair() {
        System.out.println("Modernchair created");
    }
}
