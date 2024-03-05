package com.example.utils;

import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.tp1.R;

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

    public static void changeActivity(Activity from, Activity where) {
        Intent intent = new Intent(from, where.getClass());
        from.startActivity(intent);
    }
}
