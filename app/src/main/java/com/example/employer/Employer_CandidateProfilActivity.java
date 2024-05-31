package com.example.employer;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.R;
import com.example.candidate.Candidate_LoginActivity;
import com.example.utils.entities.Candidate;
import com.example.utils.helpers.CommonHelper;
import com.example.utils.LambaExpr;
import com.example.utils.listeners.DownloadCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

public class Employer_CandidateProfilActivity extends Activity {

    Button returnBtn;
    TextView textViewPrenom;
    TextView textViewNom;
    TextView textViewDateNaissance;
    TextView textViewCountry;
    TextView textViewCity;
    TextView textViewDescription;

    private FirebaseAuth mAuth;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_candidateprofil);
        CommonHelper.changeActionbarColor(this, getResources().getColor(R.color.blue));
        CommonHelper.addReturnBtnOnImg(this);

        LambaExpr exprLogIn = () -> {
            CommonHelper.changeActivity(this, new Candidate_LoginActivity());
        };

        textViewPrenom = findViewById(R.id.TextViewPrenom);
        textViewNom = findViewById(R.id.TextViewNom);
        textViewDateNaissance = findViewById(R.id.TextViewDateNaissance);
        textViewCountry = findViewById(R.id.TextViewCountry);
        textViewCity = findViewById(R.id.TextViewCity);
        textViewDescription = findViewById(R.id.TextViewDescription);

        String pdfUrl = "";

        Candidate candidate = getIntent().getParcelableExtra("candidate");
        textViewPrenom.setText(candidate.getName());
        textViewNom.setText(candidate.getSurname());
        textViewCountry.setText(candidate.getCountry());
        textViewCity.setText(candidate.getCity());
        textViewDateNaissance.setText(candidate.getDateOfBirth());
        textViewDescription.setText(candidate.getDescription());
        pdfUrl = candidate.getPdfUrl();

        ImageView btnDownload = findViewById(R.id.button_CV);

        String finalPdfUrl = pdfUrl;
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileName = "CV_" + candidate.getName() + "_" + candidate.getSurname();
                File localFile = new File(getExternalFilesDir(null), fileName + ".pdf");

                downloadPDFFromFirebase(finalPdfUrl, localFile, new DownloadCompleteListener() {
                    @Override
                    public void onDownloadComplete(File localFile) {
                        openPDFFile(localFile);
                    }

                    @Override
                    public void onDownloadFailed(String errorMessage) {
                        CommonHelper.makeNotification(Employer_CandidateProfilActivity.this, getString(R.string.text_error), errorMessage, R.drawable.baseline_warning_24, R.color.ruby, "Some data string passed here", "Some LONGtext for notification here");
                    }
                });
            }
        });

        Button buttonRetour = findViewById(R.id.button_return);

        buttonRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer un Intent pour démarrer la nouvelle activité
                Intent intent = new Intent(Employer_CandidateProfilActivity.this, Employer_MyOffersActivity.class);
                startActivity(intent); // Démarrer la nouvelle activité
            }
        });

        ImageView btnPhone = findViewById(R.id.image_phone);

        btnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiatePhoneCall(candidate.getPhoneNumber()); // Assurez-vous que getPhoneNumber() retourne le bon numéro
            }
        });

        ImageView btnSMS = findViewById(R.id.image_sms);

        btnSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSMS(candidate.getPhoneNumber()); // Assurez-vous que getPhoneNumber() retourne le bon numéro
            }
        });

        ImageView btnMail = findViewById(R.id.image_email);

        btnMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactCandidateByEmail(candidate.getEmail()); // Assurez-vous que getPhoneNumber() retourne le bon numéro
            }
        });
    }

    public void initiatePhoneCall(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        if (phoneNumber == null) {
            CommonHelper.makeNotification(Employer_CandidateProfilActivity.this, getString(R.string.text_error), getString(R.string.text_error_no_phone), R.drawable.baseline_warning_24, R.color.ruby, "Some data string passed here", "Some LONGtext for notification here");
            return;
        }
        intent.setData(Uri.parse("tel:" + phoneNumber));

        startActivity(intent);
    }

    public void sendSMS(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto:" + phoneNumber));
        intent.putExtra("sms_body", ""); // Vous pouvez pré-remplir le corps du SMS ici si vous le souhaitez

        if (phoneNumber == null) {
            CommonHelper.makeNotification(Employer_CandidateProfilActivity.this, getString(R.string.text_error), getString(R.string.text_error_no_phone), R.drawable.baseline_warning_24, R.color.ruby, "Some data string passed here", "Some LONGtext for notification here");
            return;
        }

        startActivity(intent);
    }

    public void downloadPDFFromFirebase(String pdfUrl, final File localFile, final DownloadCompleteListener listener) {
        StorageReference pdfRef = FirebaseStorage.getInstance().getReferenceFromUrl(pdfUrl);

        pdfRef.getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        // Le fichier PDF a été téléchargé avec succès
                        if (listener != null) {
                            listener.onDownloadComplete(localFile);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Gestion des erreurs lors du téléchargement du fichier
                        if (listener != null) {
                            listener.onDownloadFailed(exception.getMessage());
                        }
                    }
                });
    }

    private void contactCandidateByEmail(String email) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + email));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Votre sujet ici");
        intent.putExtra(Intent.EXTRA_TEXT, "Votre message ici");
        startActivity(intent);
    }

    private void openPDFFile(File pdfFile) {
        // Ouvrir le fichier PDF dans une application PDF externe
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", pdfFile);
        intent.setDataAndType(uri, "application/pdf");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
    }
}
