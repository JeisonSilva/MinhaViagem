package com.example.minhaviagem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private static int RC_SIGN_IN = 9001;
    private GoogleSignInClient googleSignClient;
    private SignInButton btn_login_google;

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

        googleSignClient =  GoogleSignIn.getClient(this, gso);
        btn_login_google.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        googleSignClient.signOut();

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

    private boolean EstaLogada(GoogleSignInAccount account) {
        return account != null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login_google:
                SignInGoogle();
                break;
        }
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
}