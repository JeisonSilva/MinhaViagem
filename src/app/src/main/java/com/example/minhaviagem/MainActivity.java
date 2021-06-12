package com.example.minhaviagem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.minhaviagem.login.models.Login;
import com.example.minhaviagem.minhalocalizacao.MinhaLocalizacaoFragment;
import com.example.minhaviagem.notificacao.ContatosNotificacaoFragment;
import com.example.minhaviagem.previsaotempo.PrecisaoTempoFragment;
import com.google.android.libraries.places.api.Places;
import com.google.android.material.navigation.NavigationView;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    private Login login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigarion);

        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer);
        toggle.syncState();


        exibirPerfil(navigationView);

        AppCenter.start(getApplication(), BuildConfig.APPCANTER_API_KEY,
                Analytics.class, Crashes.class);

        if(!Places.isInitialized())
            Places.initialize(getBaseContext(), BuildConfig.MAP_API_KEY);




    }

    private void exibirPerfil(NavigationView navigationView) {
        String email = getIntent().getStringExtra("email");
        String nome = getIntent().getStringExtra("nome");
        login = new Login(email, nome);

        AppCompatTextView tv_email = navigationView.getHeaderView(0).findViewById(R.id.tv_email);
        AppCompatTextView tv_nome = navigationView.getHeaderView(0).findViewById(R.id.tv_nome);

        tv_email.setText(login.getEmail());
        tv_nome.setText(login.getNome());

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_percurso:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, MinhaLocalizacaoFragment.newInstance(login))
                        .commit();
                break;
            case R.id.nav_notificacao:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, ContatosNotificacaoFragment.newInstance())
                        .commit();
                break;
            case R.id.nav_precisao:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, PrecisaoTempoFragment.newInstance((SensorManager) getSystemService(SENSOR_SERVICE)))
                        .commit();
        }

        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);

        return true;

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

}