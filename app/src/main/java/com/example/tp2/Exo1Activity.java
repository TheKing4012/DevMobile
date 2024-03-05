package com.example.tp2;


import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.R;
import com.example.utils.CommonHelper;

public class Exo1Activity extends Activity {
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo3);
        CommonHelper.createReturnBtn((Activity) this, (LinearLayout) this.findViewById(R.id.tp1_exo3_menu));
    }
}