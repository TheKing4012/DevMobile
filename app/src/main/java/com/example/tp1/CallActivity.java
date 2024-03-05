package com.example.tp1;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class CallActivity extends AppCompatActivity {
    Button btn1;
    String phoneNumber;
    TextView tvPhoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Vérifier si la permission est déjà accordée
            if (ContextCompat.checkSelfPermission(CallActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // Demander la permission à l'utilisateur
                ActivityCompat.requestPermissions(CallActivity.this, new String[]{android.Manifest.permission.CALL_PHONE}, 101);
            }
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        tvPhoneNumber = findViewById(R.id.tVPhoneNumber);

        Intent intent = getIntent();
        phoneNumber = intent.getStringExtra("phoneNumber");

        tvPhoneNumber.setText(phoneNumber);

        btn1 = (Button) findViewById(R.id.button_call);

        btn1.setBackgroundColor(Color.TRANSPARENT);

        btn1.setText("   ");

        btn1.setOnClickListener(view -> {
            Intent intentCall = new Intent(Intent.ACTION_CALL);
            intentCall.setData(Uri.parse("tel:" + phoneNumber));
            this.startActivity(intentCall);

            //<uses-permission android:name="android.permission.CALL_PHONE"></uses-permission>
        });
    }
}