package com.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.example.utils.CommonHelper;
import com.example.utils.CustomSpinnerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class Employer_CreateOfferActivity extends Activity {

    Button publierBtn;

    private FirebaseAuth mAuth;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_createoffer);

        CommonHelper.changeActionbarColor(this, getResources().getColor(R.color.blue));

        CommonHelper.addReturnBtnOnImg(this);

        CommonHelper.centerAndIntalicEditTextHint(this, getString(R.string.text_title), R.id.EditTextTitle);
        CommonHelper.centerAndIntalicEditTextHint(this, getString(R.string.text_salary), R.id.EditTextSalary);
        CommonHelper.centerAndIntalicEditTextHint(this, getString(R.string.text_description), R.id.EditTextDescription);

        publierBtn = findViewById(R.id.button_public_offer);

        Spinner typeSpinner = findViewById(R.id.SpinnerOfferType);
        Spinner timeSpinner = findViewById(R.id.SpinnerTime);
        Spinner zoneSpinner = findViewById(R.id.SpinnerZone);

        CustomSpinnerAdapter statusAdapter = new CustomSpinnerAdapter(this,
                android.R.layout.simple_spinner_item, getResources().getTextArray(R.array.status));
        CustomSpinnerAdapter timeAdapter = new CustomSpinnerAdapter(this,
                android.R.layout.simple_spinner_item, getResources().getTextArray(R.array.periodes));
        CustomSpinnerAdapter zoneAdapter = new CustomSpinnerAdapter(this,
                android.R.layout.simple_spinner_item, getResources().getTextArray(R.array.zones));

        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        zoneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        typeSpinner.setAdapter(statusAdapter);
        timeSpinner.setAdapter(timeAdapter);
        zoneSpinner.setAdapter(zoneAdapter);

        // Sélectionner "Non défini" comme valeur par défaut
        typeSpinner.setSelection(0);
        timeSpinner.setSelection(0);
        zoneSpinner.setSelection(0);

        Button buttonRetour = findViewById(R.id.button_return);

        buttonRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer un Intent pour démarrer la nouvelle activité
                Intent intent = new Intent(Employer_CreateOfferActivity.this, Employer_MyOffersActivity.class);
                startActivity(intent); // Démarrer la nouvelle activité
            }
        });
    }
}