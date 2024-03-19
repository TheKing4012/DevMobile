package com.example.tp2;


import static java.lang.Math.abs;
import static java.lang.Math.max;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.R;
import com.example.utils.CommonHelper;

public class Exo6Activity extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private DisplayMetrics displayMetrics = new DisplayMetrics();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tp2_exo6);

        CommonHelper.changeActionbarColor(this, getResources().getColor(R.color.ruby));
        CommonHelper.createReturnBtn((Activity) this, (ConstraintLayout) this.findViewById(R.id.tp2_exo6_menu));

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        ImageView imageView = findViewById(R.id.imageView);
        TextView distanceText = findViewById(R.id.distance_text);
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();;
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            // Récupérer la valeur de proximité
            float proximityValue = event.values[0];

            // Affichage de la TextView
            if (proximityValue < 5) {
                distanceText.setText(getResources().getString(R.string.distance_close));
                layoutParams.width = screenWidth + 1500;
                layoutParams.height = screenHeight + (int)(1500.0 * 1920.0/1080.0);
                distanceText.setTextSize(100);
            } else {
                distanceText.setText(getResources().getString(R.string.distance_far));
                layoutParams.width = screenWidth;
                layoutParams.height = screenHeight;
                distanceText.setTextSize(50);
            }
            imageView.setLayoutParams(layoutParams);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Ne fait rien pour le moment
    }
}