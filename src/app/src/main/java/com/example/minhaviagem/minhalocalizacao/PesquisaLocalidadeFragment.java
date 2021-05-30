package com.example.minhaviagem.minhalocalizacao;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.minhaviagem.R;
import com.example.minhaviagem.minhalocalizacao.services.CoordenadaDestino;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Arrays;

public class PesquisaLocalidadeFragment extends BottomSheetDialogFragment {

    private CoordenadaDestino coordenadaDestino;
    private AutocompleteSupportFragment autocomplete_fragment;
    private AutocompleteSupportFragment autocompleteFragment;

    public PesquisaLocalidadeFragment(CoordenadaDestino coordenadaDestino) {
        this.coordenadaDestino = coordenadaDestino;
    }

    public static PesquisaLocalidadeFragment newInstance(CoordenadaDestino coordenadaDestino) {

        Bundle args = new Bundle();

        PesquisaLocalidadeFragment fragment = new PesquisaLocalidadeFragment(coordenadaDestino);
        fragment.setArguments(args);
        return fragment;
    }

    public PesquisaLocalidadeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pesquisa_localidade_fragment, container, false);
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG));
        autocompleteFragment.setHint("Qual seu destino?");

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                coordenadaDestino.MarcarDestino(place);
            }

            @Override
            public void onError(@NonNull Status status)
            {

            }
        });
        return view;
    }
}
