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
        // Vérifier si la permission est déjà accordée
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // Demander la permission à l'utilisateur
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, requestCode);
        } else {
            // La permission est déjà accordée, effectuer l'action désirée ici
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