package com.example.tp1;


import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.utils.CommonHelper;

public class TP1Activity extends Activity {

    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tp1);

        btn1 = (Button) findViewById(R.id.button_exo1);
        btn2 = (Button) findViewById(R.id.button_exo2);
        btn3 = (Button) findViewById(R.id.button_exo3);
        btn4 = (Button) findViewById(R.id.button_exo4);
        btn5 = (Button) findViewById(R.id.button_exo5);
        btn6 = (Button) findViewById(R.id.button_exo6);
        btn7 = (Button) findViewById(R.id.button_exo7);
        btn8 = (Button) findViewById(R.id.button_exo8);


        btn1.setOnClickListener(view -> {
            CommonHelper.changeActivity(this, new HelloWorldActivity());
        });

        btn2.setOnClickListener(view -> {
            CommonHelper.changeActivity(this, new Exo3Activity());
        });

        btn3.setOnClickListener(view -> {
            CommonHelper.changeActivity(this, new Exo3ActivityOnlyJava());
        });

        btn4.setOnClickListener(view -> {
            CommonHelper.changeActivity(this, new Exo5Activity());
        });

        btn5.setOnClickListener(view -> {
            CommonHelper.changeActivity(this, new Exo6Activity());
        });

        btn6.setOnClickListener(view -> {
            CommonHelper.changeActivity(this, new Exo7Activity());
        });

        btn7.setOnClickListener(view -> {
            Toast.makeText(this, getResources().getText(R.string.text_soon), Toast.LENGTH_SHORT).show();
        });

        btn8.setOnClickListener(view -> {
            Toast.makeText(this, getResources().getText(R.string.text_soon), Toast.LENGTH_SHORT).show();
        });
    }
}