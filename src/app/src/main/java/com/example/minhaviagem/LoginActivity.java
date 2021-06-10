package com.example.minhaviagem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, FacebookCallback<LoginResult>{

    private static int RC_SIGN_IN = 9001;
    private static int RC_SIGN_FACEBOOK = 64206;
    private GoogleSignInClient googleSignClient;
    private SignInButton btn_login_google;
    private CallbackManager callbackManager;
    private LoginButton btn_login_facebook;
    private ProfileTracker profileTracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login_google = findViewById(R.id.btn_login_google);

        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();

        callbackManager = CallbackManager.Factory.create();
        btn_login_facebook = findViewById(R.id.btn_login_facebook);
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"));
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                if(currentProfile != null){
                    AcessarPainelViagem(currentProfile);
                }
            }
        };

        btn_login_facebook.registerCallback(callbackManager,this);
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                    }
                });

        googleSignClient =  GoogleSignIn.getClient(this, gso);
        btn_login_google.setOnClickListener(this);



    }

    @Override
    protected void onStart() {
        super.onStart();

        googleSignClient.signOut();
        if(!profileTracker.isTracking())
            profileTracker.startTracking();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void AcessarPainelViagem(GoogleSignInAccount account) {
        String nome = account.getGivenName();
        String email = account.getEmail();

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("email", email);
        intent.putExtra("nome", nome);
        startActivity(intent);
        finish();
    }

    private void AcessarPainelViagem(Profile profile) {
        String nome = profile.getName();
        String email = "";

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("email", email);
        intent.putExtra("nome", nome);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login_google:
                SignInGoogle();
                break;
            case R.id.btn_login_facebook:
                SignInFacebook();
        }
    }

    private void SignInFacebook() {

    }

    private void SignInGoogle() {
        Intent intent = googleSignClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handlerSignInResult(task);
        }else{
        }

        if(requestCode == RC_SIGN_FACEBOOK) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handlerSignInResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            AcessarPainelViagem(account);
        }catch (ApiException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        Log.e("LoginResult", loginResult.getAccessToken().getToken());
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(FacebookException error) {
        Log.e("FacebookException", error.getMessage());
    }
}