package com.example.minhaviagem.login.models;

public class Login {
    private String email;
    private String nome;

    public Login(String email, String nome) {
        this.email = email;
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }
}
