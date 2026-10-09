package clean_code.complex_tasks.shorten_url;

public class UrlShortenerService {
    private final ShorteningStrategy strategy;
    private final UrlStorage storage;

    public UrlShortenerService(ShortenerFactory shortenerFactory) {
        this.strategy = shortenerFactory.createStratagy();
        this.storage = UrlStorage.getInstance();
    }



    public String shortenUrl(String longUrl) {
        String shortUrl = strategy.shorten(longUrl);
        storage.save(shortUrl, longUrl);
        return shortUrl;
    }

    public String expandUrl(String shortUrl) {
        return storage.get(shortUrl);
    }
}
