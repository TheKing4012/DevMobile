package com.example;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utils.CommonHelper;
import com.example.utils.FadeItemAnimator;
import com.example.utils.LambaExpr;
import com.example.utils.RecyclerItem;
import com.example.utils.RecyclerItemAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.util.ArrayList;
import java.util.List;

public class ListOffersActivity extends Activity {

    private RecyclerView recyclerView;
    private RecyclerItemAdapter adapter;
    private List<RecyclerItem> recyclerItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_offers);

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
        recyclerItemList.add(new RecyclerItem("Item 1", "Description 1"));
        recyclerItemList.add(new RecyclerItem("Item 2", "Description 2"));
        recyclerItemList.add(new RecyclerItem("Item 2", "Description 2"));
        recyclerItemList.add(new RecyclerItem("Item 2", "Description 2"));
        recyclerItemList.add(new RecyclerItem("Item 2", "Description 2"));
        recyclerItemList.add(new RecyclerItem("Item 2", "Description 2"));
        recyclerItemList.add(new RecyclerItem("Item 2", "Description 2"));
        recyclerItemList.add(new RecyclerItem("Item 2", "Description 2"));
        recyclerItemList.add(new RecyclerItem("Item 2", "Description 2"));
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
                CommonHelper.makeNotification(ListOffersActivity.this, getString(R.string.text_error), getString(R.string.text_error_mail_already_used), R.drawable.baseline_warning_24, R.color.ruby, "Some data string passed here", "Some LONGtext for notification here");

            }
        });

        Button buttonApplication = findViewById(R.id.button_see_application);

        buttonApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer un Intent pour démarrer la nouvelle activité
                Intent intent = new Intent(ListOffersActivity.this, ListApplicationActivity.class);
                startActivity(intent); // Démarrer la nouvelle activité
            }
        });

        Spinner typeSpinner = findViewById(R.id.SpinnerOfferType);
        Spinner timeSpinner = findViewById(R.id.SpinnerTime);
        Spinner zoneSpinner = findViewById(R.id.SpinnerZone);

        ArrayAdapter<CharSequence> status_adapter = ArrayAdapter.createFromResource(this,
                R.array.status, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> time_adapter = ArrayAdapter.createFromResource(this,
                R.array.periodes, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> zone_adapter = ArrayAdapter.createFromResource(this,
                R.array.zones, android.R.layout.simple_spinner_item);

        typeSpinner.setAdapter(status_adapter);
        timeSpinner.setAdapter(time_adapter);
        zoneSpinner.setAdapter(zone_adapter);
    }
}