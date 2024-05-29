package com.example.utils;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class OfferHelper {

    private DatabaseReference databaseReference;

    public OfferHelper() {
        databaseReference = FirebaseDatabase.getInstance().getReference("offers");
    }
    
    public void getLast100Offers(ValueEventListener listener) {
        Query query = databaseReference.orderByKey().limitToLast(100);
        query.addListenerForSingleValueEvent(listener);
    }

    public void getOffersByCriteria(String status, String zone, long startPeriod, long endPeriod, ValueEventListener listener) {
        Query query = databaseReference
                .orderByChild("status")
                .equalTo(status)
                .limitToFirst(100);

        query.addListenerForSingleValueEvent(listener);
    }

    public void addOffer(String offerId, Map<String, Object> offerData, DatabaseReference.CompletionListener listener) {
        databaseReference.child(offerId).setValue(offerData, listener);
    }

    public void removeOffer(String offerId, DatabaseReference.CompletionListener listener) {
        databaseReference.child(offerId).removeValue(listener);
    }

    public void addCandidateToOffer(String offerId, String candidateId, DatabaseReference.CompletionListener listener) {
        databaseReference.child(offerId).child("candidates").child(candidateId).setValue(true, listener);
    }
}
