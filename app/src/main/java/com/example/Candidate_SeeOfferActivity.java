package com.example;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.utils.CommonHelper;
import com.example.utils.CustomSpinnerAdapter;
import com.example.utils.Offer;
import com.example.utils.OfferHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
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

        Intent intent = getIntent();
        Offer offer = null;
        Activity activity = this;
        if (intent != null) {
            offer = intent.getParcelableExtra("offer");
            if (offer != null) {
                textViewTitle.setText(offer.getTitle());
                textViewDescription.setText(offer.getDescription());
                textViewZone.setText(offer.getZone());
                textViewPeriode.setText(offer.getPeriod());
                textViewSalary.setText(offer.getRemuneration() + "€");
            }
        }


        applyBtn = findViewById(R.id.button_apply);
        returnBtn = findViewById(R.id.button_return);
        positionImage = findViewById(R.id.button_position);

        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Candidate_SeeOfferActivity.this, Candidate_ListOffersActivity.class);
                startActivity(intent);
            }
        });

        ImageView btnMap = findViewById(R.id.button_position);

        Offer finalOffer = offer;
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(finalOffer != null) {
                    openMapForZone(finalOffer);
                }
            }
        });

        if(!CommonHelper.isFireBaseUserConnected()) {
            applyBtn.setVisibility(View.GONE);
        } else {
            applyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Créer un Intent pour démarrer la nouvelle activité
                    Intent intent = new Intent(Candidate_SeeOfferActivity.this, Candidate_ApplyActivity.class);
                    if(finalOffer != null) {
                        intent.putExtra("offer", finalOffer);
                    }
                    startActivity(intent); // Démarrer la nouvelle activité
                }
            });
        }
    }

    public void openMapForZone(Offer offer) {
        String zone = offer.getZone();

        if (zone == null || zone.isEmpty()) {
            Toast.makeText(this, "Zone non spécifiée", Toast.LENGTH_SHORT).show();
            return;
        }

        Uri geoLocation = Uri.parse("geo:0,0?q=" + Uri.encode(zone));

        Intent mapIntent = new Intent(Intent.ACTION_VIEW, geoLocation);

        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            Toast.makeText(this, "Aucune application de carte disponible", Toast.LENGTH_SHORT).show();
        }
    }
}