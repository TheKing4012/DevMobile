package com.example.tp2;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.R;
import com.example.utils.CommonHelper;

public class Exo3Activity extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tp2_exo3);

        CommonHelper.changeActionbarColor(this, getResources().getColor(R.color.ruby));
        CommonHelper.createReturnBtn((Activity) this, (ConstraintLayout) this.findViewById(R.id.tp2_exo3_menu));

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

        // Affichage de la valeur d'accélération
        TextView accelerometretv = findViewById(R.id.accelerometre);
        accelerometretv.setText(getResources().getString(R.string.text_accelerometer) +"\n\n"+ String.valueOf(acceleration));

        // Détermination de la couleur en fonction de l'accélération
        ImageView background = findViewById(R.id.imageView);
        if (acceleration < 20) {
            if (acceleration > 10) {
                background.setImageResource(R.drawable.abstract_black); // Accélération moyenne : noir
            } else {
                background.setImageResource(R.drawable.abstract_green); // Faible accélération : vert
            }
        } else {
            background.setImageResource(R.drawable.abstract_red); // Forte accélération : rouge
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Ne fait rien pour le moment
    }
}