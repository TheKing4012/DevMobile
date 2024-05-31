package com.example;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utils.CommonHelper;
import com.example.utils.FadeItemAnimator;
import com.example.utils.Offer;
import com.example.utils.RecyclerItem;
import com.example.utils.RecyclerItemAdapter_buttons;

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
        if (intent != null) {
            offer = intent.getParcelableExtra("offer");
            if (offer != null) {
                offerTitle.setText(offer.getTitle()); // Par exemple, pour afficher le titre de l'offre
            }
        }


        //--------------------------------------------------------------------------

        //METTRE LE TITRE DE LOFFRE SELECTIONNE

        //offerTitle.setText("TITRE DE LOFFRE A SET");

        //METTRE LE TITRE DE LOFFRE SELECTIONNE

        //--------------------------------------------------------------------------
    }
}