package com.example.joy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.joy.AppHelper.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignupScreenActivity extends AppCompatActivity {
    EditText r_email, r_pass;
    RelativeLayout signupbtn;
    String email, password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        r_email = (EditText) findViewById(R.id.r_email);
        r_pass = (EditText) findViewById(R.id.r_pass);
        signupbtn = (RelativeLayout) findViewById(R.id.signupbtn);
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = r_email.getText().toString().trim();
                password = r_pass.getText().toString().trim();
                vol_signup();
            }
        });


    }

    public void vol_signup() {
        String URl_signup = "https://reqres.in/api/register";
        Map<String, String> map = new HashMap<String, String>();
        map.put("email", email);
        map.put("password",password);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(URl_signup, new JSONObject(map), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String token = response.getString("token");
                    Log.d("token:::::::::::::::::", token);
                    Toast.makeText(SignupScreenActivity.this, "Registration Success", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(SignupScreenActivity.this,LoginScreenActivity.class);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }
}
