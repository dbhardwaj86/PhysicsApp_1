package com.example.deepak.physicsapp_1;

import com.example.deepak.physicsapp_1.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailLoader.ErrorReason;
import com.google.android.youtube.player.YouTubeThumbnailView;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class LectureActivity extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener, YouTubeThumbnailView.OnInitializedListener{

    public static final String API_KEY = "AIzaSyB_92jyeowh0rW8aazzAi1ORTYsoKYk3JA";
    public static final String VIDEO_ID = "o7VVHhK9zf0";
    public static final String PLAYLIST_ID = "PLHLoLiL1nL8G7_JGVKGefUxllIZnP39vU";

    private YouTubePlayer youTubePlayer;
    private YouTubePlayerView youTubePlayerView;
    private YouTubeThumbnailView youTubeThumbnailView;
    private YouTubeThumbnailLoader youTubeThumbnailLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture);

        youTubePlayerView = (YouTubePlayerView)findViewById(R.id.youtubeplayerview);
        youTubePlayerView.initialize(API_KEY, this);

        youTubeThumbnailView = (YouTubeThumbnailView)findViewById(R.id.youtubethumbnailview);
        youTubeThumbnailView.initialize(API_KEY, this);
        youTubeThumbnailView.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View arg0) {
                if(youTubePlayer != null){
                    youTubePlayer.play();
                }
            }});
    }

    @Override
    public void onInitializationFailure(Provider provider,
                                        YouTubeInitializationResult result) {
        Toast.makeText(getApplicationContext(),
                "YouTubePlayer.onInitializationFailure()",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInitializationSuccess(Provider provider, YouTubePlayer player,
                                        boolean wasRestored) {

        youTubePlayer = player;

        Toast.makeText(getApplicationContext(),
                "YouTubePlayer.onInitializationSuccess()",
                Toast.LENGTH_LONG).show();

        if (!wasRestored) {
            player.cueVideo(VIDEO_ID);
        }
    }

    @Override
    public void onInitializationFailure(YouTubeThumbnailView thumbnailView,
                                        YouTubeInitializationResult error) {

        Toast.makeText(getApplicationContext(),
                "YouTubeThumbnailView.onInitializationFailure()",
                Toast.LENGTH_LONG).show();

    }

    @Override
    public void onInitializationSuccess(YouTubeThumbnailView thumbnailView,
                                        YouTubeThumbnailLoader thumbnailLoader) {

        Toast.makeText(getApplicationContext(),
                "YouTubeThumbnailView.onInitializationSuccess()",
                Toast.LENGTH_LONG).show();

        youTubeThumbnailLoader = thumbnailLoader;
        thumbnailLoader.setOnThumbnailLoadedListener(new ThumbnailLoadedListener());

        //youTubeThumbnailLoader.setVideo("X-zPrXJpiAw");
        youTubeThumbnailLoader.setPlaylist(PLAYLIST_ID);

    }

    private final class ThumbnailLoadedListener implements
            YouTubeThumbnailLoader.OnThumbnailLoadedListener {

        @Override
        public void onThumbnailError(YouTubeThumbnailView arg0, ErrorReason arg1) {
            Toast.makeText(getApplicationContext(),
                    "ThumbnailLoadedListener.onThumbnailError()",
                    Toast.LENGTH_LONG).show();
        }

        @Override
        public void onThumbnailLoaded(YouTubeThumbnailView arg0, String arg1) {
            Toast.makeText(getApplicationContext(),
                    "ThumbnailLoadedListener.onThumbnailLoaded()",
                    Toast.LENGTH_LONG).show();

        }

    }

}


