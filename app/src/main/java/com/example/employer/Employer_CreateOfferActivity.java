package com.example.employer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.R;
import com.example.utils.helpers.CommonHelper;
import com.example.utils.adapters.CustomSpinnerAdapter;
import com.example.utils.helpers.OfferHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
                android.R.layout.simple_spinner_item, getResources().getTextArray(R.array.type_interim));
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

        typeSpinner.setSelection(0);
        timeSpinner.setSelection(0);
        zoneSpinner.setSelection(0);

        Button buttonRetour = findViewById(R.id.button_return);
        buttonRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Employer_CreateOfferActivity.this, Employer_MyOffersActivity.class);
                startActivity(intent);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        publierBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createOffer();
            }
        });
    }

    private void createOffer() {
        EditText titleEditText = findViewById(R.id.EditTextTitle);
        EditText salaryEditText = findViewById(R.id.EditTextSalary);
        EditText descriptionEditText = findViewById(R.id.EditTextDescription);
        Spinner typeSpinner = findViewById(R.id.SpinnerOfferType);
        Spinner timeSpinner = findViewById(R.id.SpinnerTime);
        Spinner zoneSpinner = findViewById(R.id.SpinnerZone);

        String title = titleEditText.getText().toString().trim();
        String salary = salaryEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        String type = typeSpinner.getSelectedItem().toString();
        String time = timeSpinner.getSelectedItem().toString();
        String zone = zoneSpinner.getSelectedItem().toString();

        if (title.isEmpty() || salary.isEmpty() || description.isEmpty() || type.isEmpty() || time.isEmpty() || zone.isEmpty()) {
            CommonHelper.makeNotification(this, getString(R.string.text_error), getString(R.string.text_error_create_offer), R.drawable.baseline_warning_24, R.color.ruby, "Some data string passed here", "Some LONGtext for notification here");
            return;
        }

        FirebaseUser currentUser = mAuth.getCurrentUser();

        String employerID = currentUser.getUid();

        OfferHelper offerHelper = new OfferHelper();
        offerHelper.addOffer(title, description, type, zone, salary, time, employerID, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    CommonHelper.makeNotification(Employer_CreateOfferActivity.this, getString(R.string.text_offer_created), "", R.drawable.baseline_warning_24, R.color.ruby, "Some data string passed here", "Some LONGtext for notification here");
                    Intent intent = new Intent(Employer_CreateOfferActivity.this, Employer_MyOffersActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    CommonHelper.makeNotification(Employer_CreateOfferActivity.this, getString(R.string.text_error), databaseError.getMessage(), R.drawable.baseline_warning_24, R.color.ruby, "Some data string passed here", "Some LONGtext for notification here");
                }
            }
        });
    }
}
