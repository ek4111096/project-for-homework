package practice_5.museum;

public class Manuscript extends Exhibit{

    @Override
    public void describe() {
        System.out.println("Info about Manuscript");
    }

    @Override
    public void preserve() {
        System.out.println("Need temprature and humidity control");
    }
}
