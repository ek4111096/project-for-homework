package practice_11;

public class DebugTask6 {
    public static void main(String[] args) {
        countdown(5);
    }
    public static void countdown(int n) {
        if (n >= 1) {
            System.out.println(n);
            countdown(n - 1);
        }

    }
}
