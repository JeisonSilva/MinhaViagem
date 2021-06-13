package com.example.minhaviagem.minhalocalizacao.services;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.model.Place;

public interface CoordenadaCallback {
    void PosicionarDispositivo(LatLng latLng);

    void PosicionarDestino(Place place);
}
