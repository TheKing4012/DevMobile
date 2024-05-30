package com.example;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utils.CommonHelper;
import com.example.utils.FadeItemAnimator;
import com.example.utils.RecyclerItem;
import com.example.utils.RecyclerItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class Candidate_MyApplicationsActivity extends Activity {

    private RecyclerView recyclerView;
    private RecyclerItemAdapter adapter;
    private List<RecyclerItem> recyclerItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_myapplications);

        CommonHelper.changeActionbarColor(this, getResources().getColor(R.color.blue));

        CommonHelper.addReturnBtnOnImg(this);

        recyclerView = findViewById(R.id.recyclerView);

        // Vérifiez si recyclerView est null
        if (recyclerView == null) {
            throw new NullPointerException("RecyclerView is null. Check your layout XML and the ID used in findViewById.");
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new FadeItemAnimator(this));

        recyclerItemList = new ArrayList<>();
        recyclerItemList.add(new RecyclerItem("Item 1", "Description 1", "check"));
        recyclerItemList.add(new RecyclerItem("Item 2", "Description 2", "pending"));
        recyclerItemList.add(new RecyclerItem("Item 2", "Description 2", "check"));
        recyclerItemList.add(new RecyclerItem("Item 2", "Description 2", "deny"));
        recyclerItemList.add(new RecyclerItem("Item 2", "Description 2", "pending"));
        recyclerItemList.add(new RecyclerItem("Item 2", "Description 2", "deny"));
        recyclerItemList.add(new RecyclerItem("Item 2", "Description 2", "deny"));
        recyclerItemList.add(new RecyclerItem("Item 2", "Description 2", "pending"));
        recyclerItemList.add(new RecyclerItem("Item 2", "Description 2", "check"));
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
                CommonHelper.makeNotification(Candidate_MyApplicationsActivity.this, getString(R.string.text_error), getString(R.string.text_error_mail_already_used), R.drawable.baseline_warning_24, R.color.ruby, "Some data string passed here", "Some LONGtext for notification here");

            }
        });

        Button buttonOffers = findViewById(R.id.button_see_offers);
        buttonOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer un Intent pour démarrer la nouvelle activité
                Intent intent = new Intent(Candidate_MyApplicationsActivity.this, Candidate_ListOffersActivity.class);
                startActivity(intent); // Démarrer la nouvelle activité
            }
        });

        Spinner statusSpinner = findViewById(R.id.SpinnerStatus);

        ArrayAdapter<CharSequence> status_adapter = ArrayAdapter.createFromResource(this,
                R.array.status, android.R.layout.simple_spinner_item);


        statusSpinner.setAdapter(status_adapter);

    }
}