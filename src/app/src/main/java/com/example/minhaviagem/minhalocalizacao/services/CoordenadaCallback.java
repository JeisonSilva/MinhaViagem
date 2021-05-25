package com.example.minhaviagem.minhalocalizacao.services;

import com.google.android.gms.maps.model.LatLng;

public interface CoordenadaCallback {
    void PosicionarDispositivo(LatLng latLng);
}
