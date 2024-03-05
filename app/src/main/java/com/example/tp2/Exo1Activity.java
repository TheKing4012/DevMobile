package com.example.tp2;


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

public class Exo1Activity extends Activity {
    private SensorManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tp2_exo1);
        CommonHelper.createReturnBtn((Activity) this, (LinearLayout) this.findViewById(R.id.tp2_exo1_menu));

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        ));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setId(R.id.tp2_exo1_menu);

        sm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = sm.getSensorList(Sensor.TYPE_ALL);

        StringBuilder sensorDesc = new StringBuilder();
        for (Sensor sensor : sensors) {
            sensorDesc.delete(0,sensorDesc.length());
            sensorDesc.append(getResources().getString(R.string.text_name)).append(" : ").append(sensor.getName()).append("\r\n")
            .append(getResources().getString(R.string.text_type)).append(" : ").append(sensor.getType()).append("\r\n")
            .append(getResources().getString(R.string.text_version)).append(" : ").append(sensor.getVersion()).append("\r\n")
            .append(getResources().getString(R.string.text_resolution)).append(" : ").append(sensor.getResolution()).append("\r\n")
            .append(getResources().getString(R.string.text_power)).append(" : ").append(sensor.getPower()).append("\r\n")
            .append(getResources().getString(R.string.text_vendor)).append(" : ").append(sensor.getVendor()).append("\r\n")
            .append(getResources().getString(R.string.text_range)).append(" : ").append(sensor.getMaximumRange()).append("\r\n")
            .append(getResources().getString(R.string.text_delay)).append(" : ").append(sensor.getMinDelay()).append("\r\n");

            TextView sensorTV = new EditText(this);
            sensorTV.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            sensorTV.setText(sensorDesc);

            linearLayout.addView(sensorTV);
            setContentView(linearLayout);
        }
    }
}