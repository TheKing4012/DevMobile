package com.example.utils.helpers;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseHelper {

    public interface EmailFetchListener {
        void onEmailFetched(String email);
        void onError(String errorMessage);
    }

    public static void fetchEmailFromUserID(String userId, EmailFetchListener listener) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        // Récupérer l'utilisateur actuellement connecté
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            if (email != null) {
                listener.onEmailFetched(email);
            } else {
                listener.onError("Email not found for current user");
            }
        } else {
            listener.onError("No authenticated user found");
        }
    }
}
