package practice_9;

public class ThreadSolvers {
    static volatile boolean stop = false;


    public static void main(String[] args) throws InterruptedException {
        //1
        Thread t = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("Hello from thread!");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        t.start();

        //2
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("A");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("B");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        //3
        Thread t3 = new Thread(() -> {
            int count = 0;
            while (!stop) {
                count++;
                System.out.println(count);
            }
        });

        t3.start();
        Thread.sleep(2000);
        stop = true;
        System.out.println("Stop signal sent");


    }
}
