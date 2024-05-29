package com.example;


import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utils.CommonHelper;
import com.example.utils.FadeItemAnimator;
import com.example.utils.RecyclerItem;
import com.example.utils.RecyclerItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListApplicationActivity extends Activity {

    private RecyclerView recyclerView;
    private RecyclerItemAdapter adapter;
    private List<RecyclerItem> recyclerItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_application);

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
                CommonHelper.makeNotification(ListApplicationActivity.this, getString(R.string.text_error), getString(R.string.text_error_mail_already_used), R.drawable.baseline_warning_24, R.color.ruby, "Some data string passed here", "Some LONGtext for notification here");

            }
        });

        Spinner typeSpinner = findViewById(R.id.SpinnerOfferType);
        Spinner periodeSpinner = findViewById(R.id.SpinnerTime);
        Spinner zoneSpinner = findViewById(R.id.SpinnerZone);

        ArrayAdapter<CharSequence> type_adapter = ArrayAdapter.createFromResource(this,
                R.array.type_interim, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> periode_adapter = ArrayAdapter.createFromResource(this,
                R.array.periodes, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> zone_adapter = ArrayAdapter.createFromResource(this,
                R.array.zones, android.R.layout.simple_spinner_item);


        typeSpinner.setAdapter(type_adapter);
        periodeSpinner.setAdapter(periode_adapter);
        zoneSpinner.setAdapter(zone_adapter);

    }
}