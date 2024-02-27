package com.example.tp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class ProcessActivity extends AppCompatActivity {

    String name, surname, age, skillDomain, phoneNumber;
    TextView tvName, tvSurname, tvAge, tvSkillDomain, tvPhoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process);

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

    }
}