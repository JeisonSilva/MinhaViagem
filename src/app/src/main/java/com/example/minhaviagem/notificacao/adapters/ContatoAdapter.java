package com.example.minhaviagem.notificacao.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minhaviagem.R;

import java.util.List;

public class ContatoAdapter extends RecyclerView.Adapter<ContatoViewHolder> {
    List<Contato> contatos;

    public ContatoAdapter(List<Contato> contatos) {
        this.contatos = contatos;
    }

    @NonNull
    @Override
    public ContatoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contato_card, parent, false);
        return new ContatoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContatoViewHolder holder, int position) {
        holder.tv_nome.setText(contatos.get(position).getNome());
        holder.tv_telefone.setText(contatos.get(position).getTelefone());
    }

    @Override
    public int getItemCount() {
        return contatos.size();
    }
}
