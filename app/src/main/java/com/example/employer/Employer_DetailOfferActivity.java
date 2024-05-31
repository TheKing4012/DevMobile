package com.example.employer;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.candidate.Candidate_ListOffersActivity;
import com.example.utils.entities.Candidate;
import com.example.utils.helpers.CommonHelper;
import com.example.utils.animators.FadeItemAnimator;
import com.example.utils.entities.Offer;
import com.example.utils.helpers.OfferHelper;
import com.example.utils.RecyclerItem;
import com.example.utils.adapters.RecyclerItemAdapter_buttons;
import com.example.utils.listeners.FilteredCandidatesListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class Employer_DetailOfferActivity extends Activity {

    private RecyclerView recyclerView;
    private TextView offerTitle;
    private RecyclerItemAdapter_buttons adapter;
    private List<RecyclerItem> recyclerItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_detailoffer);

        CommonHelper.changeActionbarColor(this, getResources().getColor(R.color.blue));

        CommonHelper.addReturnBtnOnImg(this);

        offerTitle = findViewById(R.id.textViewTitle);
        recyclerView = findViewById(R.id.recyclerView);

        // Vérifiez si recyclerView est null
        if (recyclerView == null) {
            throw new NullPointerException("RecyclerView is null. Check your layout XML and the ID used in findViewById.");
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new FadeItemAnimator(this));

        recyclerItemList = new ArrayList<>();
        // Ajoutez d'autres items ici

        adapter = new RecyclerItemAdapter_buttons(recyclerItemList);
        adapter.setActivity(this);
        adapter.setTextProfile(this.getResources().getString(R.string.text_see_profile));
        recyclerView.setAdapter(adapter);

        Button buttonOffers = findViewById(R.id.button_see_offers);
        buttonOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer un Intent pour démarrer la nouvelle activité
                Intent intent = new Intent(Employer_DetailOfferActivity.this, Candidate_ListOffersActivity.class);
                startActivity(intent); // Démarrer la nouvelle activité
            }
        });

        Intent intent = getIntent();
        Offer offer;
        Activity activity = this;
        if (intent != null) {
            offer = intent.getParcelableExtra("offer");
            if (offer != null) {
                adapter.setOfferID(offer.getOfferId());
                offerTitle.setText(offer.getTitle()); // Par exemple, pour afficher le titre de l'offre
                Button btnDelete = findViewById(R.id.button_see_offers);

                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OfferHelper offerHelper = new OfferHelper();
                        offerHelper.deleteOffer(offer.getOfferId(), new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                CommonHelper.makeNotification(activity, activity.getResources().getString(R.string.text_delete_successs), "", R.drawable.baseline_warning_24, R.color.ruby, "Some data string passed here", "Some LONGtext for notification here");
                                CommonHelper.changeActivity(Employer_DetailOfferActivity.this, new Employer_MyOffersActivity());
                            }
                        });
                    }
                });

                OfferHelper offerHelper = new OfferHelper();
                offerHelper.getCandidatesByOfferID(offer.getOfferId(), new FilteredCandidatesListener() {
                    @Override
                    public void onFilteredCandidates(List<Candidate> candidates) {
                        recyclerItemList.clear();
                        for (Candidate candidate : candidates) {
                            recyclerItemList.add(new RecyclerItem(candidate, null));
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle possible errors.
                    }
                });
            }
        }
    }
}