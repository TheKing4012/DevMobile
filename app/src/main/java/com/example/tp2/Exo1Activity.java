package com.example.tp2;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.R;
import com.example.utils.CommonHelper;

import java.util.List;
import java.util.Objects;

public class Exo1Activity extends Activity {
    private SensorManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tp2_exo1);
        LinearLayout linearLayout = findViewById(R.id.tp2_exo1_menu);

        sm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = sm.getSensorList(Sensor.TYPE_ALL);

        String sensorDesc = "";
        for (Sensor sensor : sensors) {
            sensorDesc = "";
            sensorDesc = sensorDesc.concat(getResources().getString(R.string.text_name)).concat(" : ").concat(sensor.getName()).concat("\r\n")
            .concat(getResources().getString(R.string.text_type)).concat(" : ").concat(String.valueOf(sensor.getType())).concat("\r\n")
            .concat(getResources().getString(R.string.text_version)).concat(" : ").concat(String.valueOf(sensor.getVersion())).concat("\r\n")
            .concat(getResources().getString(R.string.text_resolution)).concat(" : ").concat(String.valueOf(sensor.getResolution())).concat("\r\n")
            .concat(getResources().getString(R.string.text_power)).concat(" : ").concat(String.valueOf(sensor.getPower())).concat("\r\n")
            .concat(getResources().getString(R.string.text_vendor)).concat(" : ").concat(sensor.getVendor()).concat("\r\n")
            .concat(getResources().getString(R.string.text_range)).concat(" : ").concat(String.valueOf(sensor.getMaximumRange())).concat("\r\n")
            .concat(getResources().getString(R.string.text_delay)).concat(" : ").concat(String.valueOf(sensor.getMinDelay())).concat("\r\n");

            TextView textview = new TextView(this);
            textview.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            //System.out.println(sensorDesc);
            textview.setText(sensorDesc);
            linearLayout.addView(textview);
        }

        CommonHelper.createReturnBtn((Activity) this, (LinearLayout) this.findViewById(R.id.tp2_exo1_menu));
    }
}