package clean_code.complex_tasks.shorten_url;

public class Base62Strategy implements ShorteningStrategy {
    private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private int id = 1;

    @Override
    public String shorten(String url) {
        return toBase62(id++);
    }

    private String toBase62(int number) {
        StringBuilder result = new StringBuilder();

        while (number > 0) {
            result.append(BASE62.charAt(number% 62));
            number /= 62;
        }
        return result.reverse().toString();
    }
}
