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
import com.example.utils.LambaExpr;
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

        // Vérifiez si recyclerView est null
        if (recyclerView == null) {
            throw new NullPointerException("RecyclerView is null. Check your layout XML and the ID used in findViewById.");
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new FadeItemAnimator(this));

        recyclerItemList = new ArrayList<>();
        recyclerItemList.add(new RecyclerItem("Item 1", "Description 1", null));
        recyclerItemList.add(new RecyclerItem("Item 2", "Description 2", null));
        recyclerItemList.add(new RecyclerItem("Item 2", "Description 2", null));
        recyclerItemList.add(new RecyclerItem("Item 2", "Description 2", null));
        recyclerItemList.add(new RecyclerItem("Item 2", "Description 2", null));
        recyclerItemList.add(new RecyclerItem("Item 2", "Description 2", null));
        recyclerItemList.add(new RecyclerItem("Item 2", "Description 2", null));
        // Ajoutez d'autres items ici

        adapter = new RecyclerItemAdapter(recyclerItemList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new RecyclerItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                /*Intent intent = new Intent(ListOffersActivity.this, SecondActivity.class);
                intent.putExtra("item_position", position);
                startActivity(intent);
                 */
                CommonHelper.makeNotification(Candidate_ListOffersActivity.this, getString(R.string.text_error), getString(R.string.text_error_mail_already_used), R.drawable.baseline_warning_24, R.color.ruby, "Some data string passed here", "Some LONGtext for notification here");

            }
        });

        Button buttonApplication = findViewById(R.id.button_see_application);

        buttonApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer un Intent pour démarrer la nouvelle activité
                Intent intent = new Intent(Candidate_ListOffersActivity.this, Candidate_MyApplicationsActivity.class);
                startActivity(intent); // Démarrer la nouvelle activité
            }
        });

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

        // Sélectionner "Non défini" comme valeur par défaut
        typeSpinner.setSelection(0);
        timeSpinner.setSelection(0);
        zoneSpinner.setSelection(0);
    }

    private void loadLast100Offers() {

        OfferHelper offerHelper = new OfferHelper();
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
}