package clean_code.complex_tasks.shorten_url;

import java.util.HashMap;
import java.util.Map;

public class UrlStorage {
    private static UrlStorage urlStorage;

    private final Map<String, String> storage = new HashMap<>();

    public static UrlStorage getInstance() {
        if (urlStorage == null) {
            urlStorage = new UrlStorage();
        }
        return urlStorage;
    }

    public void save(String shortUrl, String longUrl) {
        storage.put(shortUrl, longUrl);
    }

    public String get(String shortUrl) {
        return storage.get(shortUrl);
    }
}
