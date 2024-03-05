package com.example.tp2;


import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.R;
import com.example.utils.CommonHelper;

public class TP2Activity extends Activity {

    Button btn1, btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tp2);

        CommonHelper.createReturnBtn((Activity) this, (LinearLayout) this.findViewById(R.id.tp2_menu));

        btn1 = (Button) findViewById(R.id.button_exo1);
        btn2 = (Button) findViewById(R.id.button_exo2);


        btn1.setOnClickListener(view -> {
            CommonHelper.changeActivity(this, new Exo1Activity());
        });

        btn2.setOnClickListener(view -> {
            CommonHelper.changeActivity(this, new Exo2Activity());
        });

        /*
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

         */
    }
}