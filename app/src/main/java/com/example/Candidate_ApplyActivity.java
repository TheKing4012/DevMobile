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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class Candidate_ApplyActivity extends Activity {

    Button applyBtn;
    Button returnBtn;

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
                // Créer un Intent pour démarrer la nouvelle activité
                Intent intent = new Intent(Candidate_ApplyActivity.this, Candidate_SeeOfferActivity.class);
                startActivity(intent); // Démarrer la nouvelle activité
            }
        });

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
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

        if (requestCode == REQUEST_CODE_PICK_PDF && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri pdfUri = data.getData();
            uploadPDFToStorage(pdfUri);
        }
    }

    private void uploadPDFToStorage(Uri pdfUri) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference pdfRef = storageRef.child("pdfs/" + userId + "/" + System.currentTimeMillis() + ".pdf");

        pdfRef.putFile(pdfUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pdfRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String pdfUrl = uri.toString();
                                saveUserDataWithPDF(pdfUrl);
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Candidate_ApplyActivity.this, "Failed to upload PDF: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void checkStoragePermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
            } else {
                Toast.makeText(this, "Storage permission is required to select PDF", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void saveUserDataWithPDF(String pdfUrl) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String name = ((EditText) findViewById(R.id.EditTextNom)).getText().toString();
        String surname = ((EditText) findViewById(R.id.EditTextPrenom)).getText().toString();
        String dateOfBirth = ((EditText) findViewById(R.id.EditTextDateNaissance)).getText().toString();
        String phone = ((EditText) findViewById(R.id.EditTextTelephone)).getText().toString();
        String country = ((EditText) findViewById(R.id.EditTextCountry)).getText().toString();
        String city = ((EditText) findViewById(R.id.EditTextCity)).getText().toString();
        String website = ((EditText) findViewById(R.id.EditTextWebsite)).getText().toString();
        String desc = ((EditText) findViewById(R.id.EditTextDescription)).getText().toString();

        registerCandidate(userId, name, surname, dateOfBirth, phone, country, city, website, desc, pdfUrl);
    }

    private void registerCandidate(String userId, String name, String surname, String dateOfBirth, String phone, String country, String city, String website, String desc, String pdfUrl) {
        Map<String, Object> candidateData = new HashMap<>();
        candidateData.put("name", name);
        candidateData.put("surname", surname);
        candidateData.put("dateOfBirth", dateOfBirth);
        candidateData.put("phone", phone);
        candidateData.put("website", website);
        candidateData.put("country", country);
        candidateData.put("city", city);
        candidateData.put("description", desc);
        if(pdfUrl != null) {
            candidateData.put("pdfUrl", pdfUrl);
        }

        DatabaseReference candidatesRef = database.child("users").child("candidates");
        candidatesRef.child(userId).setValue(candidateData);
    }
}