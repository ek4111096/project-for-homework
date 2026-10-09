package clean_code.complex_tasks.video_service;

public interface VideoAdapter {
    Video convert(String path);
    boolean supports(String path);
}
