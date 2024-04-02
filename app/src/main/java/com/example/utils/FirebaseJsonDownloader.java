package com.example.utils;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class FirebaseJsonDownloader {

    private DatabaseReference databaseReference;

    public FirebaseJsonDownloader(String url) {
        // Initialize Firebase Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReferenceFromUrl(url);

        // Reference to your JSON node"https://rgrpo-af967-default-rtdb.firebaseio.com/"
    }

    public FirebaseJsonDownloader(String url, String child) {
        // Initialize Firebase Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReferenceFromUrl(url).child(child);

        // Reference to your JSON node"https://rgrpo-af967-default-rtdb.firebaseio.com/"
    }

    public void downloadJsonFile() {
        // Add ValueEventListener to get the JSON data
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get the JSON data snapshot
                String jsonData = dataSnapshot.getValue().toString();

                // Convert JSON data to InputStream
                InputStream inputStream = new ByteArrayInputStream(jsonData.getBytes(StandardCharsets.UTF_8));

                // Now you can use this inputStream for further processing
                // For example, you can pass it to your JsonParser class
                JsonParser jsonParser = new JsonParser();
                jsonParser.parseJsonFile(inputStream);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle cancelled event
            }
        });
    }
}
