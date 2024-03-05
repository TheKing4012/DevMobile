package com.example.tp2;


import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.R;
import com.example.utils.CommonHelper;

import java.util.List;

public class Exo2Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tp2_exo2);

        CommonHelper.changeActionbarColor(this, getResources().getColor(R.color.ruby));
        CommonHelper.createReturnBtn((Activity) this, (LinearLayout) this.findViewById(R.id.tp2_exo2_menu));

        TextView textView = findViewById(R.id.textView);

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);

        StringBuilder unavailableSensors = new StringBuilder();

        if (!sensorList.contains(sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER))) {
            unavailableSensors.append(getResources().getString(R.string.text_accelerometer)+"\n");
        }
        if (!sensorList.contains(sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE))) {
            unavailableSensors.append(getResources().getString(R.string.text_gyroscope)+"\n");
        }
        if (!sensorList.contains(sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT))) {
            unavailableSensors.append(getResources().getString(R.string.text_light_sensor)+"\n");
        }
        if (!sensorList.contains(sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY))) {
            unavailableSensors.append(getResources().getString(R.string.text_proximity_sensor)+"\n");
        }

        // Affichage du message d'indisponibilité le cas échéant
        if (unavailableSensors.length() > 0) {
            textView.setText("Les fonctionnalités suivantes ne sont pas disponibles sur votre appareil:\n\n" + unavailableSensors.toString());
        } else {
            textView.setText("Toutes les fonctionnalités sont disponibles sur votre appareil.");
        }
    }
}