package com.example.minhaviagem.notificacao.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minhaviagem.R;

import java.util.List;

public class ContatoAdapter extends RecyclerView.Adapter<ContatoViewHolder> implements View.OnClickListener {
    private final Context context;
    List<Contato> contatos;

    public ContatoAdapter(List<Contato> contatos, Context context) {
        this.contatos = contatos;
        this.context = context;
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
        holder.card_contato.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return contatos.size();
    }

    @Override
    public void onClick(View v) {
        AppCompatCheckBox chk_contato = v.findViewById(R.id.chk_contato);

        if(!chk_contato.isChecked())
            marcarContatoParaSerNotificado(chk_contato);
        else
            desmarcarContatoParaNaoNotificar(chk_contato);
    }

    private void marcarContatoParaSerNotificado(AppCompatCheckBox chk_contato) {
        chk_contato.setChecked(true);
        Toast.makeText(context, R.string.mensagem_contato_notificado, Toast.LENGTH_LONG).show();
    }

    private void desmarcarContatoParaNaoNotificar(AppCompatCheckBox chk_contato) {
        chk_contato.setChecked(false);
        Toast.makeText(context, R.string.mensagem_contato_terirada_notificacao, Toast.LENGTH_LONG).show();
    }
}
