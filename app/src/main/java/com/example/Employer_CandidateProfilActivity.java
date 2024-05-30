package com.example;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.utils.CommonHelper;
import com.example.utils.LambaExpr;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class Employer_CandidateProfilActivity extends Activity {

    Button returnBtn;
    TextView textViewPrenom;
    TextView textViewNom;
    TextView textViewDateNaissance;
    TextView textViewCountry;
    TextView textViewCity;
    TextView textViewDescription;

    private FirebaseAuth mAuth;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_candidateprofil);
        CommonHelper.changeActionbarColor(this, getResources().getColor(R.color.blue));
        CommonHelper.addReturnBtnOnImg(this);

        LambaExpr exprLogIn = () -> {
            CommonHelper.changeActivity(this, new Candidate_LoginActivity());
        };

        textViewPrenom = findViewById(R.id.TextViewPrenom);
        textViewNom = findViewById(R.id.TextViewNom);
        textViewDateNaissance = findViewById(R.id.TextViewDateNaissance);
        textViewCountry = findViewById(R.id.TextViewCountry);
        textViewCity = findViewById(R.id.TextViewCity);
        textViewDescription = findViewById(R.id.TextViewDescription);

        textViewPrenom.setText("Napoléon");
        textViewNom.setText("Bonaparte");
        textViewDateNaissance.setText("15/08/1769");
        textViewCountry.setText("France");
        textViewCity.setText("Ajaccio");
        textViewDescription.setText("Napoléon Bonaparte était un empereur et un chef militaire français, qui a régné sur la France de 1804 à 1814 et de 1815 à sa mort en 1821. Il a laissé un héritage durable en France et en Europe, avec des réformes administratives, juridiques et éducatives qui ont façonné la société moderne.");

        Button buttonRetour = findViewById(R.id.button_return);

        buttonRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer un Intent pour démarrer la nouvelle activité
                Intent intent = new Intent(Employer_CandidateProfilActivity.this, Employer_MyOffersActivity.class);
                startActivity(intent); // Démarrer la nouvelle activité
            }
        });
    }
}