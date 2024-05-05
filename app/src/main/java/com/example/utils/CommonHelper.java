package com.example.utils;

import static androidx.core.content.ContextCompat.getSystemService;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.content.Intent;
import android.text.Layout;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.NotificationActivity;
import com.example.R;

public class CommonHelper {
    public static void createReturnBtn(Activity activity, LinearLayout layout) {

        Button monBouton = new Button(activity);
        monBouton.setText(R.string.text_btn_return);
        monBouton.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.BOTTOM
        ));

        monBouton.setOnClickListener(view -> {
                activity.finish();
        });

        FrameLayout frameLayout = new FrameLayout(activity);
        LinearLayout.LayoutParams frameParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0,
                1
        );
        frameLayout.setLayoutParams(frameParams);

        layout.addView(frameLayout);

        frameLayout.addView(monBouton);
    }

    public static void createReturnBtn(Activity activity, ConstraintLayout layout) {
        Button monBouton = new Button(activity);
        monBouton.setText(R.string.text_btn_return);

        // Création des paramètres de layout pour le bouton
        ConstraintLayout.LayoutParams buttonParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        buttonParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID; // Alignement du bas du bouton sur le bas du parent
        monBouton.setLayoutParams(buttonParams);

        monBouton.setOnClickListener(view -> {
            activity.finish();
        });

        // Ajout du bouton au layout ConstraintLayout
        layout.addView(monBouton);
    }

    public static void createReturnBtn(Fragment fragment, ConstraintLayout layout) {

        Button monBouton = new Button(fragment.getContext());
        monBouton.setText(R.string.text_btn_return);
        // Création des paramètres de layout pour le bouton
        ConstraintLayout.LayoutParams buttonParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        buttonParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID; // Alignement du bas du bouton sur le bas du parent
        monBouton.setLayoutParams(buttonParams);

        monBouton.setOnClickListener(view -> {
            fragment.getParentFragmentManager().popBackStackImmediate();
        });

        // Ajout du bouton au layout ConstraintLayout
        layout.addView(monBouton);
    }

    public static void changeActivity(Activity from, Activity where) {
        Intent intent = new Intent(from, where.getClass());
        from.startActivity(intent);
        //from.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public static void changeActionbarColor(Activity from, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            from.getWindow().setStatusBarColor(id);
        }
    }

    public static void makeNotification(Activity from, int id) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if(ContextCompat.checkSelfPermission(from,
                    Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(from,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }

        String chanelID = "CHANNEL_ID_NOTIFICATION";
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(from.getApplicationContext(), chanelID);
        builder
                .setSmallIcon(R.drawable.baseline_warning_24)
                .setContentTitle("Test Title")
                .setContentText("Some text for notification here")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        Intent intent = new Intent(from.getApplicationContext(), NotificationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("data", "Some value to be passed here");

        PendingIntent pendingIntent = PendingIntent.getActivity(from.getApplicationContext(),
                0, intent, PendingIntent.FLAG_MUTABLE);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) from.getSystemService(Context.NOTIFICATION_SERVICE);

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
}
