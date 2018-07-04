package com.example.joy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LoginScreenActivity extends AppCompatActivity {
    TextView newacc;
    RelativeLayout rl_loginbtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        newacc=(TextView)findViewById(R.id.newaccount);
        rl_loginbtn=(RelativeLayout)findViewById(R.id.rl_loginbtn) ;
        newacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginScreenActivity.this,SignupScreenActivity.class);
               startActivity(intent);
               overridePendingTransition(R.anim.slide_in_right,R.anim.slide_in_right);
            }
        });
        rl_loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginScreenActivity.this,EventActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_in_right);
            }
        });
    }
}
