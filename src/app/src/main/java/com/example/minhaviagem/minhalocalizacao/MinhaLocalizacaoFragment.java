package com.example.minhaviagem.minhalocalizacao;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.minhaviagem.R;
import com.example.minhaviagem.minhalocalizacao.services.CoordenadaAtual;
import com.example.minhaviagem.minhalocalizacao.services.CoordenadaCallback;
import com.example.minhaviagem.minhalocalizacao.services.CoordenadaDestino;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class MinhaLocalizacaoFragment extends Fragment implements OnMapReadyCallback, CoordenadaCallback {

    private static final int ACCESS_FINE_LOCATION = 1;
    private static int AUTOCOMPLETE_REQUEST_CODE = 1;

    private SupportMapFragment mapFragment;
    private FusedLocationProviderClient locationClient;
    private LatLng posicaoAtual;
    private FloatingActionButton bt_pesquisa_destino;
    private CoordenadaAtual coordenadaAtual;
    private CoordenadaDestino coordenadaDestino;
    private LatLng posicaoDestino;

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
            this.coordenadaAtual = new CoordenadaAtual(this);
            this.coordenadaDestino = new CoordenadaDestino(this);

            this.coordenadaAtual.SolicitarCoordenadasDispositivo(getContext());
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

        this.bt_pesquisa_destino = view.findViewById(R.id.bt_pesquisa_destino);
        this.mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);

        this.bt_pesquisa_destino.setOnClickListener(pesquisarDestinoListener);
        return view;
    }

    View.OnClickListener pesquisarDestinoListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            // Set the fields to specify which types of place data to
            // return after the user has made a selection.
            List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);

            // Start the autocomplete intent.
            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                    .build(getContext());
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                coordenadaDestino.MarcarDestino(place);
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(MinhaLocalizacaoFragment.class.getName(), status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.clear();

        googleMap.addMarker(new MarkerOptions()
                .position(posicaoAtual)
                .title("Posição atual"));

        if(posicaoDestino != null){
            googleMap.addMarker(new MarkerOptions()
                    .position(posicaoDestino)
                    .title("Posição destino"));

            googleMap.addPolyline(new PolylineOptions().add(posicaoAtual).add(posicaoDestino).color(Color.RED));
        }

        googleMap.setMinZoomPreference(16);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(this.posicaoAtual));
    }

    @Override
    public void PosicionarDispositivo(LatLng latLng) {
        this.posicaoAtual = latLng;
        mapFragment.getMapAsync(this);
    }

    @Override
    public void PosicionarDestino(Place place) {
        this.posicaoDestino = place.getLatLng();
        mapFragment.getMapAsync(this);
    }
}