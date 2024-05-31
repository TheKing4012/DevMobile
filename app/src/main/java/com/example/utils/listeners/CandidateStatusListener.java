package com.example.utils.listeners;

import com.google.firebase.database.DatabaseError;

public interface CandidateStatusListener {
    void onStatusRetrieved(String status);
    void onCancelled(DatabaseError databaseError);
}

