package com.example.minhaviagem.minhalocalizacao.services;

import com.example.minhaviagem.minhalocalizacao.MinhaLocalizacaoFragment;
import com.google.android.libraries.places.api.model.Place;

public class CoordenadaDestino {
    private final CoordenadaCallback coordenadaCallback;

    public CoordenadaDestino(CoordenadaCallback coordenadaCallback) {
        this.coordenadaCallback = coordenadaCallback;

    }

    public void MarcarDestino(Place place) {
        coordenadaCallback.PosicionarDestino(place);
    }
}
