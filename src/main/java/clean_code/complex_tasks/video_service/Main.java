package clean_code.complex_tasks.video_service;

public class Main {
    public static void main(String[] args) {
        VideoService videoService = new VideoService(new AviAdapter(), new MovAdapter(), new WmvAdapter());

        Video video = videoService.uploadVideo("path/to/example.avi");

        videoService.streamVideo(video);
    }
}
