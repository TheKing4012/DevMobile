package com.example.utils.listeners;

import com.example.utils.entities.Candidate;
import com.google.firebase.database.DatabaseError;

import java.util.List;

public interface FilteredCandidatesListener {
    void onFilteredCandidates(List<Candidate> candidates);
    void onCancelled(DatabaseError databaseError);
}