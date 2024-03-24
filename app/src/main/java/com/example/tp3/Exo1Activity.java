package com.example.tp3;

import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;

import com.example.R;

public class Exo1Activity extends FragmentActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JsonReader.deleteJSONFile(this);
        setContentView(R.layout.activity_tp3_exo1);

    }
}