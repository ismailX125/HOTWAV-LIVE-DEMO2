package com.hotwav.demo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.MotionEvent;
import android.widget.VideoView;
import android.net.Uri;
import android.view.WindowManager;

public class MainActivity extends Activity {

    VideoView video;
    Handler handler = new Handler();

    Runnable returnToVideo = new Runnable() {
        @Override
        public void run() {
            showVideo();
        }
    };

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
            View.SYSTEM_UI_FLAG_FULLSCREEN |
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );
    }

    void showVideo() {
        video.setVideoURI(Uri.parse(
            "android.resource://" + getPackageName() + "/" +
            getResources().getIdentifier(
                "demo", "raw", getPackageName()
            )
        ));

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
