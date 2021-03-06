package com.example.joy;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.joy.FragmentActivity.CommonActivity;

public class EventDetailActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        FloatingActionButton logout = (FloatingActionButton) findViewById(R.id.logout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        relativeLayout = (RelativeLayout) findViewById(R.id.status_share);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.logout);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = LayoutInflater.from(EventDetailActivity.this);
                View mView = layoutInflater.inflate(R.layout.dialog_logout, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(EventDetailActivity.this);
                alertDialogBuilderUserInput.setView(mView);
                alertDialogBuilderUserInput.setMessage("Do you want to Logout?");
                final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {

                            }
                        })
                        .setNegativeButton("No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Story) {
            Intent intent = new Intent(EventDetailActivity.this, CommonActivity.class);
            intent.putExtra("Title", "Story");
            startActivity(intent);
        } else if (id == R.id.nav_Schedule) {
            Intent intent = new Intent(EventDetailActivity.this, CommonActivity.class);
            intent.putExtra("Title", "Schedule");
            startActivity(intent);
        } else if (id == R.id.nav_Travel) {
            Intent intent = new Intent(EventDetailActivity.this, CommonActivity.class);
            intent.putExtra("Title", "Travel");
            startActivity(intent);
        } else if (id == R.id.nav_Guests) {
            Intent intent = new Intent(EventDetailActivity.this, CommonActivity.class);
            intent.putExtra("Title", "Guests");
            startActivity(intent);
        } else if (id == R.id.nav_moments) {
            Intent intent = new Intent(EventDetailActivity.this, CommonActivity.class);
            intent.putExtra("Title", "moments");
            startActivity(intent);

        } else if (id == R.id.nav_Settings) {
            Intent intent = new Intent(EventDetailActivity.this, SettingActivity.class);

            startActivity(intent);


        } else if (id == R.id.nav_ExitEvent) {
            Intent intent = new Intent(EventDetailActivity.this, EventActivity.class);

            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
