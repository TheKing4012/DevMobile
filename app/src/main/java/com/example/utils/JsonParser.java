package com.example.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Scanner;

public class JsonParser {
    InputStream inputStream;

    public JsonParser(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void parseJsonFile() {
        try {
            // Convert InputStream to String
            Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
            String jsonData = scanner.hasNext() ? scanner.next() : "";

            // Close the inputStream
            inputStream.close();

            // Print the JSON data
            System.out.println("JSON data: " + jsonData);

            // Convert String to JSONArray
            JSONArray jsonArray = new JSONArray(jsonData);

            // Loop through the JSONArray to access each JSONObject
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // Now you can access individual fields of each JSONObject
                String email = jsonObject.getString("Email");
                String surname = jsonObject.getString("Surname");
                String name = jsonObject.getString("Name");
                String phone = jsonObject.getString("Phone");
                String day = jsonObject.getString("Day");
                String month = jsonObject.getString("Month");
                int year = jsonObject.getInt("Year");
                int dayPosition = jsonObject.getInt("DayPosition");
                int monthPosition = jsonObject.getInt("MonthPosition");
                int yearPosition = jsonObject.getInt("YearPosition");
                boolean music = jsonObject.getBoolean("Music");
                boolean sport = jsonObject.getBoolean("Sport");
                boolean reading = jsonObject.getBoolean("Reading");
                boolean synchronise = jsonObject.getBoolean("Synchronise");

                // Now you can use these values as needed
            }
        } catch (JSONException e) {
            e.printStackTrace();
            // Handle JSON parsing errors
        } catch (Exception e) {
            e.printStackTrace();
            // Handle other exceptions
        }
    }

    public boolean isExistingEmail(String newEmail) {
        boolean isExisting = false;
        try {
            // Convert InputStream to String
            Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
            String jsonData = scanner.hasNext() ? scanner.next() : "";

            // Close the inputStream
            inputStream.close();

            // Convert String to JSONArray
            JSONArray jsonArray = new JSONArray(jsonData);

            // Loop through the JSONArray to check if the email exists
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // Get the email field of each JSONObject
                String email = jsonObject.getString("Email");

                // Check if the email matches the new email
                if (email.equals(newEmail)) {
                    isExisting = true;
                    break; // Exit the loop if email exists
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            // Handle JSON parsing errors
        } catch (Exception e) {
            e.printStackTrace();
            // Handle other exceptions
        }
        return isExisting;
    }

}
