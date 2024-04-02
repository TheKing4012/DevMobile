package com.example.tp3;


import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.tp1.Exo3Activity;
import com.example.tp1.Exo3ActivityOnlyJava;
import com.example.tp1.Exo5Activity;
import com.example.tp1.Exo6Activity;
import com.example.tp1.Exo7Activity;
import com.example.tp1.HelloWorldActivity;
import com.example.R;
import com.example.utils.CommonHelper;

public class TP3Activity extends Activity {

    Button btn1, btn2, btn3, btn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tp3);

        btn1 = (Button) findViewById(R.id.button_exo1);
        btn2 = (Button) findViewById(R.id.button_exo2);
        btn3 = (Button) findViewById(R.id.button_exo3);
        btn4 = (Button) findViewById(R.id.button_exo4);

        btn1.setOnClickListener(view -> {
            CommonHelper.changeActivity(this, new Exo1Activity());
        });

        btn2.setOnClickListener(view -> {
            CommonHelper.changeActivity(this, new Exo2Activity());
        });

        btn3.setOnClickListener(view -> {
            CommonHelper.changeActivity(this, new Exo3Activity());
        });

        btn4.setOnClickListener(view -> {
            CommonHelper.changeActivity(this, new Exo4Activity());
        });
    }
}