package com.example.minhaviagem.previsaotempo;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.minhaviagem.R;
import com.example.minhaviagem.previsaotempo.models.Celsius;
import com.example.minhaviagem.previsaotempo.models.Fahrenheit;
import com.example.minhaviagem.previsaotempo.models.Kelvin;
import com.example.minhaviagem.previsaotempo.models.Temperatura;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PrecisaoTempoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrecisaoTempoFragment extends Fragment implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor temperatura;
    private AppCompatTextView tv_celsius;
    private AppCompatTextView tv_Kelvin;
    private AppCompatTextView tv_fahrenheit;
    private AppCompatImageView img_temperatura;
    private AppCompatImageView img_falha;
    private AppCompatTextView tv_mensagem;
    private boolean isTemperatura;

    public PrecisaoTempoFragment() {
        // Required empty public constructor
    }

    public PrecisaoTempoFragment(SensorManager sensorManager) {
        this.sensorManager = sensorManager;
    }

    public static PrecisaoTempoFragment newInstance(SensorManager sensorManager) {
        PrecisaoTempoFragment fragment = new PrecisaoTempoFragment(sensorManager);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

        this.temperatura =  sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        if(this.temperatura == null)
            isTemperatura = false;
        else
            isTemperatura = true;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(!isTemperatura){
            tv_mensagem.setVisibility(View.VISIBLE);
            img_falha.setVisibility(View.VISIBLE);
        }else{
            sensorManager.registerListener(this, temperatura, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if(isTemperatura)
            sensorManager.unregisterListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_precisao_tempo, container, false);
        tv_celsius = view.findViewById(R.id.tv_temperatura_celsius);
        tv_Kelvin = view.findViewById(R.id.tv_temperatura_Kelvin);
        tv_fahrenheit = view.findViewById(R.id.tv_temperatura_Fahrenheit);
        tv_mensagem = view.findViewById(R.id.tv_mensagem_sem_sensor);
        img_temperatura = view.findViewById(R.id.img_temperatura);
        img_falha = view.findViewById(R.id.img_falha);

        return view;
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        Celsius celsius = new Celsius(event.values[0]);
        Fahrenheit fahrenheit = new Fahrenheit(celsius);
        Kelvin kelvin = new Kelvin(celsius);

        exibirTemperatura(celsius);
        exibirTemperatura(fahrenheit);
        exibirTemperatura(kelvin);
        exibirImagemReferenteATemperatura(celsius);
    }

    private void exibirImagemReferenteATemperatura(Temperatura temperatura) {
        if(temperatura.EstaQuente())
            img_temperatura.setBackgroundResource(R.mipmap.verao);
        else
            img_temperatura.setBackgroundResource(R.mipmap.inverno);

    }

    private void exibirTemperatura(Celsius celsius) {
        tv_celsius.setText(String.format("%.1f Cº", celsius.CalcularTemperatura()));
    }

    private void exibirTemperatura(Fahrenheit fahrenheit) {
        tv_fahrenheit.setText(String.format("%.1f Fº", fahrenheit.CalcularTemperatura()));
    }

    private void exibirTemperatura(Kelvin kelvin) {
        tv_Kelvin.setText(String.format("%.2f Kº", kelvin.CalcularTemperatura()));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}