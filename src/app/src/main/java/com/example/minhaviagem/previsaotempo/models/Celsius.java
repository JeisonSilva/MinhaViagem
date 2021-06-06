package com.example.minhaviagem.previsaotempo.models;

public class Celsius extends Temperatura{
    private final float value;

    public Celsius(float value) {
        super();

        this.value = value;
    }

    @Override
    public float CalcularTemperatura() {
        return this.value;
    }

    @Override
    public boolean EstaQuente() {
        return this.value > 15f;
    }
}
