package com.example.tp1;


import static com.example.tp1.utils.View.checkNotEmptySpinner;
import static com.example.tp1.utils.View.checkNotEmptyText;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.tp1.utils.CommonHelper;
import com.example.tp1.utils.Dialog;
import com.example.tp1.utils.LambaExpr;

public class Exo3Activity extends Activity {
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo3);
        CommonHelper.createReturnBtn((Activity) this, (LinearLayout) this.findViewById(R.id.exo3_menu));
    }
}