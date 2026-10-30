package generators;

public class TestUtils {

    private TestUtils() {}

    public static void repeat(int times, Runnable action) {
        for (int i = 0; i < times; i++) {
            action.run();
        }
    }
}