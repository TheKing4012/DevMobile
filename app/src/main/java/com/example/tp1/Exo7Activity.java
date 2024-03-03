package com.example.tp1;


import static com.example.tp1.utils.View.checkNotEmptySpinner;
import static com.example.tp1.utils.View.checkNotEmptyText;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.tp1.utils.Dialog;

public class Exo7Activity extends Activity {

    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo3);

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

            if (checkNotEmptyText(this, nameText) && checkNotEmptyText(this, surnameText) && checkNotEmptyText(this, ageText) && checkNotEmptySpinner(this, skillED, skillText) && checkNotEmptyText(this, numText)) {
                //Dialog.showInterractDialog(btn1, this, getResources().getString(R.string.text_confirmation), getResources().getString(R.string.text_confirmation), getResources().getString(R.string.text_confirmation_yes), getResources().getString(R.string.text_confirmation_no), "Nice Job");
            } else {
                Dialog.showErrorDialog(this, getResources().getString(R.string.text_missing_item), getResources().getString(R.string.text_missing_item));
            }
        });
    }
}