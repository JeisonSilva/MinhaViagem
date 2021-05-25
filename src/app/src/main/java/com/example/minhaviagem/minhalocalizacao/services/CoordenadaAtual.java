package com.example.minhaviagem.minhalocalizacao.services;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

public class CoordenadaAtual extends LocationCallback {

    private final CoordenadaCallback coordenadaCallback;
    private FusedLocationProviderClient locationClient;

    public CoordenadaAtual(CoordenadaCallback coordenadaCallback) {
        this.coordenadaCallback = coordenadaCallback;
    }

    @SuppressLint("MissingPermission")
    public void SolicitarCoordenadasDispositivo(Context context) {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setSmallestDisplacement(10);
        this.locationClient = LocationServices.getFusedLocationProviderClient(context);
        this.locationClient.requestLocationUpdates(locationRequest, this, null);
    }

    @Override
    public void onLocationResult(@NonNull LocationResult locationResult) {
        super.onLocationResult(locationResult);

        LatLng latLng = new LatLng(locationResult.getLastLocation().getLatitude(), locationResult.getLastLocation().getLongitude());
        this.coordenadaCallback.PosicionarDispositivo(latLng);
    }
}
