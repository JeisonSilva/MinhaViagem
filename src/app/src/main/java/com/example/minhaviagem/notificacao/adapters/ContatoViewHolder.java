package com.example.minhaviagem.notificacao.adapters;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minhaviagem.R;

public class ContatoViewHolder extends RecyclerView.ViewHolder {
    public final AppCompatTextView tv_nome;
    public final AppCompatTextView tv_telefone;

    public ContatoViewHolder(@NonNull View itemView) {
        super(itemView);

        tv_nome = itemView.findViewById(R.id.tv_nome_contato);
        tv_telefone = itemView.findViewById(R.id.tv_telefone_contato);
    }
}
