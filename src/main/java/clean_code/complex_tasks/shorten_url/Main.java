package clean_code.complex_tasks.shorten_url;

public class Main {
    public static void main(String[] args) {
        UrlShortenerService shortenerService = new UrlShortenerService(new ShortenerFactory(new Base62Strategy()));

        String shortUrl = shortenerService.shortenUrl("https://example.com/very/long/url");
        System.out.println("Короткий URL "+ shortUrl);

        String longUrl = shortenerService.expandUrl(shortUrl);
        System.out.println("Оригинальный URL "+ longUrl);
    }
}
