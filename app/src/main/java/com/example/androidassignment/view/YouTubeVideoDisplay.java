package com.example.androidassignment.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.androidassignment.R;
import com.example.androidassignment.model.youtube.YouTubeVideoModel;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

//Displays the movie trailer using the YouTubeBaseActivity

public class YouTubeVideoDisplay extends YouTubeBaseActivity {

    private YouTubePlayerView youTubePlayerView; //Get the YouTube player view from the layout

    @Override
    protected void onCreate(Bundle bundle) {

        super.onCreate(bundle);
        setContentView(R.layout.trailer_view_layout);

        youTubePlayerView = findViewById(R.id.video_player);

        //Get the id of the trailer video
        Intent intent = getIntent();
        String trailerId = intent.getStringExtra("trailerId");

        YouTubePlayer.OnInitializedListener onInitializedListener; //Listener object on the YouTubePlayer

        YouTubeVideoModel youTubeVideoModel = new YouTubeVideoModel(trailerId); //Pass the trailer id to the YouTubeViewModel


        onInitializedListener = new YouTubePlayer.OnInitializedListener() {

            //When the video load is successful
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(youTubeVideoModel.getVideoId());
                youTubePlayer.setFullscreen(true);

            }

            //In case of error
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult
                    youTubeInitializationResult) {
                Toast.makeText(getApplicationContext(), "Something went wrong.\nPlease try again after some time.", Toast.LENGTH_SHORT).show();
            }
        };

        //Initializing the YouTubePlayer
        youTubePlayerView.initialize(YouTubeVideoModel.getApiKey(),onInitializedListener);

    }

}
