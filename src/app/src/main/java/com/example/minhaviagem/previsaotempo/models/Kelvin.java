package com.example.minhaviagem.previsaotempo.models;

public class Kelvin extends Temperatura{
    private final Celsius celsius;

    public Kelvin(Celsius celsius) {
        super();

        this.celsius = celsius;
    }

    @Override
    public float CalcularTemperatura() {
        float result = (this.celsius.CalcularTemperatura() + 273.15f);
        return result;
    }

    @Override
    public boolean EstaQuente() {
        return this.celsius.CalcularTemperatura() > 288.15f;
    }
}
