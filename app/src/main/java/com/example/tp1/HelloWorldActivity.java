package com.example.tp1;


import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.utils.CommonHelper;

public class HelloWorldActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.helloworld);

        CommonHelper.createReturnBtn((Activity) this, (LinearLayout) this.findViewById(R.id.tp1_helloworld_menu));
    }
}