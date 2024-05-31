package com.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utils.CommonHelper;
import com.example.utils.FadeItemAnimator;
import com.example.utils.FilteredOffersListener;
import com.example.utils.LambaExpr;
import com.example.utils.Offer;
import com.example.utils.OfferHelper;
import com.example.utils.RecyclerItem;
import com.example.utils.RecyclerItemAdapter;
import com.example.utils.RecyclerItemAdapter_buttons;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

public class Employer_MyOffersActivity extends Activity {

    private RecyclerView recyclerView;
    private RecyclerItemAdapter adapter;
    private List<RecyclerItem> recyclerItemList;
    private FirebaseAuth mAuth;
    private OfferHelper offerHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_myoffers);

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

        mAuth = FirebaseAuth.getInstance();
        offerHelper = new OfferHelper();

        List<Offer> myOffers = new ArrayList<>();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String employerID = currentUser.getUid();
        offerHelper.getOffersByEmployerID(employerID, new FilteredOffersListener() {
            @Override
            public void onFilteredOffers(List<Offer> offers) {
                recyclerItemList.clear();
                for (Offer offer : offers) {
                    myOffers.add(offer);
                    recyclerItemList.add(new RecyclerItem(offer, ""));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors.
            }
        });

        adapter.setOnItemClickListener(new RecyclerItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Offer offer = recyclerItemList.get(position).getOffer();
                Intent intent = new Intent(Employer_MyOffersActivity.this, Employer_DetailOfferActivity.class);
                intent.putExtra("offer", offer);
                startActivity(intent);
            }
        });

        Button btnNewOffer = findViewById(R.id.button_create_offer);

        btnNewOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonHelper.changeActivity(Employer_MyOffersActivity.this, new Employer_CreateOfferActivity());
            }
        });
    }
}
