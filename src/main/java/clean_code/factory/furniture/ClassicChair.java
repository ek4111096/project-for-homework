package clean_code.factory.furniture;

public class ClassicChair implements Chair{
    @Override
    public void createChair() {
        System.out.println("Classic chair created");
    }
}
