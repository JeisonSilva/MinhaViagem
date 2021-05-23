package com.example.minhaviagem.minhalocalizacao;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.minhaviagem.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MinhaLocalizacaoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MinhaLocalizacaoFragment extends Fragment {

    public MinhaLocalizacaoFragment() {
        // Required empty public constructor
    }
    public static MinhaLocalizacaoFragment newInstance(String param1, String param2) {
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_minha_localizacao, container, false);
    }
}