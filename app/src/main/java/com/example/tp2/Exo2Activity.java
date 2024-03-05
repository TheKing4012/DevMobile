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
        CommonHelper.createReturnBtn((Activity) this, (LinearLayout) this.findViewById(R.id.tp1_exo3_menu));

        TextView textView = findViewById(R.id.textView);

        // Obtention du gestionnaire de capteurs
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // Obtention de la liste des capteurs disponibles sur l'appareil
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);

        StringBuilder unavailableSensors = new StringBuilder();

        // Vérification des capteurs requis et construction du message d'indisponibilité
        if (!sensorList.contains(sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER))) {
            unavailableSensors.append("Accéléromètre\n");
        }
        if (!sensorList.contains(sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE))) {
            unavailableSensors.append("Gyroscope\n");
        }
        if (!sensorList.contains(sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT))) {
            unavailableSensors.append("Capteur de lumière\n");
        }
        if (!sensorList.contains(sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY))) {
            unavailableSensors.append("Capteur de proximité\n");
        }

        // Affichage du message d'indisponibilité le cas échéant
        if (unavailableSensors.length() > 0) {
            textView.setText("Les fonctionnalités suivantes ne sont pas disponibles sur votre appareil:\n\n" + unavailableSensors.toString());
        } else {
            textView.setText("Toutes les fonctionnalités sont disponibles sur votre appareil.");
        }
    }
}