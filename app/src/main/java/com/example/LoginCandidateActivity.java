package com.example;


import static android.content.ContentValues.TAG;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AlignmentSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.utils.CommonHelper;
import com.example.utils.LambaExpr;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginCandidateActivity extends Activity {

    Button btnSend;
    EditText entrepriseEditText, motDePasseEditText, prenomEditText;
    ImageView imageView;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_candidate);

        CommonHelper.changeActionbarColor(this, getResources().getColor(R.color.blue));

        CommonHelper.addReturnBtnOnImg(this);

        CommonHelper.centerAndIntalicEditTextHint(this, getString(R.string.text_email_adress), R.id.EditTextMail);
        CommonHelper.centerAndIntalicEditTextHint(this, getString(R.string.text_password), R.id.EditTextPassword);

        LambaExpr exprLoginIn = () -> {
            CommonHelper.changeActivity(this, new SigninCandidateActivity());
        };

        CommonHelper.setClickableTextFromString(this, '\n', R.id.textViewSignin, getString(R.string.text_login_hint), exprLoginIn);


        btnSend = findViewById(R.id.button_send);

        mAuth = FirebaseAuth.getInstance();
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;
                email = String.valueOf(entrepriseEditText.getText().toString());
                password = String.valueOf(motDePasseEditText.getText().toString());

                if(TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Account Created.",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(getApplicationContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}