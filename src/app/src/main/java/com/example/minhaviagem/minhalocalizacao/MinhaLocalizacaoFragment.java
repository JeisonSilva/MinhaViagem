package com.example.minhaviagem.minhalocalizacao;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.minhaviagem.R;
import com.example.minhaviagem.minhalocalizacao.services.CoordenadaAtual;
import com.example.minhaviagem.minhalocalizacao.services.CoordenadaCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Arrays;

public class MinhaLocalizacaoFragment extends Fragment implements OnMapReadyCallback, CoordenadaCallback {

    private static final int ACCESS_FINE_LOCATION = 1;
    private SupportMapFragment mapFragment;
    private FusedLocationProviderClient locationClient;
    private LatLng posicaoAtual;

    public MinhaLocalizacaoFragment() {
    }

    public static MinhaLocalizacaoFragment newInstance() {
        MinhaLocalizacaoFragment fragment = new MinhaLocalizacaoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        if (checkPermissions()) {
            CoordenadaAtual coordenadaAtual = new CoordenadaAtual(this);
            coordenadaAtual.SolicitarCoordenadasDispositivo(getContext());
        } else if (!checkPermissions()) {
            //Não implementado, apenas necessário se targetSdkVersion >= 23
            requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, ACCESS_FINE_LOCATION);

        }



    }



    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission(String permissionName, int permissionRequestCode) {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{permissionName}, permissionRequestCode);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_minha_localizacao, container, false);
        this.mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.clear();

        googleMap.addMarker(new MarkerOptions()
                .position(this.posicaoAtual)
                .title("Marker"));

        googleMap.setMinZoomPreference(16);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(this.posicaoAtual));
    }

    @Override
    public void PosicionarDispositivo(LatLng latLng) {
        this.posicaoAtual = latLng;
        mapFragment.getMapAsync(this);
    }
}