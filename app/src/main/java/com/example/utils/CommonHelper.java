package com.example.utils;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.content.Intent;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.AlignmentSpan;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.MainActivity;
import com.example.NotificationActivity;
import com.example.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

    public static void centerAndIntalicEditTextHint(Activity from, String text, int elementId) {
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new StyleSpan(Typeface.ITALIC), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        EditText editText = from.findViewById(elementId);
        editText.setHint(spannableString);
    }

    public static void setClickableTextFromString(Activity from, char starChar, int textViewID, String text, LambaExpr expr) {
        TextView textView = from.findViewById(textViewID);
        SpannableString spannableString = new SpannableString(text);

        int start = -1;
        int end;
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                expr.exec();
            }
        };

        StyleSpan styleSpan = new StyleSpan(Typeface.BOLD | Typeface.ITALIC);
        StyleSpan colorSpan = new StyleSpan(R.color.blue);

        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == starChar) {
                if (start > -1) {
                    end = i;
                    spannableString.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannableString.setSpan(styleSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannableString.setSpan(colorSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }

                start = i + 1;
            }
        }

        if (start > -1) {
            end = text.length();
            spannableString.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(styleSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(colorSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(spannableString);
    }

    public static boolean isFireBaseUserConnected() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return user != null;
    }

    public static String obfuscateToken(String token) {
        return Base64.encodeToString(token.getBytes(), Base64.NO_WRAP);
    }

    public static String getSessionToken(Activity from) {
        SharedPreferences preferences = from.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return preferences.getString("session_token", null);
    }

    public static void saveTokenToSharedPreferences(Activity from, String token) {
        SharedPreferences sharedPreferences = from.getSharedPreferences("user_data", from.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("firebase_token", token);
        editor.apply();
    }

    public static void signOutFirebase(Activity from) {
        from.getSharedPreferences("user_data", from.MODE_PRIVATE).edit().remove("firebase_token").apply();
        FirebaseAuth.getInstance().signOut();
    }

    public static void checkTokenAndReauthenticate(Activity from, Activity to) {
        SharedPreferences sharedPreferences = from.getSharedPreferences("user_data", from.MODE_PRIVATE);
        String token = sharedPreferences.getString("firebase_token", null);

        if (token != null) {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.signInWithCustomToken(token)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                changeActivity(from, to);
                            } else {
                                CommonHelper.makeNotification(from, from.getResources().getString(R.string.text_disconnected), "", R.drawable.baseline_warning_24, R.color.ruby, "Some data string passed here", "Some LONGtext for notification here");
                            }
                        }
                    });
        }
    }

    public static String shortenText(String text) {
        if (text == null) {
            return null;
        }

        if (text.length() <= 30) {
            if(text.length() <= 20) {
                return text;
            }
            return text.substring(0, 20)+"\n"+text.substring(21);
        }

        return text.substring(0, 20)+"\n"+text.substring(21,32)+"...";
    }

    public static void makeNotification(Activity from, String title, String descShort, int iconID, int colorID, String dataString, String descDetailled) {
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
                .setSmallIcon(iconID) // R.drawable.baseline_warning_24
                .setColor(from.getResources().getColor(colorID)) // R.color.ruby
                .setContentTitle(title)
                .setContentText(descShort)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        Intent intent = new Intent(from.getApplicationContext(), NotificationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("data", dataString);

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
                        descDetailled, importante);
                notificationChannel.setLightColor(Color.WHITE);
                notificationChannel.enableVibration(true);
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        notificationManager.notify(0, builder.build());
    }

    public static void addReturnBtnOnImg(Activity activity) {
        ImageView imageView;
        imageView = (ImageView) activity.findViewById(R.id.imageViewLogo);

        imageView.setOnClickListener(view -> {
            CommonHelper.changeActivity(activity, new MainActivity());
        });
    }

    public static void addReturnBtnOnImgWithLambda(Activity activity, LambaExpr expr) {
        ImageView imageView;
        imageView = (ImageView) activity.findViewById(R.id.imageViewLogo);

        imageView.setOnClickListener(view -> {
            expr.exec();
        });
    }
}
