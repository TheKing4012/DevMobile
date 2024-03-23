package com.example.tp3;


import static com.example.utils.View.checkNotEmptySpinner;
import static com.example.utils.View.checkNotEmptyText;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.Fragment;

import com.example.R;

import com.example.utils.CommonHelper;
import com.example.utils.Dialog;
import com.example.utils.LambaExpr;

public class Exo1Activity extends FragmentActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tp3_exo1);
        //CommonHelper.createReturnBtn((Activity) this, (LinearLayout) this.findViewById(R.id.tp1_exo3_menu));

    }
}