package com.example.tp1;


import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.utils.CommonHelper;

public class Exo3ActivityOnlyJava extends Activity {
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        ));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setId(R.id.exo3_menu);


        EditText editTextName = new EditText(this);
        editTextName.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        editTextName.setHint(getString(R.string.text_name));


        EditText editTextSurname = new EditText(this);
        editTextSurname.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        editTextSurname.setHint(getString(R.string.text_surname));


        EditText editTextAge = new EditText(this);
        editTextAge.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        editTextAge.setHint(getString(R.string.text_age));
        editTextAge.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);


        Spinner spinnerSkillDomain = new Spinner(this);
        spinnerSkillDomain.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.skillDomains, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSkillDomain.setAdapter(adapter);
        spinnerSkillDomain.setPrompt(getString(R.string.text_skillDomain));

        // Création du composant EditText pour le numéro de téléphone
        EditText editTextPhoneNumber = new EditText(this);
        editTextPhoneNumber.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        editTextPhoneNumber.setHint(getString(R.string.text_phoneNumber));
        editTextPhoneNumber.setInputType(InputType.TYPE_CLASS_PHONE);


        Button buttonSend = new Button(this);
        buttonSend.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        buttonSend.setText(getString(R.string.text_btn_send));


        linearLayout.addView(editTextName);
        linearLayout.addView(editTextSurname);
        linearLayout.addView(editTextAge);
        linearLayout.addView(spinnerSkillDomain);
        linearLayout.addView(editTextPhoneNumber);
        linearLayout.addView(buttonSend);


        setContentView(linearLayout);

        CommonHelper.createReturnBtn((Activity) this, linearLayout);
    }
}