package com.example.candidate;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.MainActivity;
import com.example.R;
import com.example.utils.listeners.CandidateStatusListener;
import com.example.utils.helpers.CommonHelper;
import com.example.utils.animators.FadeItemAnimator;
import com.example.utils.listeners.FilteredOffersListener;
import com.example.utils.LambaExpr;
import com.example.utils.entities.Offer;
import com.example.utils.helpers.OfferHelper;
import com.example.utils.RecyclerItem;
import com.example.utils.adapters.RecyclerItemAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

public class Candidate_MyApplicationsActivity extends Activity {

    private RecyclerView recyclerView;
    private RecyclerItemAdapter adapter;
    private List<RecyclerItem> recyclerItemList;
    private OfferHelper offerHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_myapplications);

        CommonHelper.changeActionbarColor(this, getResources().getColor(R.color.blue));

        LambaExpr exprImg = () -> {
            if (CommonHelper.isFireBaseUserConnected()) {
                CommonHelper.signOutFirebase(this);
                CommonHelper.makeNotification(this, this.getResources().getString(R.string.text_disconnected), "", R.drawable.baseline_warning_24, R.color.ruby, "Some data string passed here", "Some LONGtext for notification here");
                finish();
            }
            CommonHelper.changeActivity(this, new MainActivity());
        };

        CommonHelper.addReturnBtnOnImgWithLambda(this, exprImg);

        recyclerView = findViewById(R.id.recyclerView);

        if (recyclerView == null) {
            throw new NullPointerException("RecyclerView is null. Check your layout XML and the ID used in findViewById.");
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new FadeItemAnimator(this));

        recyclerItemList = new ArrayList<>();
        adapter = new RecyclerItemAdapter(recyclerItemList);
        recyclerView.setAdapter(adapter);

        offerHelper = new OfferHelper();
        String candidateID = FirebaseAuth.getInstance().getUid();

        offerHelper.getOffersByCandidateID(candidateID, new FilteredOffersListener() {
            @Override
            public void onFilteredOffers(List<Offer> offers) {
                for (Offer offer : offers) {
                    offerHelper.getCandidateStatusForOffer(offer.getOfferId(), candidateID, new CandidateStatusListener() {
                        @Override
                        public void onStatusRetrieved(String status) {
                            recyclerItemList.add(new RecyclerItem(offer, status));
                            adapter.notifyDataSetChanged(); // Notify adapter of data change
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            CommonHelper.makeNotification(Candidate_MyApplicationsActivity.this, getString(R.string.text_error), databaseError.getMessage(), R.drawable.baseline_warning_24, R.color.ruby, "Some data string passed here", "Some LONGtext for notification here");
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                CommonHelper.makeNotification(Candidate_MyApplicationsActivity.this, getString(R.string.text_error), databaseError.getMessage(), R.drawable.baseline_warning_24, R.color.ruby, "Some data string passed here", "Some LONGtext for notification here");
            }
        });

        adapter.setOnItemClickListener(new RecyclerItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                CommonHelper.makeNotification(Candidate_MyApplicationsActivity.this, getString(R.string.text_error), getString(R.string.text_error_mail_already_used), R.drawable.baseline_warning_24, R.color.ruby, "Some data string passed here", "Some LONGtext for notification here");
            }
        });

        Button buttonOffers = findViewById(R.id.button_see_offers);
        buttonOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Candidate_MyApplicationsActivity.this, Candidate_ListOffersActivity.class);
                startActivity(intent);
            }
        });

        Spinner statusSpinner = findViewById(R.id.SpinnerStatus);
        ArrayAdapter<CharSequence> status_adapter = ArrayAdapter.createFromResource(this,
                R.array.status, android.R.layout.simple_spinner_item);

        statusSpinner.setAdapter(status_adapter);
    }
}
