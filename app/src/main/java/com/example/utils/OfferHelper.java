package com.example.utils;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public void getLast100OffersWithCriteria(String zone, String period, String status, FilteredOffersListener listener) {
        databaseReference.orderByKey().limitToLast(100).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Offer> filteredOffers = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Offer offer = snapshot.getValue(Offer.class);
                    if (offer != null && offer.getZone().equals(zone)
                            && offer.getPeriod().equals(period)
                            && offer.getStatus().equals(status)) {
                        filteredOffers.add(offer);
                    }
                }
                listener.onFilteredOffers(filteredOffers);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onCancelled(databaseError);
            }
        });
    }

    public void addOffer(String title, String description, String type, String zone, String remuneration, String period, DatabaseReference.CompletionListener listener) {
        DatabaseReference offersRef = databaseReference.push();
        String offerId = offersRef.getKey();

        Map<String, Object> offerData = new HashMap<>();
        offerData.put("title", title);
        offerData.put("description", description);
        offerData.put("type", type);
        offerData.put("zone", zone);
        offerData.put("remuneration", remuneration);
        offerData.put("period", period);
        offerData.put("offerId", offerId);

        offersRef.setValue(offerData, listener);
    }

    public void removeOffer(DatabaseReference.CompletionListener listener) {
        DatabaseReference offersRef = databaseReference.push();
        String offerId = offersRef.getKey();

        offersRef = databaseReference.child(offerId);
        offersRef.removeValue(listener);
    }

    public void addCandidateToOffer(String offerId, String candidateId, DatabaseReference.CompletionListener listener) {
        databaseReference.child(offerId).child("candidates").child(candidateId).setValue(true, listener);
    }
}
