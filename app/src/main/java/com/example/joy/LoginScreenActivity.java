package com.example.joy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.joy.AppHelper.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;


public class LoginScreenActivity extends AppCompatActivity {
    TextView newacc;
    RelativeLayout rl_loginbtn;
    EditText et_email, et_pass;
    String email, password;
    boolean isLoggedIn;
    int req_code;
    int cursor = 0;
    GoogleSignInClient mGoogleSignInClient;

    Button loginwithfb, loginwithgoogle;

    CallbackManager callbackManager;
    String fbID = "", fbName = "", fbEmail = "", fbUrl = "",
            fbBirthDay = "", fbFirstName = "", fbLastName = "", fbGender = "", fbLocale = "",
            fbUpdateTime = "", fbLocationID = "", fbLocationName = "",
            fbProfilePicSmall = "", fbFriendList = "", fb_profilePicLarge, fbFullName, fbUserID, accessToken;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());

        setContentView(R.layout.activity_login);
        final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        callbackManager = CallbackManager.Factory.create();
        AppEventsLogger.activateApp(this);


        loginwithfb = (Button) findViewById(R.id.loginwithfb);
        et_email = (EditText) findViewById(R.id.et_email);
        et_pass = (EditText) findViewById(R.id.et_pass);
        newacc = (TextView) findViewById(R.id.newaccount);

        loginwithgoogle = (Button) findViewById(R.id.loginwithgoogle);
        rl_loginbtn = (RelativeLayout) findViewById(R.id.rl_loginbtn);
        //fblogin
        loginwithfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                        || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                    cursor = 1;
                    LoginManager.getInstance().logInWithReadPermissions(
                            LoginScreenActivity.this,
                            Arrays.asList("public_profile", "email"));

                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("Success", "Login1");

                        accessToken = loginResult.getAccessToken().getToken();
                        Log.d("accesstoken::::::", accessToken);

                        FacebookSdk.addLoggingBehavior(LoggingBehavior.REQUESTS);

                        // ---- Facebook user details
                        GraphRequest request = GraphRequest.newMeRequest(
                                AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                                    @SuppressLint("LongLogTag")
                                    @Override
                                    public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {

                                        try {
                                            Log.d("Fb_Details::::::::", String.valueOf(graphResponse));
                                            fbID = "" + jsonObject.getString("id");
                                            fbUserID = fbID;

                                            /////fetch all friends from facebook

                                            new GraphRequest(
                                                    AccessToken.getCurrentAccessToken(),
                                                    //  "/{user-id}/friends",

                                                    "/" + fbID + "/" + "friends",

                                                    null,
                                                    HttpMethod.GET,
                                                    new GraphRequest.Callback() {
                                                        @SuppressLint("LongLogTag")
                                                        public void onCompleted(GraphResponse response) {

                                                            Log.d("Fb_FriendList:::::::::::", String.valueOf(response));
                                                            final JSONObject jsonObject = response.getJSONObject();
                                                            Log.d("jsonObject::::::::::::::::", String.valueOf(jsonObject));

                                                            JSONObject summary = null;
                                                            try {
                                                                summary = jsonObject.getJSONObject("summary");
                                                                System.out.println("summary------>" + summary);
                                                                fbFriendList = summary.getString("total_count");
                                                                System.out.println("total_count------>" + fbFriendList);

                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }


                                                        }
                                                    }
                                            ).executeAsync();

                                            fbName = "" + jsonObject.getString("name");
                                            fbFullName = fbName;
                                            fbEmail = "" + jsonObject.getString("email");


                                            fbUrl = "" + jsonObject.getString("link");
                                            try {
                                                JSONObject jOBJ = jsonObject.getJSONObject("age_range");
                                                fbBirthDay = "" + jOBJ.getString("min");
                                            } catch (Exception e) {
                                                fbBirthDay = "Not mention";
                                            }
                                            fbFirstName = "" + jsonObject.getString("first_name");

                                            fbLastName = "" + jsonObject.getString("last_name");
                                            fbGender = "" + jsonObject.getString("gender");

                                            fbLocale = "" + jsonObject.getString("locale");
                                            fbUpdateTime = "" + jsonObject.getString("updated_time");

                                            try {
                                                JSONObject jOBJ = jsonObject.getJSONObject("location");
                                                fbLocationID = "" + jOBJ.getString("id");
                                                fbLocationName = "" + jOBJ.getString("name");
                                            } catch (Exception e) {
                                                fbLocationID = "Not mention";
                                                fbLocationName = "Not mention";
                                            }

                                            fb_profilePicLarge = "https://graph.facebook.com/" + fbID + "/picture?type=large";


                                            fbProfilePicSmall = "https://graph.facebook.com/" + fbID + "/picture?type=small";

                                            Log.i("fbID :", fbID);
                                            Log.i("fbName :", fbName);
                                            Log.i("fbEmail :", fbEmail);
                                            Log.i("fbUrl :", fbUrl);
                                            Log.i("fbBirthDay :", fbBirthDay);
                                            Log.i("fbFirstName :", fbFirstName);
                                            Log.i("fbLastName :", fbLastName);
                                            Log.i("fbGender :", fbGender);
                                            Log.i("fbLocale :", fbLocale);
                                            Log.i("fbUpdateTime :", fbUpdateTime);
                                            Log.i("fbLocationID :", fbLocationID);
                                            Log.i("fbLocationName :", fbLocationName);
                                            Log.i("fbProfilePicLarge :", fb_profilePicLarge);
                                            Log.i("fbProfilePicSmall :", fbProfilePicSmall);

                                            //     vol_Login();
                                        } catch (JSONException e) {
                                            Log.i("FB JSON EXCP : ", e.toString());
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields",
                                "id, name, email, link, birthday, age_range, first_name, " +
                                        "last_name, gender, updated_time, verified, " +
                                        "timezone, locale, location");
                        request.setParameters(parameters);
                        request.executeAsync();
                        Intent intent = new Intent(LoginScreenActivity.this, EventActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(LoginScreenActivity.this, "Login Cancel", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onError(FacebookException error) {
                        if (error instanceof FacebookAuthorizationException) {
                            if (AccessToken.getCurrentAccessToken() != null) {
                                LoginManager.getInstance().logOut();
                                loginwithfb.performClick();
                            }
                        }
                    }

                });


        //::::::::::::END


        newacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginScreenActivity.this, SignupScreenActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
            }
        });
        rl_loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                        || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                    email = et_email.getText().toString().trim();
                    password = et_pass.getText().toString().trim();

                    vol_login();
                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }


            }
        });

        //::::google login
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        loginwithgoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                        || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                    cursor = 2;
                    signIn();

                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void signIn() {

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, req_code);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            Intent intent = new Intent(LoginScreenActivity.this, EventActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
        } catch (ApiException e) {

            Log.w("ERROR:::::::::", "signInResult:failed code=" + e.getStatusCode());

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null || AccessToken.getCurrentAccessToken() != null) {
            Intent intent = new Intent(LoginScreenActivity.this, EventActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);

        }
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (cursor == 2) {
            if (requestCode == req_code) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);
            }
        } else if (cursor == 1) {


            System.out.println("### In OnActivity rESULTFaceBook ###");

            callbackManager.onActivityResult(requestCode, resultCode, data);
        }


    }

    public void vol_login() {
        if (email.isEmpty() || password.isEmpty()) {

            Toast.makeText(getApplicationContext(), "Please fill the required fields", Toast.LENGTH_SHORT).show();
        } else {

            rl_loginbtn.setClickable(false);
            String URL_login = "https://reqres.in/api/login";
            Map<String, String> map = new HashMap<String, String>();
            map.put("email", email);
            map.put("password", password);
            final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(URL_login, new JSONObject(map), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        String token = response.getString("token");
                        Log.d("token::::::::::::::::::", token);
                        Intent intent = new Intent(LoginScreenActivity.this, EventActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
                        rl_loginbtn.setClickable(true);

                    } catch (JSONException e) {
                        e.printStackTrace();

                        rl_loginbtn.setClickable(true);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    rl_loginbtn.setClickable(true);

                }
            });
            AppController.getInstance().addToRequestQueue(jsonObjectRequest);
        }
    }

}
