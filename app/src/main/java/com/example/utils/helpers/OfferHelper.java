package com.example.utils.helpers;

import androidx.annotation.NonNull;

import com.example.utils.entities.Candidate;
import com.example.utils.listeners.CandidateStatusListener;
import com.example.utils.listeners.FilteredCandidatesListener;
import com.example.utils.listeners.FilteredOffersListener;
import com.example.utils.entities.Offer;
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
                    offer.setStatus(snapshot.child("type").getValue(String.class));
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

    public void getOffersByCandidateID(String candidateID, FilteredOffersListener listener) {
        DatabaseReference offersRef = FirebaseDatabase.getInstance().getReference().child("offers");
        offersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Offer> candidateOffers = new ArrayList<>();
                for (DataSnapshot offerSnapshot : dataSnapshot.getChildren()) {
                    Offer offer = offerSnapshot.getValue(Offer.class);
                    if (offer != null && offerSnapshot.hasChild("candidates")) {
                        DataSnapshot candidatesSnapshot = offerSnapshot.child("candidates");
                        if (candidatesSnapshot.hasChild(candidateID)) {
                            candidateOffers.add(offer);
                        }
                    }
                }
                listener.onFilteredOffers(candidateOffers);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onCancelled(databaseError);
            }
        });
    }



    public void getCandidateStatusForOffer(String offerId, String candidateID, CandidateStatusListener listener) {
        DatabaseReference offerRef = FirebaseDatabase.getInstance().getReference()
                .child("offers").child(offerId).child("candidates").child(candidateID);
        offerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String status = dataSnapshot.child("status").getValue(String.class);
                    listener.onStatusRetrieved(status);
                } else {
                    listener.onStatusRetrieved(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onCancelled(databaseError);
            }
        });
    }

    public void getCandidatesByOfferID(String offerID, FilteredCandidatesListener listener) {
        DatabaseReference offersRef = FirebaseDatabase.getInstance().getReference().child("offers").child(offerID).child("candidates");
        offersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Candidate> candidates = new ArrayList<>();
                for (DataSnapshot candidateSnapshot : dataSnapshot.getChildren()) {
                    Candidate candidate = candidateSnapshot.getValue(Candidate.class);
                    if (candidate != null) {
                        candidate.setCandidateId(candidateSnapshot.getKey()); // Set the candidateId

                        // Pour chaque candidat, vérifiez s'il existe également dans users/candidates/{id}
                        DatabaseReference userCandidateRef = FirebaseDatabase.getInstance().getReference().child("users").child("candidates").child(candidate.getCandidateId());
                        userCandidateRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                                if (userSnapshot.exists()) {
                                    String phoneNumber = userSnapshot.child("phone").getValue(String.class);
                                    String city = userSnapshot.child("city").getValue(String.class);
                                    String userId = userSnapshot.child("userId").getValue(String.class); // Récupération du userId

                                    candidate.setPhoneNumber(phoneNumber);
                                    candidate.setCity(city);

                                    // Utilisation de FirebaseHelper pour récupérer l'email à partir du userID
                                    FirebaseHelper.fetchEmailFromUserID(userId, new FirebaseHelper.EmailFetchListener() {
                                        @Override
                                        public void onEmailFetched(String email) {
                                            candidate.setEmail(email); // Définition de l'email

                                            // Ajoutez le candidat à la liste après avoir traité toutes les données nécessaires
                                            candidates.add(candidate);

                                            // Informez le listener une fois tous les candidats récupérés
                                            if (candidates.size() == dataSnapshot.getChildrenCount()) {
                                                listener.onFilteredCandidates(candidates);
                                            }
                                        }

                                        @Override
                                        public void onError(String errorMessage) {
                                            // Gestion des erreurs lors de la récupération de l'email
                                            // Ajoutez le candidat à la liste même en cas d'erreur
                                            candidates.add(candidate);

                                            // Informez le listener une fois tous les candidats récupérés
                                            if (candidates.size() == dataSnapshot.getChildrenCount()) {
                                                listener.onFilteredCandidates(candidates);
                                            }
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                listener.onCancelled(databaseError);
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onCancelled(databaseError);
            }
        });
    }

    public void getCandidateInfo(String offerID, String candidateID, FilteredCandidatesListener listener) {
        DatabaseReference candidateRef = FirebaseDatabase.getInstance().getReference()
                .child("offers").child(offerID).child("candidates").child(candidateID);

        candidateRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Candidate candidate = dataSnapshot.getValue(Candidate.class);
                if (candidate != null) {
                    candidate.setCandidateId(dataSnapshot.getKey());

                    // Pour chaque candidat, vérifiez s'il existe également dans users/candidates/{id}
                    DatabaseReference userCandidateRef = FirebaseDatabase.getInstance().getReference()
                            .child("users").child("candidates").child(candidate.getCandidateId());

                    userCandidateRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                            if (userSnapshot.exists()) {
                                String phoneNumber = userSnapshot.child("phone").getValue(String.class);
                                String city = userSnapshot.child("city").getValue(String.class);
                                String userId = userSnapshot.child("userId").getValue(String.class); // Récupération du userId

                                candidate.setPhoneNumber(phoneNumber);
                                candidate.setCity(city);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            listener.onCancelled(databaseError);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onCancelled(databaseError);
            }
        });
    }


    public void deleteOffer(String offerId, DatabaseReference.CompletionListener listener) {
        DatabaseReference offerRef = databaseReference.child(offerId);
        offerRef.removeValue(listener);
    }
}
