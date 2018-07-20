package com.example.joy.FragmentActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.joy.Fragment.AllDrawerItemsActivity;
import com.example.joy.R;

public class CommonActivity extends FragmentActivity {
    FrameLayout frameLayout;
    TextView txt_drawer_heading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        txt_drawer_heading = (TextView) findViewById(R.id.txt_drawer_heading);
        frameLayout = (FrameLayout) findViewById(R.id.framelayout);
        if (getIntent().getStringExtra("Title") != null && getIntent().getStringExtra("Title").equals("Story")) {
            txt_drawer_heading.setText("Story");

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.framelayout, new AllDrawerItemsActivity()).commit();


        }
        if (getIntent().getStringExtra("Title") != null && getIntent().getStringExtra("Title").equals("Schedule")) {
            txt_drawer_heading.setText("Schedule");

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.framelayout, new AllDrawerItemsActivity()).commit();
        }
        if (getIntent().getStringExtra("Title") != null && getIntent().getStringExtra("Title").equals("Travel")) {
            txt_drawer_heading.setText("Travel");

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.framelayout, new AllDrawerItemsActivity()).commit();
        }
        if (getIntent().getStringExtra("Title") != null && getIntent().getStringExtra("Title").equals("Guests")) {
            txt_drawer_heading.setText("Guests");

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.framelayout, new AllDrawerItemsActivity()).commit();
        }
        if (getIntent().getStringExtra("Title") != null && getIntent().getStringExtra("Title").equals("moments")) {
            txt_drawer_heading.setText("Moments");

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.framelayout, new AllDrawerItemsActivity()).commit();
        }
    }
}
