package com.example.minhaviagem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.minhaviagem.R;
import com.example.minhaviagem.minhalocalizacao.MinhaLocalizacaoFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCenter.start(getApplication(), BuildConfig.APPCANTER_API_KEY,
                Analytics.class, Crashes.class);




    }

    @Override
    protected void onStart() {
        super.onStart();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, MinhaLocalizacaoFragment.newInstance())
                .commit();
    }
}