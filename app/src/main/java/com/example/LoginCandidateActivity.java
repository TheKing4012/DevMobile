package com.example;


import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AlignmentSpan;
import android.text.style.StyleSpan;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.utils.CommonHelper;

public class LoginCandidateActivity extends Activity {

    Button btnAnnonyme, btnInterimaire, btnEmployeur;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_candidate);

        CommonHelper.changeActionbarColor(this, getResources().getColor(R.color.blue));

        CommonHelper.addReturnBtnOnImg(this);

        // Créer un SpannableString pour le premier hint ("Nom de l'entreprise")
        SpannableString spannableStringEntreprise = new SpannableString("Nom");
        spannableStringEntreprise.setSpan(new StyleSpan(Typeface.ITALIC), 0, spannableStringEntreprise.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringEntreprise.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, spannableStringEntreprise.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Appliquer le SpannableString au premier EditText
        EditText entrepriseEditText = findViewById(R.id.EditTextName);
        entrepriseEditText.setHint(spannableStringEntreprise);

        // Créer un SpannableString pour le deuxième hint ("Mot de Passe")
        SpannableString spannableStringMotDePasse = new SpannableString("Mot de passe");
        spannableStringMotDePasse.setSpan(new StyleSpan(Typeface.ITALIC), 0, spannableStringMotDePasse.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringMotDePasse.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, spannableStringMotDePasse.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Appliquer le SpannableString au deuxième EditText
        EditText motDePasseEditText = findViewById(R.id.EditTextPassword);
        motDePasseEditText.setHint(spannableStringMotDePasse);

        // Créer un SpannableString pour le deuxième hint ("Mot de Passe")
        SpannableString spannableStringPrenom = new SpannableString("Prenom");
        spannableStringPrenom.setSpan(new StyleSpan(Typeface.ITALIC), 0, spannableStringPrenom.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringPrenom.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, spannableStringPrenom.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Appliquer le SpannableString au deuxième EditText
        EditText prenomEditText = findViewById(R.id.EditTextSurname);
        prenomEditText.setHint(spannableStringPrenom);


    }
}