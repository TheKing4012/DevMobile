package com.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.utils.CommonHelper;
import com.example.utils.CustomSpinnerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class Candidate_SeeOfferActivity extends Activity {

    Button returnBtn;
    Button applyBtn;
    ImageView positionImage;
    TextView textViewTitle;
    TextView textViewDescription;
    TextView textViewPeriode;
    TextView textViewZone;
    TextView textViewSalary;

    private FirebaseAuth mAuth;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_seeoffer);

        CommonHelper.changeActionbarColor(this, getResources().getColor(R.color.blue));
        CommonHelper.addReturnBtnOnImg(this);

        textViewTitle = findViewById(R.id.TextViewTitle);
        textViewDescription = findViewById(R.id.TextViewDescription);
        textViewZone = findViewById(R.id.TextViewZone);
        textViewPeriode = findViewById(R.id.TextViewPeriode);
        textViewSalary = findViewById(R.id.TextViewSalary);

        textViewTitle.setText("Découvrir le secret de la licorne");
        textViewDescription.setText("Embarquer avec TinTin à la recherche du secret du navire : 'La Licorne'");
        textViewZone.setText("Béziers");
        textViewPeriode.setText("Fall 2023");
        textViewSalary.setText("4000€");


        applyBtn = findViewById(R.id.button_apply);
        returnBtn = findViewById(R.id.button_return);
        positionImage = findViewById(R.id.button_position);

        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer un Intent pour démarrer la nouvelle activité
                Intent intent = new Intent(Candidate_SeeOfferActivity.this, Candidate_ListOffersActivity.class);
                startActivity(intent); // Démarrer la nouvelle activité
            }
        });
    }
}