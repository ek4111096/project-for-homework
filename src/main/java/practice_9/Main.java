package practice_9;

public class Main {
    //4
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        Thread a = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        });

        Thread b = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        });

        a.start();
        b.start();

        a.join();
        b.join();

        System.out.println("The value of counter is: " + counter.getCount());
    }
}
