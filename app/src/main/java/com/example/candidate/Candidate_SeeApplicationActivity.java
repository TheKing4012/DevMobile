package com.example.candidate;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.R;
import com.example.utils.entities.Candidate;
import com.example.utils.entities.Offer;
import com.example.utils.helpers.CommonHelper;
import com.example.utils.helpers.OfferHelper;
import com.example.utils.listeners.FilteredCandidatesListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

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

        acceptBtn.setVisibility(View.VISIBLE);
        rejectBtn.setVisibility(View.VISIBLE);

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

        ImageView btnGPS = findViewById(R.id.button_position);

        Offer finalOffer = offer;
        btnGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMapForZone(finalOffer);
            }
        });

        OfferHelper offerHelper = new OfferHelper();
        offerHelper.getCandidateInfo(offer.getOfferId(), FirebaseAuth.getInstance().getUid(), new FilteredCandidatesListener() {
            @Override
            public void onFilteredCandidates(List<Candidate> candidates) {
                if (!candidates.isEmpty()) {
                    Candidate candidate = candidates.get(0); // Assuming you expect only one candidate per offer
                    String status = candidate.getAnswer();

                    System.out.println("Candidate status: " + status); // Log candidate status

                    // Assuming acceptBtn and rejectBtn are properly defined in your activity layout
                    acceptBtn.setVisibility(View.VISIBLE);
                    rejectBtn.setVisibility(View.VISIBLE);
                } else {
                    // Handle case where no candidates are found
                    System.out.println("No candidates found for offer: " + finalOffer.getOfferId());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database error
                CommonHelper.makeNotification(activity, getString(R.string.text_error), databaseError.getMessage(), R.drawable.baseline_warning_24, R.color.ruby, "Some data string passed here", "Some LONGtext for notification here");
            }
        });

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