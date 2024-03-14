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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.R;
import com.example.utils.CommonHelper;

public class Exo5Activity extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private CameraManager cameraManager;
    private Sensor accelerometer;
    private Boolean isFlashLightOn = Boolean.FALSE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tp2_exo5);

        CommonHelper.changeActionbarColor(this, getResources().getColor(R.color.ruby));
        CommonHelper.createReturnBtn((Activity) this, (ConstraintLayout) this.findViewById(R.id.tp2_exo5_menu));

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
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

        // Détermination du texte en fonction de l'accélérationFlashLightState
        ImageView light = findViewById(R.id.light);
        TextView lighttv = findViewById(R.id.light_text);
        if (acceleration > 15) {
            if (cameraManager != null) {
                try {
                    String cameraId = cameraManager.getCameraIdList()[0];
                    System.out.println(this.getFlashLightState());
                    if (this.getFlashLightState()) {
                        setFlashLightState(Boolean.FALSE);
                        cameraManager.setTorchMode(cameraId, false);
                        light.setImageResource(R.drawable.lightoff);
                        lighttv.setText(getResources().getString(R.string.light_text_off));
                    } else {
                        setFlashLightState(Boolean.TRUE);
                        cameraManager.setTorchMode(cameraId, true);
                        light.setImageResource(R.drawable.lighton);
                        lighttv.setText(getResources().getString(R.string.light_text_on));
                    }
                } catch (CameraAccessException e) {
                    System.out.println("exception");
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, "FlashLight is unavailable", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Ne fait rien pour le moment
    }

    private void setFlashLightState(Boolean value){
        this.isFlashLightOn = value;
    }

    private Boolean getFlashLightState(){
        return this.isFlashLightOn;
    }
}