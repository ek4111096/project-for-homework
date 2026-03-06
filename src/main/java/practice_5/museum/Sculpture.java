package practice_5.museum;

public class Sculpture extends Exhibit{
    @Override
    public void describe() {
        System.out.println("Info about Sculpture");
    }

    @Override
    public void preserve() {
        System.out.println("Need regular restoration");
    }
}
