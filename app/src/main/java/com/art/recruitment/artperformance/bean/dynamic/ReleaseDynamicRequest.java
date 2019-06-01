package com.art.recruitment.artperformance.bean.dynamic;

import java.util.List;

public class ReleaseDynamicRequest {
    private String content;
    private List<String> imagePath;
    private String videoPath;
    private String videoPreview;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImagePath() {
        return imagePath;
    }

    public void setImagePath(List<String> imagePath) {
        this.imagePath = imagePath;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getVideoPreview() {
        return videoPreview;
    }

    public void setVideoPreview(String videoPreview) {
        this.videoPreview = videoPreview;
    }
}
