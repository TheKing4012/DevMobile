package com.example.utils;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;

public class JsonParser {

    public static String loadJSONFromAsset(Context context, String fileName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static void parseJsonData(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray usersArray = jsonObject.getJSONArray("users");
            for (int i = 0; i < usersArray.length(); i++) {
                JSONObject userObject = usersArray.getJSONObject(i);
                int id = userObject.getInt("id");
                String firstName = userObject.getString("first_name");
                String lastName = userObject.getString("last_name");
                String dateOfBirth = userObject.getString("date_of_birth");
                // Faites quelque chose avec les données récupérées
                System.out.println("User " + id + ": " + firstName + " " + lastName + ", Date of Birth: " + dateOfBirth);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
