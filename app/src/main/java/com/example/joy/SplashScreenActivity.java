package com.example.joy;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SplashScreenActivity extends AppCompatActivity {
    Button loginbtn;
    VideoView videoview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }


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
