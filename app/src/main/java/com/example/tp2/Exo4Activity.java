package com.example.tp2;


import static java.lang.Math.abs;
import static java.lang.Math.max;

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

public class Exo4Activity extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tp2_exo4);

        CommonHelper.changeActionbarColor(this, getResources().getColor(R.color.ruby));
        CommonHelper.createReturnBtn((Activity) this, (ConstraintLayout) this.findViewById(R.id.tp2_exo4_menu));

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
        TextView directiontv = findViewById(R.id.directiontv);
        float x = event.values[0];
        float y = event.values[1];
        double angle = Math.atan2(y, x) * (180 / Math.PI);

        // Calcul de l'accélération totale
        double acceleration = Math.sqrt(x * x + y * y);

        // Récupérer une référence à l'ImageView de la flèche
        ImageView arrowImageView = findViewById(R.id.arrow);
        arrowImageView.setRotation((float) angle);

        // Détermination du texte en fonction de l'accélération
        String direction_affichage = getResources().getString(R.string.direction);
        if (acceleration > 1) {
            if(abs(x) >= abs(y)){ //x est le plus grand en valeur absolue
                if(x > 0){
                    direction_affichage = getResources().getString(R.string.left);
                } else {
                    direction_affichage = getResources().getString(R.string.right);
                }
            } else { //y est le plus grand en valeur absolue
                if(y > 0){
                    direction_affichage = getResources().getString(R.string.up);
                } else {
                    direction_affichage = getResources().getString(R.string.down);
                }
            }
            directiontv.setText(direction_affichage);
        } else {
            directiontv.setText(getResources().getString(R.string.direction));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Ne fait rien pour le moment
    }
}