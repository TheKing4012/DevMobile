package com.example;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.utils.CommonHelper;
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

public class SigninCandidateActivity extends Activity {

    Button signInBtn;

    private FirebaseAuth mAuth;
    private DatabaseReference database;

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

        LambaExpr exprLogIn = () -> {
            CommonHelper.changeActivity(this, new LoginCandidateActivity());
        };

        CommonHelper.setClickableTextFromString(this, '\n', R.id.textViewSignin, getString(R.string.text_signin_hint), exprLogIn);

        ImageView imageViewCV = findViewById(R.id.button_CV);

        imageViewCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickPDFFromStorage();
            }
        });

        signInBtn = findViewById(R.id.button_send);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        Activity activity = this;

        LambaExpr signinCandidate = () -> {
            String email = ((EditText) (findViewById(R.id.EditTextMail))).getText().toString();
            String password = ((EditText) (findViewById(R.id.EditTextPassword))).getText().toString();
            String passwordCheck = ((EditText) (findViewById(R.id.EditTextConfirmPassword))).getText().toString();
            String name = ((EditText) (findViewById(R.id.EditTextNom))).getText().toString();
            String surname = ((EditText) (findViewById(R.id.EditTextPrenom))).getText().toString();
            String dateofbirth = ((EditText) (findViewById(R.id.EditTextDateNaissance))).getText().toString();
            String phone = ((EditText) (findViewById(R.id.EditTextTelephone))).getText().toString();
            String country = ((EditText) (findViewById(R.id.EditTextCountry))).getText().toString();
            String city = ((EditText) (findViewById(R.id.EditTextCity))).getText().toString();
            String website = ((EditText) (findViewById(R.id.EditTextWebsite))).getText().toString();
            String desc = ((EditText) (findViewById(R.id.EditTextDescription))).getText().toString();

            if (!email.isEmpty() && !password.isEmpty()) {
                if (password.length() < 6) {
                    CommonHelper.makeNotification(this, getString(R.string.text_error), getString(R.string.text_error_signin_password), R.drawable.baseline_warning_24, R.color.ruby, "Some data string passed here", "Some LONGtext for notification here");

                } else {

                    if(!password.equals(passwordCheck)) {
                        CommonHelper.makeNotification(this, getString(R.string.text_error), getString(R.string.text_error_not_same_password), R.drawable.baseline_warning_24, R.color.ruby, "Some data string passed here", "Some LONGtext for notification here");
                        return;
                    }

                    if(name.isEmpty()) {
                        CommonHelper.makeNotification(this, getString(R.string.text_error), getString(R.string.text_error_signin_candidate).replaceAll("element", getString(R.string.text_name)), R.drawable.baseline_warning_24, R.color.ruby, "Some data string passed here", "Some LONGtext for notification here");
                        return;
                    }
                    if(surname.isEmpty()) {
                        CommonHelper.makeNotification(this, getString(R.string.text_error), getString(R.string.text_error_signin_candidate).replaceAll("element", getString(R.string.text_firstname)), R.drawable.baseline_warning_24, R.color.ruby, "Some data string passed here", "Some LONGtext for notification here");
                        return;
                    }
                    if(desc.isEmpty()) {
                        CommonHelper.makeNotification(this, getString(R.string.text_error), getString(R.string.text_error_signin_candidate).replaceAll("element", getString(R.string.text_description)), R.drawable.baseline_warning_24, R.color.ruby, "Some data string passed here", "Some LONGtext for notification here");
                        return;
                    }
                    if(dateofbirth.isEmpty()) {
                        CommonHelper.makeNotification(this, getString(R.string.text_error), getString(R.string.text_error_signin_candidate).replaceAll("element", getString(R.string.text_birth_date)), R.drawable.baseline_warning_24, R.color.ruby, "Some data string passed here", "Some LONGtext for notification here");
                        return;
                    }

                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                    registerCandidate(userId, name, surname, dateofbirth, phone, country, city, website, desc);
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

                                    CommonHelper.changeActivity(activity, new LoginEmployerActivity());
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
                signinCandidate.exec();
            }
        });
    }

    private static final int REQUEST_CODE_PICK_PDF = 101;

    private void pickPDFFromStorage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        checkStoragePermissions();
        startActivityForResult(Intent.createChooser(intent, "Sélectionner un fichier PDF"), REQUEST_CODE_PICK_PDF);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PICK_PDF && resultCode == RESULT_OK && data != null) {
            Uri pdfUri = data.getData();
            if (pdfUri != null) {
                uploadPDFToFirebase(pdfUri);
            }
        }
    }

    private void uploadPDFToFirebase(Uri pdfUri) {
        // Nom du fichier dans Firebase Storage (peut être modifié selon vos besoins)
        String fileName = "mon_fichier.pdf";

        /*
        // Référence à l'emplacement de stockage dans Firebase Storage
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("pdfs/" + fileName);

        // Téléchargement du fichier PDF sélectionné vers Firebase Storage
        storageRef.putFile(pdfUri)
                .addOnSuccessListener(taskSnapshot -> {
                    // Succès du téléchargement
                    // Récupérer l'URL du fichier téléchargé
                    storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        String pdfUrl = uri.toString();
                        // Maintenant, vous pouvez utiliser pdfUrl pour enregistrer cette URL dans votre base de données Firebase
                        // Par exemple, sauvegarder l'URL dans Realtime Database ou Firestore
                        // firebaseDatabaseReference.child("pdfs").push().setValue(pdfUrl);
                    }).addOnFailureListener(e -> {
                        // Gestion des erreurs lors de la récupération de l'URL
                    });
                })
                .addOnFailureListener(e -> {
                    // Échec du téléchargement
                });

         */
    }

    private static final int REQUEST_STORAGE_PERMISSION = 101;

    // Vérifier et demander les permissions de stockage si nécessaire
    private void checkStoragePermissions() {
        // Vérifier la version d'Android
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Vérifier la permission READ_EXTERNAL_STORAGE
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // Demander la permission READ_EXTERNAL_STORAGE
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_STORAGE_PERMISSION);
            }

            // Vérifier la permission WRITE_EXTERNAL_STORAGE
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // Demander la permission WRITE_EXTERNAL_STORAGE
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_STORAGE_PERMISSION);
            }
        } else {
            // Pour les versions antérieures à Marshmallow, les permissions sont accordées à l'installation
            // Vous pouvez directement effectuer des actions qui nécessitent ces permissions ici
        }
    }

    private void registerCandidate(String userId, String name, String surname, String dateOfBirth, String phone, String country, String city, String website, String desc) {
        Map<String, Object> employerData = new HashMap<>();
        employerData.put("name", name);
        employerData.put("surname", surname);
        employerData.put("dateOfBirth", dateOfBirth);
        employerData.put("phone", phone);
        employerData.put("website", website);
        employerData.put("country", country);
        employerData.put("city", city);
        employerData.put("description", desc);

        DatabaseReference employersRef = database.child("users").child("candidates");
        employersRef.child(userId).setValue(employerData);
    }
}