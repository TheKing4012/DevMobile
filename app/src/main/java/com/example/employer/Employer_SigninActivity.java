package com.example.employer;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Employer_SigninActivity extends Activity {

    final String facebookSearchURL = "https://www.facebook.com/search/top/?q=";
    final String linkedInSearchURL = "https://www.linkedin.com/search/results/all/?keywords=";

    EditText editTextFacebook;
    EditText editTextLinkedIn;

    Button signInBtn;

    private FirebaseAuth mAuth;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_signin);
        signInBtn = findViewById(R.id.button_send);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        Activity activity = this;

        LambaExpr signinEmployer = () -> {
            String email = ((EditText) (findViewById(R.id.EditTextMail))).getText().toString();
            String password = ((EditText) (findViewById(R.id.EditTextPassword))).getText().toString();
            String companyName = ((EditText) (findViewById(R.id.EditTextEnterprise))).getText().toString();
            String phone = ((EditText) (findViewById(R.id.EditTextTelephone))).getText().toString();
            String country = ((EditText) (findViewById(R.id.EditTextCountry))).getText().toString();
            String city = ((EditText) (findViewById(R.id.EditTextCity))).getText().toString();
            String website = ((EditText) (findViewById(R.id.EditTextWebsite))).getText().toString();
            String facebook = ((EditText) (findViewById(R.id.EditTextFacebook))).getText().toString();
            String linkedin = ((EditText) (findViewById(R.id.EditTextLinkedIn))).getText().toString();

            if (!email.isEmpty() && !password.isEmpty() && !companyName.isEmpty()) {
                if (password.length() < 6) {
                    CommonHelper.makeNotification(this, getString(R.string.text_error), getString(R.string.text_error_signin_password), R.drawable.baseline_warning_24, R.color.ruby, "Some data string passed here", "Some LONGtext for notification here");

                } else {
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                    registerEmployer(activity, userId, phone, companyName, country, city, website, facebook, linkedin);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    if (e.getMessage().equalsIgnoreCase("The email address is already in use by another account.")) {
                                        CommonHelper.makeNotification(activity, getString(R.string.text_error), getString(R.string.text_error_mail_already_used), R.drawable.baseline_warning_24, R.color.ruby, "Some data string passed here", "Some LONGtext for notification here");
                                    } else if (e != null) {
                                        Toast.makeText(activity, "Employer registration failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(activity, "Employer registration failed. Please try again.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })

                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    FirebaseUser user = authResult.getUser();
                                    String uid = user.getUid();
                                    String mail = user.getEmail();
                                    final String[] companyName = {""};
                                    DatabaseReference employersRef = FirebaseDatabase.getInstance().getReference("users/employers/" + uid);

                                    employersRef.get()
                                            .addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                                                @Override
                                                public void onSuccess(@NonNull DataSnapshot dataSnapshot) {
                                                    if (dataSnapshot.exists()) {
                                                        Map<String, Object> userData = (Map<String, Object>) dataSnapshot.getValue();
                                                        companyName[0] = userData.get("companyName").toString();
                                                    }
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w("TAG", "Échec de la lecture des données utilisateur.", e);
                                                }
                                            });
                                    SharedPreferences preferences = getSharedPreferences("user_data", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("uid", uid);
                                    editor.putString("mail", mail);
                                    editor.putString("companyName", companyName[0]);
                                    editor.apply();

                                    FirebaseAuth mAuth = FirebaseAuth.getInstance();

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
                                            });

                                    CommonHelper.changeActivity(activity, new Employer_LoginActivity());
                                    finish();
                                }
                            });

                }
            } else {
                CommonHelper.makeNotification(this, getString(R.string.text_error), getString(R.string.text_error_signin_employer), R.drawable.baseline_warning_24, R.color.ruby, "Some data string passed here", "Some LONGtext for notification here");
            }
        };

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signinEmployer.exec();
            }
        });

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

        LambaExpr exprLogIn = () -> {
            CommonHelper.changeActivity(this, new Employer_LoginActivity());
        };

        CommonHelper.setClickableTextFromString(this, '\n', R.id.textViewSignin, getString(R.string.text_signin_hint), exprLogIn);
    }

    public void openLink(EditText editTextFacebook, EditText editTextLinkedIn, View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String url = "";
        if (view.getTag() == "facebook") {
            String textFacebook = editTextFacebook.getText().toString();
            if (!textFacebook.isEmpty()) {
                url = textFacebook;
            } else {
                url = facebookSearchURL;
            }
        } else {
            String textLinkedIn = editTextLinkedIn.getText().toString();
            if (!textLinkedIn.isEmpty()) {
                url = textLinkedIn;
            } else {
                url = linkedInSearchURL;
            }
        }
        intent.setData(Uri.parse(url));

        startActivity(intent);
    }

    private void registerEmployer(Activity activity, String userId, String phone, String companyName, String country, String city, String website, String facebook, String linkedin) {
        Map<String, Object> employerData = new HashMap<>();
        employerData.put("companyName", companyName);
        employerData.put("phone", phone);
        employerData.put("website", website);
        employerData.put("country", country);
        employerData.put("city", city);
        employerData.put("facebook", facebook);
        employerData.put("linkedin", linkedin);

        DatabaseReference employersRef = database.child("users").child("employers");
        employersRef.child(userId).setValue(employerData);
    }
}