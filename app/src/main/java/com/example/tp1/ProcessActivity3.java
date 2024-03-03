package com.example.tp1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.tp1.utils.CommonHelper;

public class ProcessActivity3 extends AppCompatActivity {
    Button btn1;
    String name, surname, age, skillDomain, phoneNumber;
    TextView tvName, tvSurname, tvAge, tvSkillDomain, tvPhoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process3);

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
            Intent intentCall = new Intent(this, CallActivity.class);
            intentCall.putExtra("phoneNumber", phoneNumber);
            this.startActivity(intentCall);
        });

    }
}