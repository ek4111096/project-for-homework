package clean_code.complex_tasks.video_service;

public class WmvAdapter implements VideoAdapter {
    @Override
    public Video convert(String path) {
        System.out.println("Конвертация WMV в MP4");

        return new Video(path.hashCode(), path, "MP4");
    }

    @Override
    public boolean supports(String path) {
        return path.toLowerCase().endsWith(".wmv");
    }
}
