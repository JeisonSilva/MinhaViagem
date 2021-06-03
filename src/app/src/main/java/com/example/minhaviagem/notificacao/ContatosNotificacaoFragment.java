package com.example.minhaviagem.notificacao;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.minhaviagem.R;
import com.example.minhaviagem.minhalocalizacao.services.CoordenadaAtual;
import com.example.minhaviagem.minhalocalizacao.services.CoordenadaDestino;
import com.example.minhaviagem.minhalocalizacao.services.GeofenceHelper;
import com.example.minhaviagem.notificacao.adapters.Contato;
import com.example.minhaviagem.notificacao.adapters.ContatoAdapter;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

public class ContatosNotificacaoFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int READ_CONTACTS = 1;
    private List<Contato> contatos;
    private RecyclerView rv_contatos;

    public ContatosNotificacaoFragment() {
        // Required empty public constructor
    }

    public static ContatosNotificacaoFragment newInstance() {
        ContatosNotificacaoFragment fragment = new ContatosNotificacaoFragment();
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
            contatos = new ArrayList<>();
            LoaderManager.getInstance(this).initLoader(0, null, this);
        } else if (!checkPermissions()) {
            //Não implementado, apenas necessário se targetSdkVersion >= 23
            requestPermission(Manifest.permission.READ_CONTACTS, READ_CONTACTS);
        }


    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_CONTACTS);
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
        View view = inflater.inflate(R.layout.fragment_contatos_notificacao, container, false);
        rv_contatos = view.findViewById(R.id.rv_contatos);
        return view;
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        Uri contatos = ContactsContract.Contacts.CONTENT_URI;
        return new CursorLoader(
                getActivity(),
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        ContentResolver contentResolver = getActivity().getContentResolver();
        String _ID = ContactsContract.CommonDataKinds.Phone._ID;
        String Phone_ContactID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        String display_name = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
        String has_phone_number = ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER;
        String number = ContactsContract.CommonDataKinds.Phone.NUMBER;


        while (data.moveToNext()){
            String contact_id = data.getString(data.getColumnIndex(_ID));
            String name = data.getString(data.getColumnIndex(display_name));
            int possuiNumero = Integer.parseInt(data.getString(data.getColumnIndex(has_phone_number)));

            if(possuiNumero > 0) {
                Cursor cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, Phone_ContactID + "=?", new String[]{contact_id}, null);
                while (cursor.moveToNext()) {
                    String numero = cursor.getString(cursor.getColumnIndex(number));
                    contatos.add(new Contato(name, numero));
                }
            }
        }

        ContatoAdapter adapter = new ContatoAdapter(contatos);
        rv_contatos.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_contatos.setAdapter(adapter);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}