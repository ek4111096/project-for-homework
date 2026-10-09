package clean_code.complex_tasks.video_service;

public class Video {
    private final int id;
    private final String path;
    private final String format;

    public Video(int id, String path, String format) {
        this.id = id;
        this.path = path;
        this.format = format;
    }

    public int getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public String getFormat() {
        return format;
    }
}
