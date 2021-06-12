package com.example.minhaviagem;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SpashScreenActivity extends AppCompatActivity implements Runnable{

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
            navegarParaLogin();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    private void navegarParaLogin(){
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }
}
