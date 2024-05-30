package com.example;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utils.CommonHelper;
import com.example.utils.FadeItemAnimator;
import com.example.utils.LambaExpr;
import com.example.utils.RecyclerItem;
import com.example.utils.RecyclerItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class Employer_MyOffersActivity extends Activity {

    private RecyclerView recyclerView;
    private RecyclerItemAdapter adapter;
    private List<RecyclerItem> recyclerItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_myoffers);

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
                CommonHelper.makeNotification(Employer_MyOffersActivity.this, getString(R.string.text_error), getString(R.string.text_error_mail_already_used), R.drawable.baseline_warning_24, R.color.ruby, "Some data string passed here", "Some LONGtext for notification here");

            }
        });

        Button buttonApplication = findViewById(R.id.button_create_offer);

        buttonApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer un Intent pour démarrer la nouvelle activité
                //Intent intent = new Intent(Employers_MyOffersActivity.this, ListApplicationActivity.class);
                //startActivity(intent); // Démarrer la nouvelle activité
            }
        });
    }
}