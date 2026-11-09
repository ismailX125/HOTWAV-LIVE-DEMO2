package com.hotwav.demo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.MotionEvent;
import android.widget.VideoView;
import android.widget.FrameLayout;
import android.net.Uri;
import android.os.Environment;
import android.view.WindowManager;
import android.media.MediaPlayer;
import java.io.File;

public class MainActivity extends Activity {

    VideoView video;
    Handler handler = new Handler();

    Runnable returnToVideo = () -> showVideo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        );

        video = new VideoView(this);
        setContentView(video);

        hideBars();
        showVideo();
    }

    void hideBars() {
        getWindow().getDecorView().setSystemUiVisibility(
            5894 | 1024 | 512
        );
    }

    void showVideo() {

        File folder = new File(
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_MOVIES
            ), "HOTWAV"
        );

        File file = new File(folder, "demo.mp4");

        if (!file.exists()) return;

        video.setVideoURI(Uri.fromFile(file));

        video.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            video.start();
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        handler.removeCallbacks(returnToVideo);

        if (event.getAction() == MotionEvent.ACTION_UP) {
            handler.postDelayed(returnToVideo, 30000);
        }

        return super.dispatchTouchEvent(event);
    }
}
