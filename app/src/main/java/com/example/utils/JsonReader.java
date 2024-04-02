package com.example.utils;

import android.content.Context;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class JsonReader {

    public static JSONObject loadJSON(Context context) throws FileNotFoundException {
        JSONObject jsonObject = null;
        InputStream inputStream = null;
        try {
            inputStream = context.openFileInput("data_tp3_form.json");
        } catch (FileNotFoundException fnfe) {
            System.out.println("loadJSON : FileNotFoundExcpetion");
            throw fnfe;
        }
        try {
            Scanner scanner = new Scanner(inputStream);
            StringBuilder stringBuilder = new StringBuilder();
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine());
            }
            scanner.close();
            System.out.println("Le fichier a été chargé : " + context.getFilesDir() + "/data_tp3_form.json");
            jsonObject = new JSONObject(stringBuilder.toString());
        } catch (JSONException e) {
            System.out.println("Le fichier n'a pas pu être chargé : " + context.getFilesDir() + "/data_tp3_form.json");
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static void deleteJSONFile(Context context) {
        context.deleteFile("data_tp3_form.json");
        System.out.println("Le fichier a été supprimé : " + context.getFilesDir() + "/data_tp3_form.json");
    }

    public static void createJSONFile(Context context, Bundle bundle) {
        // Créez un JSONObject et y place les valeurs du bundle
        JSONObject jsonObject = new JSONObject();
        try {
            for (String key : bundle.keySet()) {
                jsonObject.put(key,bundle.get(key));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println(jsonObject);
        String jsonString = jsonObject.toString();
        String fileName = "data_tp3_form.json";
        FileOutputStream outputStream;

        try {
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(jsonString.getBytes());
            outputStream.close();
            System.out.println("Le fichier a été créé : " + context.getFilesDir() + "/" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}