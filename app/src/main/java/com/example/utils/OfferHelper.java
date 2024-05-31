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

    public void getLast100Offers(FilteredOffersListener listener) {
        Query query = databaseReference.orderByKey().limitToLast(100);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<DataSnapshot> snapshotList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    snapshotList.add(snapshot);
                }
                List<Offer> offers = new ArrayList<>();
                for (DataSnapshot snapshot : snapshotList) {
                    Offer offer = new Offer();
                    offer.setTitle(snapshot.child("title").getValue(String.class));
                    offer.setDescription(snapshot.child("description").getValue(String.class));
                    offer.setType(snapshot.child("type").getValue(String.class));
                    offer.setZone(snapshot.child("zone").getValue(String.class));
                    offer.setRemuneration(snapshot.child("remuneration").getValue(String.class));
                    offer.setPeriod(snapshot.child("period").getValue(String.class));
                    offer.setEmployerID(snapshot.child("employerID").getValue(String.class));
                    offer.setOfferId(snapshot.child("offerId").getValue(String.class));

                    offers.add(offer);
                }
                listener.onFilteredOffers(offers);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onCancelled(databaseError);
            }
        });
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
                            && offer.getType().equals(status)) {
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

    public void addOffer(String title, String description, String type, String zone, String remuneration, String period, String employerID, DatabaseReference.CompletionListener listener) {
        DatabaseReference offersRef = databaseReference.push();
        String offerId = offersRef.getKey();

        Map<String, Object> offerData = new HashMap<>();
        offerData.put("title", title);
        offerData.put("description", description);
        offerData.put("type", type);
        offerData.put("zone", zone);
        offerData.put("remuneration", remuneration);
        offerData.put("period", period);
        offerData.put("employerID", employerID);
        offerData.put("candidates", new ArrayList<>());
        offerData.put("offerId", offerId);

        offersRef.setValue(offerData, listener);
    }

    public void getOffersByEmployerID(String employerID, FilteredOffersListener listener) {
        Query query = databaseReference.orderByKey();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Offer> employerOffers = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Offer offer = snapshot.getValue(Offer.class);
                    if (offer != null && employerID.equals(offer.getEmployerID())) {
                        employerOffers.add(offer);
                    }
                }
                listener.onFilteredOffers(employerOffers);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onCancelled(databaseError);
            }
        });
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
