package com.example;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.utils.CommonHelper;
import com.example.utils.LambaExpr;
import com.example.utils.Offer;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class Candidate_ApplyActivity extends Activity {

    Button applyBtn;
    Button returnBtn;

    String pdfUrl = "";

    private FirebaseAuth mAuth;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_apply);

        CommonHelper.changeActionbarColor(this, getResources().getColor(R.color.blue));

        CommonHelper.addReturnBtnOnImg(this);

        CommonHelper.centerAndIntalicEditTextHint(this, getString(R.string.text_name), R.id.EditTextNom);
        CommonHelper.centerAndIntalicEditTextHint(this, getString(R.string.text_firstname), R.id.EditTextPrenom);
        CommonHelper.centerAndIntalicEditTextHint(this, getString(R.string.text_birth_date), R.id.EditTextDateNaissance);
        CommonHelper.centerAndIntalicEditTextHint(this, getString(R.string.text_country), R.id.EditTextCountry);
        CommonHelper.centerAndIntalicEditTextHint(this, getString(R.string.text_description), R.id.EditTextDescription);

        ImageView imageViewCV = findViewById(R.id.button_CV);

        imageViewCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickPDFFromStorage();
            }
        });

        applyBtn = findViewById(R.id.button_apply);
        returnBtn = findViewById(R.id.button_return);

        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Candidate_ApplyActivity.this, Candidate_SeeOfferActivity.class);
                startActivity(intent);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        Offer offer = null;
        Activity activity = this;
        if (intent != null) {
            offer = intent.getParcelableExtra("offer");
            if (offer != null) {
                TextView textViewTitle = findViewById(R.id.TextViewTitle);
                textViewTitle.setText(offer.getTitle());

                Button btnApply = findViewById(R.id.button_apply);

                Offer finalOffer = offer;
                btnApply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveUserDataWithPDF(finalOffer);
                    }
                });
            }
        }
    }

    private static final int REQUEST_CODE_PICK_PDF = 101;

    private void pickPDFFromStorage() {
        // Vérifier d'abord les autorisations
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Si les autorisations ne sont pas accordées, demander à l'utilisateur
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
        } else {
            // Les autorisations sont déjà accordées, donc sélectionner le PDF
            startPickPDFIntent();
        }
    }

    private void startPickPDFIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        startActivityForResult(Intent.createChooser(intent, "Sélectionner un fichier PDF"), REQUEST_CODE_PICK_PDF);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PICK_PDF && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri pdfUri = data.getData();
            uploadPDFToStorage(pdfUri);
        }
    }

    private void uploadPDFToStorage(Uri pdfUri) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        String userId = currentUser.getUid();
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference pdfRef = storageRef.child("pdfs/" + userId + "/" + System.currentTimeMillis() + ".pdf");

        pdfRef.putFile(pdfUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pdfRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                pdfUrl = uri.toString();
                                CommonHelper.makeNotification(Candidate_ApplyActivity.this, getString(R.string.text_cv_upload_successs), getString(R.string.text_cv), R.drawable.baseline_warning_24, R.color.ruby, "Some data string passed here", "Some LONGtext for notification here");
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        CommonHelper.makeNotification(Candidate_ApplyActivity.this, getString(R.string.text_error), e.getMessage(), R.drawable.baseline_warning_24, R.color.ruby, "Some data string passed here", "Some LONGtext for notification here");
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {
            startPickPDFIntent();
        }
    }

    private void saveUserDataWithPDF(Offer offer) {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        String userId = currentUser.getUid();
        String name = ((EditText) findViewById(R.id.EditTextNom)).getText().toString();
        String surname = ((EditText) findViewById(R.id.EditTextPrenom)).getText().toString();
        String dateOfBirth = ((EditText) findViewById(R.id.EditTextDateNaissance)).getText().toString();
        String country = ((EditText) findViewById(R.id.EditTextCountry)).getText().toString();
        String desc = ((EditText) findViewById(R.id.EditTextDescription)).getText().toString();


        Map<String, Object> candidateData = new HashMap<>();
        candidateData.put("name", name);
        candidateData.put("surname", surname);
        candidateData.put("dateOfBirth", dateOfBirth);
        candidateData.put("country", country);
        candidateData.put("description", desc);
        candidateData.put("pdfUrl", pdfUrl);
        candidateData.put("status", "Pending");

        DatabaseReference offerRef = database.child("offers").child(offer.getOfferId());

        offerRef.child("candidates").child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot userCandidateSnapshot = task.getResult();
                    if (userCandidateSnapshot.exists()) {
                        CommonHelper.makeNotification(Candidate_ApplyActivity.this, getString(R.string.text_error), getString(R.string.text_error_already_apply), R.drawable.baseline_warning_24, R.color.ruby, "Some data string passed here", "Some LONGtext for notification here");
                        CommonHelper.changeActivity(Candidate_ApplyActivity.this, new Candidate_ListOffersActivity());
                    } else {
                        addCandidateToOffer(offerRef, userId, candidateData);
                    }
                } else {
                    CommonHelper.makeNotification(Candidate_ApplyActivity.this, getString(R.string.text_error), task.getException().getMessage(), R.drawable.baseline_warning_24, R.color.ruby, "Some data string passed here", "Some LONGtext for notification here");
                }
            }
        });
    }

    private void addCandidateToOffer(DatabaseReference offerRef, String userId, Map<String, Object> candidateData) {
        offerRef.child("candidates").child(userId).setValue(candidateData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        CommonHelper.makeNotification(Candidate_ApplyActivity.this, getString(R.string.text_apply_successs), "", R.drawable.baseline_warning_24, R.color.ruby, "Some data string passed here", "Some LONGtext for notification here");
                        CommonHelper.changeActivity(Candidate_ApplyActivity.this, new Candidate_ListOffersActivity());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        CommonHelper.makeNotification(Candidate_ApplyActivity.this, getString(R.string.text_error), e.getMessage(), R.drawable.baseline_warning_24, R.color.ruby, "Some data string passed here", "Some LONGtext for notification here");
                    }
                });
    }

}