package com.example.tp1;


import static com.example.utils.View.checkNotEmptySpinner;
import static com.example.utils.View.checkNotEmptyText;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.utils.CommonHelper;
import com.example.utils.Dialog;
import com.example.utils.LambaExpr;

public class Exo5Activity extends Activity {

    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo3);
        CommonHelper.createReturnBtn((Activity) this, (LinearLayout) this.findViewById(R.id.exo3_menu));

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

            LambaExpr lambaExprYes = () -> {
                btn1.setBackgroundColor(Color.GREEN);
            };

            LambaExpr lambaExprNo = () -> {
                btn1.setBackgroundColor(Color.RED);
            };

            if (checkNotEmptyText(this, nameText) && checkNotEmptyText(this, surnameText) && checkNotEmptyText(this, ageText) && checkNotEmptySpinner(this, skillED, skillText) && checkNotEmptyText(this, numText)) {
                Dialog.showInterractDialog(this, getResources().getString(R.string.text_confirmation), getResources().getString(R.string.text_confirmation), getResources().getString(R.string.text_confirmation_yes), getResources().getString(R.string.text_confirmation_no), lambaExprYes, lambaExprNo);
            } else {
                Dialog.showErrorDialog(this, getResources().getString(R.string.text_missing_item), getResources().getString(R.string.text_missing_item));
            }
        });
    }
}