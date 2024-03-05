package com.example.tp2;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.R;
import com.example.utils.CommonHelper;

import java.util.List;

public class Exo3Activity extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tp2_exo2);

        CommonHelper.changeActionbarColor(this, getResources().getColor(R.color.ruby));
        CommonHelper.createReturnBtn((Activity) this, (LinearLayout) this.findViewById(R.id.tp2_exo2_menu));

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        // Calcul de l'accélération totale
        double acceleration = Math.sqrt(x * x + y * y + z * z);

        // Détermination de la couleur en fonction de l'accélération
        int color;
        System.out.println("acceleration: "+acceleration);
        if (acceleration < 50) {
            if (acceleration < 10) {
                color = Color.BLACK; // Accélération moyenne : noir
            } else {
                color = Color.GREEN; // Faible accélération : vert
            }
        } else {
            color = Color.RED; // Forte accélération : rouge
        }

        // Changer la couleur de l'arrière-plan
        getWindow().getDecorView().setBackgroundColor(color);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Ne fait rien pour le moment
    }
}