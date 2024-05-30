package com.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utils.CommonHelper;
import com.example.utils.CustomSpinnerAdapter;
import com.example.utils.FadeItemAnimator;
import com.example.utils.FilteredOffersListener;
import com.example.utils.LambaExpr;
import com.example.utils.Offer;
import com.example.utils.OfferHelper;
import com.example.utils.RecyclerItem;
import com.example.utils.RecyclerItemAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Candidate_ListOffersActivity extends Activity {

    private RecyclerView recyclerView;
    private RecyclerItemAdapter adapter;
    private List<RecyclerItem> recyclerItemList;

    private Button btnSearch;

    private OfferHelper offerHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_listoffers);

        CommonHelper.changeActionbarColor(this, getResources().getColor(R.color.blue));

        LambaExpr exprImg = () -> {
            if(CommonHelper.isFireBaseUserConnected()) {
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

        adapter.setOnItemClickListener(new RecyclerItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                CommonHelper.makeNotification(Candidate_ListOffersActivity.this, getString(R.string.text_error), getString(R.string.text_error_mail_already_used), R.drawable.baseline_warning_24, R.color.ruby, "Some data string passed here", "Some LONGtext for notification here");
            }
        });

        Button buttonApplication = findViewById(R.id.button_see_application);
        buttonApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Candidate_ListOffersActivity.this, Candidate_MyApplicationsActivity.class);
                startActivity(intent);
            }
        });

        Spinner typeSpinner = findViewById(R.id.SpinnerOfferType);
        Spinner timeSpinner = findViewById(R.id.SpinnerTime);
        Spinner zoneSpinner = findViewById(R.id.SpinnerZone);

        CustomSpinnerAdapter statusAdapter = new CustomSpinnerAdapter(this, android.R.layout.simple_spinner_item, getResources().getTextArray(R.array.status));
        CustomSpinnerAdapter timeAdapter = new CustomSpinnerAdapter(this, android.R.layout.simple_spinner_item, getResources().getTextArray(R.array.periodes));
        CustomSpinnerAdapter zoneAdapter = new CustomSpinnerAdapter(this, android.R.layout.simple_spinner_item, getResources().getTextArray(R.array.zones));

        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        zoneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        typeSpinner.setAdapter(statusAdapter);
        timeSpinner.setAdapter(timeAdapter);
        zoneSpinner.setAdapter(zoneAdapter);

        typeSpinner.setSelection(0);
        timeSpinner.setSelection(0);
        zoneSpinner.setSelection(0);

        offerHelper = new OfferHelper();

        loadLast100Offers();

        btnSearch = findViewById(R.id.button_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedOfferType = typeSpinner.getSelectedItem().toString();
                String selectedTime = timeSpinner.getSelectedItem().toString();
                String selectedZone = zoneSpinner.getSelectedItem().toString();

                if (!isDefaultValue(selectedTime) && !isDefaultValue(selectedZone) && !isDefaultValue(selectedOfferType)) {
                    loadLast100OffersWithCriteria(selectedZone, selectedTime, selectedOfferType);
                } else {
                    loadLast100Offers();
                }
            }
        });
    }

    private void loadLast100Offers() {
        offerHelper.getLast100Offers(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recyclerItemList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String title = snapshot.child("title").getValue(String.class);
                    String description = snapshot.child("description").getValue(String.class);
                    recyclerItemList.add(new RecyclerItem(title, description, "status"));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Candidate_ListOffersActivity.this, "Failed to load offers: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadLast100OffersWithCriteria(String zone, String period, String status) {
        offerHelper.getLast100OffersWithCriteria(zone, period, status, new FilteredOffersListener() {
            @Override
            public void onFilteredOffers(List<Offer> offers) {
                recyclerItemList.clear();
                for (Offer offer : offers) {
                    recyclerItemList.add(new RecyclerItem(offer.getTitle(), offer.getDescription(), status));
                    System.out.println(offer);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Candidate_ListOffersActivity.this, "Failed to filter offers: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isDefaultValue(String s) {
        return s.equalsIgnoreCase(getResources().getStringArray(R.array.status)[0]) || s.equalsIgnoreCase(getResources().getStringArray(R.array.periodes)[0])
                || s.equalsIgnoreCase(getResources().getStringArray(R.array.zones)[0]) || s.equalsIgnoreCase(getResources().getStringArray(R.array.type_interim)[0]);
    }
}
