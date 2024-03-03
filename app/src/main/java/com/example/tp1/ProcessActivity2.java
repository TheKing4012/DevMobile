package com.example.tp1;

import static com.example.tp1.utils.View.checkNotEmptySpinner;
import static com.example.tp1.utils.View.checkNotEmptyText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tp1.utils.CommonHelper;
import com.example.tp1.utils.Dialog;
import com.example.tp1.utils.LambaExpr;

import java.util.ArrayList;

public class ProcessActivity2 extends AppCompatActivity {
    Button btn1;
    String name, surname, age, skillDomain, phoneNumber;
    TextView tvName, tvSurname, tvAge, tvSkillDomain, tvPhoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process2);

        CommonHelper.createReturnBtn((Activity) this, (ConstraintLayout) this.findViewById(R.id.process_menu2));

        tvName = findViewById(R.id.textViewName);
        tvSurname = findViewById(R.id.textViewSurname);
        tvAge = findViewById(R.id.textViewAge);
        tvSkillDomain = findViewById(R.id.textViewSkillDomain);
        tvPhoneNumber = findViewById(R.id.textViewPhoneNumber);

        Intent intent = getIntent();

        name = intent.getStringExtra("name");
        surname = intent.getStringExtra("surname");
        age = intent.getStringExtra("age");
        skillDomain = intent.getStringExtra("skillDomain");
        phoneNumber = intent.getStringExtra("phoneNumber");

        tvName.setText(getResources().getString(R.string.text_name) +": "+ name);
        tvSurname.setText(getResources().getString(R.string.text_age) +": "+ surname);
        tvAge.setText(getResources().getString(R.string.text_age) +": "+ age);
        tvSkillDomain.setText(getResources().getString(R.string.text_skillDomain) +": "+ skillDomain);
        tvPhoneNumber.setText(getResources().getString(R.string.text_phoneNumber) +": "+ phoneNumber);

        btn1 = (Button) findViewById(R.id.button_ok);

        btn1.setOnClickListener(view -> {
            this.finish();
        });

    }
}