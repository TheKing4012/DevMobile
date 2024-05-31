package com.example.utils.listeners;

import com.example.utils.entities.Offer;
import com.google.firebase.database.DatabaseError;

import java.util.List;

public interface FilteredOffersListener {
    void onFilteredOffers(List<Offer> offers);
    void onCancelled(DatabaseError databaseError);
}

