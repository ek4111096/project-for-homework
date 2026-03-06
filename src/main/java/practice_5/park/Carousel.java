package practice_5.park;

public class Carousel extends Attraction{

    @Override
    public void info() {
        System.out.println("Carousel is calm attraction");
    }

    @Override
    public void maintain() {
        System.out.println("Carousel need frequent maintanance");

    }
}
