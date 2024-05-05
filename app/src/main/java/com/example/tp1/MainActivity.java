package com.example.tp1;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.R;

public class MainActivity extends Activity {

    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText nameED = findViewById(R.id.editTextName);
        EditText surnameED = findViewById(R.id.editTextSurname);
        EditText ageED = findViewById(R.id.editTextAge);
        Spinner skillED = findViewById(R.id.spinnerSkillDomain);
        EditText numED = findViewById(R.id.editTextPhoneNumber);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if(ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }

        btn1 = (Button) findViewById(R.id.button_send);

        btn1.setOnClickListener(view -> {
            String nameText = nameED.getText().toString();
            String surnameText = surnameED.getText().toString();
            String ageText = ageED.getText().toString();
            String skillText = skillED.getSelectedItem().toString();
            String numText = numED.getText().toString();

            makeNotification();
            /*
            if(checkEmptyText(nameText) && checkEmptyText(surnameText) && checkEmptyText(ageText) && checkEmptySpinner(skillED, skillText) && checkEmptyText(numText)) {
                showAlterDialogInterract("Are you sure ?", getResources().getString(R.string.text_confirmation), getResources().getString(R.string.text_confirmation_yes), getResources().getString(R.string.text_confirmation_no));
            } else {
                showAlterDialog(getResources().getString(R.string.text_missing_item), getResources().getString(R.string.text_missing_item));
            }
             */
        });
    }

    public void makeNotification() {
        String chanelID = "CHANNEL_ID_NOTIFICATION";
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getApplicationContext(), chanelID);
        builder
                .setSmallIcon(R.drawable.baseline_warning_24)
                .setContentTitle("Test Title")
                .setContentText("Some text for notification here")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("data", "Some value to be passed here");

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                0, intent, PendingIntent.FLAG_MUTABLE);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel =
                    notificationManager.getNotificationChannel(chanelID);
            if(notificationChannel == null) {
                int importante = NotificationManager.IMPORTANCE_HIGH;
                notificationChannel = new NotificationChannel(chanelID,
                        "Some description", importante);
                notificationChannel.setLightColor(Color.WHITE);
                notificationChannel.enableVibration(true);
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        notificationManager.notify(0, builder.build());
    }

    private boolean checkEmptyText(String text) {
        if(text.isEmpty()) {
            showDialog(R.string.text_missing_item);
            return false;
        }
        return true;
    }

    private boolean checkEmptySpinner(Spinner spinner, String text) {
        if(text.equals(spinner.getAdapter().getItem(0).toString())) {
            showDialog(R.string.text_missing_item);
            return false;
        }
        return true;
    }

    private void showAlterDialogInterract(String title, String message, String msg_yes, String msg_no) {
        AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(msg_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Nice Job", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(msg_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();

    }

    private void showAlterDialog(String title, String message) {
        AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();

    }
}