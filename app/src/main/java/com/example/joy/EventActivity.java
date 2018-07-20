package com.example.joy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class EventActivity extends AppCompatActivity {

    android.support.v7.widget.Toolbar toolbar;
    CardView cardView;
    FloatingActionButton floatingActionButton;
    RelativeLayout moreoption_img;
    GoogleSignInClient mGoogleSignInClient;
    TextView couplename, date;
    String MyPREFERENCES = "DBVALUES";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_item);
        final GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(EventActivity.this);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        couplename = (TextView) findViewById(R.id.couple_name);
        date = (TextView) findViewById(R.id.txt_date);
        cardView = (CardView) findViewById(R.id.cardView);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);

        String fname = sharedPreferences.getString("First_Name", "");
        String pfname = sharedPreferences.getString("Partners_First_Name", "");
        String addeventdate = sharedPreferences.getString("Date", "");
        couplename.setText(fname+" Weds "+pfname);
        date.setText(addeventdate);
       // Toast.makeText(getApplicationContext(),fname+""+pfname,Toast.LENGTH_SHORT).show();
        moreoption_img = (RelativeLayout) findViewById(R.id.moreoption_img);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventActivity.this, EventDetailActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
            }
        });
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingbtn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(EventActivity.this);
                View mView = layoutInflaterAndroid.inflate(R.layout.dialog_join_create, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(EventActivity.this);
                alertDialogBuilderUserInput.setView(mView);

                final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Join", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                // ToDo get user input here
                            }
                        })
                        .setNegativeButton("Create",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        Intent intent = new Intent(EventActivity.this, CreateEventActivity.class);
                                        startActivity(intent);
                                    }
                                })
                        .setNeutralButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();
            }
        });
        moreoption_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(EventActivity.this);
                View mView = layoutInflater.inflate(R.layout.dialog_logout, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(EventActivity.this);
                alertDialogBuilderUserInput.setView(mView);
                alertDialogBuilderUserInput.setMessage("Do you want to Logout?");
                //  final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {

                                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                                //log out in case login with google
                                if (account != null) {
                                    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                            .requestEmail()
                                            .build();
                                    mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);
                                    mGoogleSignInClient.signOut();
                                    Intent intent = new Intent(EventActivity.this, LoginScreenActivity.class);
                                    startActivity(intent);
                                    //log out in case login with Facebook
                                } else if (AccessToken.getCurrentAccessToken() != null) {
                                    LoginManager.getInstance().logOut();
                                    Intent intent = new Intent(EventActivity.this, LoginScreenActivity.class);
                                    startActivity(intent);
                                    //log out in case login email & password
                                } else {
                                    Intent intent = new Intent(EventActivity.this, LoginScreenActivity.class);
                                    startActivity(intent);
                                }

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

    }


}
