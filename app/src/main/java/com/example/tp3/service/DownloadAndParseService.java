package com.example.tp3.service;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadAndParseService extends Service {
    private static final String TAG = "DownloadAndParseService";
    private static final String FILE_URL = "https://example.com/yourfile.txt"; // URL of the file to download

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Start your task to download and parse the file
        new DownloadAndParseTask().execute(FILE_URL);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Not used
        return null;
    }

    private class DownloadAndParseTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            try {
                // Download the file
                URL url = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.connect();

                InputStream inputStream = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                result = stringBuilder.toString();
                inputStream.close();
            } catch (IOException e) {
                Log.e(TAG, "Error downloading file: " + e.getMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(String fileContent) {
            // Parse the downloaded file content here
            // For demonstration, let's just log the content
            Log.d(TAG, "Parsed file content: " + fileContent);

            // You can also broadcast an intent or use other mechanisms to send this data to your activity
        }
    }
}