package com.example.tp1;

import static com.example.tp1.utils.View.checkNotEmptySpinner;
import static com.example.tp1.utils.View.checkNotEmptyText;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tp1.utils.Dialog;
import com.example.tp1.utils.LambaExpr;

public class CallActivity extends AppCompatActivity {
    Button btn1;
    String phoneNumber;
    TextView tvPhoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        tvPhoneNumber = findViewById(R.id.textViewPhoneNumber);

        Intent intent = getIntent();
        phoneNumber = intent.getStringExtra("phoneNumber");

        tvPhoneNumber.setText(getResources().getString(R.string.text_phoneNumber) +": "+ phoneNumber);

        btn1 = (Button) findViewById(R.id.button_call);

        btn1.setOnClickListener(view -> {
            Intent intentCall = new Intent(Intent.ACTION_CALL);
            intentCall.setData(Uri.parse("tel:" + phoneNumber));
            this.startActivity(intentCall);

            //<uses-permission android:name="android.permission.CALL_PHONE"></uses-permission>
        });
    }
}