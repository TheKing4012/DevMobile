package com.example;


import android.app.Activity;
import android.graphics.Typeface;
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
import android.widget.TextView;

import com.example.utils.CommonHelper;
import com.example.utils.LambaExpr;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginEmployerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(CommonHelper.isFireBaseUserConnected()) {
            //TODO changeActivity() to listJobs
        } else {
            setContentView(R.layout.activity_login_employer);

            CommonHelper.changeActionbarColor(this, getResources().getColor(R.color.blue));

            CommonHelper.addReturnBtnOnImg(this);

            CommonHelper.centerAndIntalicEditTextHint(this, getString(R.string.text_email_adress), R.id.EditTextEnterprise);
            CommonHelper.centerAndIntalicEditTextHint(this, getString(R.string.text_password), R.id.EditTextPassword);

            LambaExpr exprLoginIn = () -> {
                CommonHelper.changeActivity(this, new SigninEmployerActivity());
            };

            CommonHelper.setClickableTextFromString(this, '\n', R.id.textViewSignin, getString(R.string.text_login_hint), exprLoginIn);

        }
    }
}