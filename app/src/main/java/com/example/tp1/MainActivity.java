package com.example.tp1;


import static com.example.tp1.utils.View.checkNotEmptySpinner;
import static com.example.tp1.utils.View.checkNotEmptyText;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.tp1.utils.CommonHelper;
import com.example.tp1.utils.Dialog;

public class MainActivity extends Activity {

    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = (Button) findViewById(R.id.button_exo1);
        btn2 = (Button) findViewById(R.id.button_exo2);
        btn3 = (Button) findViewById(R.id.button_exo3);
        btn4 = (Button) findViewById(R.id.button_exo4);
        btn5 = (Button) findViewById(R.id.button_exo5);
        btn6 = (Button) findViewById(R.id.button_exo6);
        btn7 = (Button) findViewById(R.id.button_exo7);


        btn1.setOnClickListener(view -> {
            CommonHelper.changeActivity(this, new HelloWorldActivity());
        });

        btn2.setOnClickListener(view -> {
            CommonHelper.changeActivity(this, new Exo3Activity());
        });

        btn3.setOnClickListener(view -> {
            CommonHelper.changeActivity(this, new Exo5Activity());
        });

        btn4.setOnClickListener(view -> {
            CommonHelper.changeActivity(this, new Exo6Activity());
        });

        btn5.setOnClickListener(view -> {
            CommonHelper.changeActivity(this, new Exo7Activity());
        });

        btn6.setOnClickListener(view -> {
            CommonHelper.changeActivity(this, new Exo8Activity());
        });

        btn7.setOnClickListener(view -> {
            CommonHelper.changeActivity(this, new Exo9Activity());
        });
    }
}