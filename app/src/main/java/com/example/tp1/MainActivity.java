package com.example.tp1;


import static com.example.tp1.utils.View.checkNotEmptySpinner;
import static com.example.tp1.utils.View.checkNotEmptyText;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.tp1.utils.Dialog;

import java.util.ArrayList;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Création d'un LinearLayout pour contenir les éléments
        LinearLayout layout = new LinearLayout(this);
        layout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        layout.setOrientation(LinearLayout.VERTICAL);

        // Création des EditText
        EditText editTextName = new EditText(this);
        editTextName.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        editTextName.setHint(getString(R.string.text_name));
        layout.addView(editTextName);

        EditText editTextSurname = new EditText(this);
        editTextSurname.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        editTextSurname.setHint(getString(R.string.text_surname));
        layout.addView(editTextSurname);

        EditText editTextAge = new EditText(this);
        editTextAge.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        editTextAge.setHint(getString(R.string.text_age));
        editTextAge.setInputType(InputType.TYPE_CLASS_NUMBER);
        layout.addView(editTextAge);

        Spinner spinnerSkillDomain = new Spinner(this);
        spinnerSkillDomain.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        spinnerSkillDomain.setAdapter(ArrayAdapter.createFromResource(this, R.array.skillDomains, android.R.layout.simple_spinner_item));
        spinnerSkillDomain.setPrompt(getString(R.string.text_skillDomain));
        layout.addView(spinnerSkillDomain);

        EditText editTextPhoneNumber = new EditText(this);
        editTextPhoneNumber.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        editTextPhoneNumber.setHint(getString(R.string.text_phoneNumber));
        editTextPhoneNumber.setInputType(InputType.TYPE_CLASS_PHONE);
        layout.addView(editTextPhoneNumber);

        Button buttonSend = new Button(this);
        buttonSend.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        buttonSend.setText(getString(R.string.text_btn_send));
        layout.addView(buttonSend);

        setContentView(layout);
    }
}
