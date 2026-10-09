package clean_code.complex_tasks.video_service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VideoService {
    private List<VideoAdapter> adapters;

    public VideoService(VideoAdapter... adapters) {
        this.adapters = new ArrayList<>(Arrays.asList(adapters));
    }

    public Video uploadVideo(String path) {
        for (VideoAdapter adapter : adapters) {
            if (adapter.supports(path)) {
                return adapter.convert(path);
            }
        }
        throw new IllegalArgumentException("Формат не поддерживается");
    }

    public void streamVideo(Video video) {
        System.out.println("Стримминг видео в формате " + video.getFormat() + " , Video ID " + video.getId());
    }
}
