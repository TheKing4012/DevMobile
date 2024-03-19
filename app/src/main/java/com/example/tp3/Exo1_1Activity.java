package com.example.tp3;


import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Exo1_1Activity extends Activity {

    Button btn1;
    EditText editTextName, editTextSurname, editTextPhoneNumber, editTextEmail;
    Spinner spinnerDay, spinnerMonth, spinnerYear;

    CheckBox checkBoxSport, checkBoxMusique, checkBoxLecture, checkBoxSynchronisation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tp3_exo1);

        // Récupération des vues
        editTextName = findViewById(R.id.editTextName);
        editTextSurname = findViewById(R.id.editTextSurname);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        editTextEmail = findViewById(R.id.editTextEmail);
        spinnerDay = findViewById(R.id.spinnerDay);
        spinnerMonth = findViewById(R.id.spinnerMonth);
        spinnerYear = findViewById(R.id.spinnerYear);
        checkBoxSport = findViewById(R.id.checkBoxSport);
        checkBoxMusique = findViewById(R.id.checkBoxMusique);
        checkBoxLecture = findViewById(R.id.checkBoxLecture);
        checkBoxSynchronisation = findViewById(R.id.checkBoxSynchronisation);

        // Configuration des Spinners
        setupSpinners(spinnerDay, spinnerMonth, spinnerYear);
    }

    private void setupSpinners(Spinner spinnerDay, Spinner spinnerMonth, Spinner spinnerYear) {
        // Configuration du Spinner pour le jour (1-31)
        List<String> dayList = new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            dayList.add(String.valueOf(i));
        }
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dayList);
        spinnerDay.setAdapter(dayAdapter);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.months_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonth.setAdapter(adapter);

        // Configuration du Spinner pour l'année (1900 - année actuelle)
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        List<String> yearList = new ArrayList<>();
        for (int i = 1900; i <= currentYear; i++) {
            yearList.add(String.valueOf(i));
        }
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, yearList);
        spinnerYear.setAdapter(yearAdapter);
    }
}