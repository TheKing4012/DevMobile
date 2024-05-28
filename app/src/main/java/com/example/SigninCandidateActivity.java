package com.example;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.utils.CommonHelper;
import com.example.utils.LambaExpr;

public class SigninCandidateActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_candidate);

        CommonHelper.changeActionbarColor(this, getResources().getColor(R.color.blue));

        CommonHelper.addReturnBtnOnImg(this);

        CommonHelper.centerAndIntalicEditTextHint(this, getString(R.string.text_name), R.id.EditTextNom);
        CommonHelper.centerAndIntalicEditTextHint(this, getString(R.string.text_firstname), R.id.EditTextPrenom);
        CommonHelper.centerAndIntalicEditTextHint(this, getString(R.string.text_email_adress), R.id.EditTextMail);
        CommonHelper.centerAndIntalicEditTextHint(this, getString(R.string.text_phone_number), R.id.EditTextTelephone);
        CommonHelper.centerAndIntalicEditTextHint(this, getString(R.string.text_birth_date), R.id.EditTextDateNaissance);
        CommonHelper.centerAndIntalicEditTextHint(this, getString(R.string.text_country), R.id.EditTextCountry);
        CommonHelper.centerAndIntalicEditTextHint(this, getString(R.string.text_city), R.id.EditTextCity);
        CommonHelper.centerAndIntalicEditTextHint(this, getString(R.string.text_password), R.id.EditTextPassword);
        CommonHelper.centerAndIntalicEditTextHint(this, getString(R.string.text_password), R.id.EditTextConfirmPassword);
        CommonHelper.centerAndIntalicEditTextHint(this, getString(R.string.text_description), R.id.EditTextDescription);

        LambaExpr exprSignIn = () -> {
            CommonHelper.changeActivity(this, new SigninCandidateActivity());
        };

        CommonHelper.setClickableTextFromString(this, '\n', R.id.textViewSignin, getString(R.string.text_signin_hint), exprSignIn);
    }
}