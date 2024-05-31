package com.example.employer;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.R;
import com.example.utils.helpers.CommonHelper;
import com.example.utils.LambaExpr;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

public class Employer_LoginActivity extends Activity {

    Button signInBtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (CommonHelper.isFireBaseUserConnected()) {
            CommonHelper.changeActivity(this, new Employer_MyOffersActivity());
        } else {
            setContentView(R.layout.activity_employer_login);

            CommonHelper.changeActionbarColor(this, getResources().getColor(R.color.blue));

            CommonHelper.addReturnBtnOnImg(this);

            CommonHelper.centerAndIntalicEditTextHint(this, getString(R.string.text_email_adress), R.id.EditTextEnterprise);
            CommonHelper.centerAndIntalicEditTextHint(this, getString(R.string.text_password), R.id.EditTextPassword);

            LambaExpr exprSignin = () -> {
                CommonHelper.changeActivity(this, new Employer_SigninActivity());
            };

            CommonHelper.setClickableTextFromString(this, '\n', R.id.textViewSignin, getString(R.string.text_login_hint), exprSignin);

            mAuth = FirebaseAuth.getInstance();
            signInBtn = findViewById(R.id.button_send);

            LambaExpr exprLoginIn = () -> {
                String email = ((EditText) (findViewById(R.id.EditTextEnterprise))).getText().toString();
                String password = ((EditText) (findViewById(R.id.EditTextPassword))).getText().toString();

                if (email.isEmpty()) {
                    CommonHelper.makeNotification(this, getString(R.string.text_error), getString(R.string.text_error_login_employer_mail), R.drawable.baseline_warning_24, R.color.ruby, "Some data string passed here", "Some LONGtext for notification here");
                    return;
                }
                if(password.isEmpty()) {
                    CommonHelper.makeNotification(this, getString(R.string.text_error), getString(R.string.text_error_login_employer_password), R.drawable.baseline_warning_24, R.color.ruby, "Some data string passed here", "Some LONGtext for notification here");
                    return;
                }

                Activity activity = this;

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Connexion réussie
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if (user != null) {
                                        user.getIdToken(true)
                                                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                                                        if (task.isSuccessful()) {
                                                            String token = task.getResult().getToken();
                                                            // Stocker le token dans SharedPreferences
                                                            CommonHelper.saveTokenToSharedPreferences(activity, token);
                                                        }
                                                    }
                                                });
                                    }
                                } else {
                                    // Gérer les erreurs de connexion
                                }
                            }
                        })
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                CommonHelper.changeActivity(activity, new Employer_LoginActivity());
                                finish();
                            }
                        })

                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                CommonHelper.makeNotification(activity, getString(R.string.text_error), e.getMessage(), R.drawable.baseline_warning_24, R.color.ruby, "Some data string passed here", "Some LONGtext for notification here");

                            }
                        });
            };

            signInBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    exprLoginIn.exec();
                }
            });
        }
    }
}