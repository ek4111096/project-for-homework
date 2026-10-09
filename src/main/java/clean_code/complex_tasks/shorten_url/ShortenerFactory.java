package clean_code.complex_tasks.shorten_url;

public class ShortenerFactory {
    private final ShorteningStrategy strategy;

    public ShortenerFactory(ShorteningStrategy strategy) {
        this.strategy = strategy;
    }

    public ShorteningStrategy createStratagy() {
        return strategy;
    }
}
