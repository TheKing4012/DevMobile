package com.example.tp2;


import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.R;
import com.example.utils.CommonHelper;

import java.util.List;

public class Exo1Activity extends Activity {
    private SensorManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tp2_exo1);

        CommonHelper.changeActionbarColor(this, getResources().getColor(R.color.ruby));
        CommonHelper.createReturnBtn((Activity) this, (LinearLayout) this.findViewById(R.id.tp2_exo1_menu));

        sm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = sm.getSensorList(Sensor.TYPE_ALL);

        StringBuffer sensorDesc = new StringBuffer();
        for (Sensor sensor : sensors) {
            sensorDesc.append(getResources().getString(R.string.text_name)).append(" : ").append(sensor.getName()).append("\r\n")
            .append(getResources().getString(R.string.text_type)).append(" : ").append(sensor.getType()).append("\r\n")
            .append(getResources().getString(R.string.text_version)).append(" : ").append(sensor.getVersion()).append("\r\n")
            .append(getResources().getString(R.string.text_resolution)).append(" : ").append(sensor.getResolution()).append("\r\n")
            .append(getResources().getString(R.string.text_power)).append(" : ").append(sensor.getPower()).append("\r\n")
            .append(getResources().getString(R.string.text_vendor)).append(" : ").append(sensor.getVendor()).append("\r\n")
            .append(getResources().getString(R.string.text_range)).append(" : ").append(sensor.getMaximumRange()).append("\r\n")
            .append(getResources().getString(R.string.text_delay)).append(" : ").append(sensor.getMinDelay()).append("\r\n");
        }
        Toast.makeText(this, sensorDesc.toString(), Toast.LENGTH_LONG).show();
    }
}