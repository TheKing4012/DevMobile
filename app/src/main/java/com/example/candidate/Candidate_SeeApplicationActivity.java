package com.example.candidate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.R;
import com.example.utils.helpers.CommonHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class Candidate_SeeApplicationActivity extends Activity {

    Button returnBtn;
    Button rejectBtn;
    Button acceptBtn;
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
        setContentView(R.layout.activity_candidate_seeapplication);

        CommonHelper.changeActionbarColor(this, getResources().getColor(R.color.blue));
        CommonHelper.addReturnBtnOnImg(this);

        textViewTitle = findViewById(R.id.TextViewTitle);
        textViewDescription = findViewById(R.id.TextViewDescription);
        textViewZone = findViewById(R.id.TextViewZone);
        textViewPeriode = findViewById(R.id.TextViewPeriode);
        textViewSalary = findViewById(R.id.TextViewSalary);

        textViewDescription.setMovementMethod(new ScrollingMovementMethod());

        textViewTitle.setText("Découvrir le secret de la licorne");
        textViewDescription.setText("Embarquer avec TinTin à la recherche du secret du navire : 'La Licorne'");
        textViewZone.setText("Béziers");
        textViewPeriode.setText("Fall 2023");
        textViewSalary.setText("4000€");


        rejectBtn = findViewById(R.id.button_reject);
        acceptBtn = findViewById(R.id.button_accept);
        returnBtn = findViewById(R.id.button_return);
        positionImage = findViewById(R.id.button_position);

        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer un Intent pour démarrer la nouvelle activité
                Intent intent = new Intent(Candidate_SeeApplicationActivity.this, Candidate_MyApplicationsActivity.class);
                startActivity(intent); // Démarrer la nouvelle activité
            }
        });
    }
}