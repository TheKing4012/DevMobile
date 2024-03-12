package com.example.tp2;


import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.R;
import com.example.utils.CommonHelper;

public class TP2Activity extends Activity {

    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tp2);

        CommonHelper.createReturnBtn((Activity) this, (ConstraintLayout) this.findViewById(R.id.tp2_menu));
        CommonHelper.changeActionbarColor(this, getResources().getColor(R.color.ruby));

        btn1 = (Button) findViewById(R.id.button_exo1);
        btn2 = (Button) findViewById(R.id.button_exo2);
        btn3 = (Button) findViewById(R.id.button_exo3);
        btn4 = (Button) findViewById(R.id.button_exo4);
        btn5 = (Button) findViewById(R.id.button_exo5);

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

        btn5.setOnClickListener(view -> {
            CommonHelper.changeActivity(this, new Exo5Activity());
        });
/*
        btn6.setOnClickListener(view -> {
            CommonHelper.changeActivity(this, new Exo6Activity());
        });

        btn7.setOnClickListener(view -> {
            Toast.makeText(this, getResources().getText(R.string.text_soon), Toast.LENGTH_SHORT).show();
        });

        btn8.setOnClickListener(view -> {
            Toast.makeText(this, getResources().getText(R.string.text_soon), Toast.LENGTH_SHORT).show();
        });

         */
    }
}