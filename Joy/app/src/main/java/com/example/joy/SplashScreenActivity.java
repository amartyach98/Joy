package com.example.joy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

public class SplashScreenActivity extends AppCompatActivity {
    Button loginbtn;
    VideoView videoview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        loginbtn = (Button) findViewById(R.id.loginbtn);
        videoview = (VideoView) findViewById(R.id.videoview);
        ///set video in background
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.weeding_view);
        videoview.setDrawingCacheEnabled(true);
        videoview.setVideoURI(uri);
        videoview.requestFocus();
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashScreenActivity.this, LoginScreenActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        videoview.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoview.start();
    }
}
