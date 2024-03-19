package com.example;


import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.R;
import com.example.tp1.TP1Activity;
import com.example.tp2.TP2Activity;
import com.example.tp3.TP3Activity;
import com.example.utils.CommonHelper;

public class MainActivity extends Activity {

    Button btn1, btn2, btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CommonHelper.changeActionbarColor(this, getResources().getColor(R.color.ruby));

        btn1 = (Button) findViewById(R.id.button_tp1);
        btn2 = (Button) findViewById(R.id.button_tp2);
        btn3 = (Button) findViewById(R.id.button_tp3);


        btn1.setOnClickListener(view -> {
            CommonHelper.changeActivity(this, new TP1Activity());
        });

        btn2.setOnClickListener(view -> {
            CommonHelper.changeActivity(this, new TP2Activity());
        });

        btn3.setOnClickListener(view -> {
            CommonHelper.changeActivity(this, new TP3Activity());
        });
    }
}