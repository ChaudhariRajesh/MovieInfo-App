package com.example.androidassignment.model.youtube;

//Model class for the YouTubePlayer

public class YouTubeVideoModel {

    //API Key of the YouTube Service
    private static final String API_KEY = "MY_API_KEY";
    private String videoId;

    public YouTubeVideoModel(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public static String getApiKey() {
        return API_KEY;
    }

}
