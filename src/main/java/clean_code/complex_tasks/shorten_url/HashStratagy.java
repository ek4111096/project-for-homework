package clean_code.complex_tasks.shorten_url;

public class HashStratagy implements ShorteningStrategy{
    @Override
    public String shorten(String url) {
        return String.valueOf(Math.abs(url.hashCode()));
    }
}
