package com.example;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.AlignmentSpan;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.utils.CommonHelper;
import com.example.utils.LambaExpr;

public class SigninEmployerActivity extends Activity {

    final String facebookSearchURL = "https://www.facebook.com/search/top/?q=";
    final String linkedInSearchURL = "https://www.linkedin.com/search/results/all/?keywords=";

    EditText editTextFacebook;
    EditText editTextLinkedIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_employer);

        CommonHelper.changeActionbarColor(this, getResources().getColor(R.color.blue));

        CommonHelper.addReturnBtnOnImg(this);

        CommonHelper.centerAndIntalicEditTextHint(this, getString(R.string.text_company_name), R.id.EditTextEnterprise);
        CommonHelper.centerAndIntalicEditTextHint(this, getString(R.string.text_email_adress), R.id.EditTextMail);
        CommonHelper.centerAndIntalicEditTextHint(this, getString(R.string.text_phone_number), R.id.EditTextTelephone);
        CommonHelper.centerAndIntalicEditTextHint(this, getString(R.string.text_country), R.id.EditTextCountry);
        CommonHelper.centerAndIntalicEditTextHint(this, getString(R.string.text_city), R.id.EditTextCity);
        CommonHelper.centerAndIntalicEditTextHint(this, getString(R.string.text_website), R.id.EditTextWebsite);
        CommonHelper.centerAndIntalicEditTextHint(this, getString(R.string.text_facebook), R.id.EditTextFacebook);
        CommonHelper.centerAndIntalicEditTextHint(this, getString(R.string.text_linkedin), R.id.EditTextLinkedIn);
        CommonHelper.centerAndIntalicEditTextHint(this, getString(R.string.text_password), R.id.EditTextPassword);
        CommonHelper.centerAndIntalicEditTextHint(this, getString(R.string.text_password), R.id.EditTextConfirmPassword);

        editTextFacebook = findViewById(R.id.EditTextFacebook);
        editTextLinkedIn = findViewById(R.id.EditTextLinkedIn);
        ImageView imageViewFacebook = findViewById(R.id.imageViewFacebookLogo);
        ImageView imageViewLinkedIn = findViewById(R.id.imageViewLinkedInLogo);
        imageViewFacebook.setTag("facebook");
        imageViewLinkedIn.setTag("linkedIn");

        imageViewFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink(editTextFacebook, editTextLinkedIn, v);
            }
        });

        imageViewLinkedIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink(editTextFacebook, editTextLinkedIn, v);
            }
        });

        LambaExpr exprSignIn = () -> {
            CommonHelper.changeActivity(this, new SigninEmployerActivity());
        };

        CommonHelper.setClickableTextFromString(this, '\n', R.id.textViewSignin, getString(R.string.text_signin_hint), exprSignIn);
    }

    public void openLink(EditText editTextFacebook, EditText editTextLinkedIn, View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String url = "";
        if(view.getTag() == "facebook") {
            String textFacebook = editTextFacebook.getText().toString();
            if(!textFacebook.isEmpty()) {
                url = textFacebook;
            } else {
                url = facebookSearchURL;
            }
        } else {
            String textLinkedIn = editTextLinkedIn.getText().toString();
            if(!textLinkedIn.isEmpty()) {
                url = textLinkedIn;
            } else {
                url = linkedInSearchURL;
            }
        }
        intent.setData(Uri.parse(url));

        startActivity(intent);
    }
}