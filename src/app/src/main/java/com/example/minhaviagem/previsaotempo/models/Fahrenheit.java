package com.example.minhaviagem.previsaotempo.models;

public class Fahrenheit  extends Temperatura{
    private final Celsius celsius;

    public Fahrenheit(Celsius celsius) {
        super();
        this.celsius = celsius;
    }

    @Override
    public float CalcularTemperatura() {
        float result = (this.celsius.CalcularTemperatura() * 1.8f) + 32;
        return result;
    }

    @Override
    public boolean EstaQuente() {
        return this.celsius.CalcularTemperatura() > 59f;
    }
}
