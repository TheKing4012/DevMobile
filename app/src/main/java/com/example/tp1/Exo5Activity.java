package com.example.tp1;


import static com.example.tp1.utils.View.checkEmptySpinner;
import static com.example.tp1.utils.View.checkEmptyText;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tp1.utils.Dialog;
import com.example.tp1.utils.View;

public class Exo5Activity extends Activity {

    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText nameED = findViewById(R.id.editTextName);
        EditText surnameED = findViewById(R.id.editTextSurname);
        EditText ageED = findViewById(R.id.editTextAge);
        Spinner skillED = findViewById(R.id.spinnerSkillDomain);
        EditText numED = findViewById(R.id.editTextPhoneNumber);

        btn1 = (Button) findViewById(R.id.button_send);

        btn1.setOnClickListener(view -> {
            String nameText = nameED.getText().toString();
            String surnameText = surnameED.getText().toString();
            String ageText = ageED.getText().toString();
            String skillText = skillED.getSelectedItem().toString();
            String numText = numED.getText().toString();

            if (checkEmptyText(this, nameText) && checkEmptyText(this, surnameText) && checkEmptyText(this, ageText) && checkEmptySpinner(this, skillED, skillText) && checkEmptyText(this, numText)) {
                Dialog.showInterractDialog(this, getResources().getString(R.string.text_confirmation), getResources().getString(R.string.text_confirmation), getResources().getString(R.string.text_confirmation_yes), getResources().getString(R.string.text_confirmation_no), "Nice Job");
            } else {
                Dialog.showErrorDialog(this, getResources().getString(R.string.text_missing_item), getResources().getString(R.string.text_missing_item));
            }
        });
    }
}