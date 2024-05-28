package com.example;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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

        ImageView imageViewCV = findViewById(R.id.button_CV);

        imageViewCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickPDFFromStorage();
            }
        });

    }

    private static final int REQUEST_CODE_PICK_PDF = 101;

    private void pickPDFFromStorage() {
        // Créer une intention pour ouvrir l'explorateur de fichiers
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Démarrer une activité pour sélectionner un fichier PDF
        checkStoragePermissions();
        startActivityForResult(Intent.createChooser(intent, "Sélectionner un fichier PDF"), REQUEST_CODE_PICK_PDF);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PICK_PDF && resultCode == RESULT_OK && data != null) {
            Uri pdfUri = data.getData();
            if (pdfUri != null) {
                // Maintenant, vous pouvez utiliser pdfUri pour manipuler le fichier PDF sélectionné
                // Par exemple, télécharger ce fichier vers Firebase Storage
                uploadPDFToFirebase(pdfUri);
            }
        }
    }

    private void uploadPDFToFirebase(Uri pdfUri) {
        Toast.makeText(SigninCandidateActivity.this, "coucouu", Toast.LENGTH_LONG);
        /*
        // Nom du fichier dans Firebase Storage (peut être modifié selon vos besoins)
        String fileName = "mon_fichier.pdf";

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
}