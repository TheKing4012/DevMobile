package com.example.tp3.service;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import com.example.utils.FirebaseJsonDownloader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class DownloadAndParseService extends Service {
    private static final String TAG = "DownloadAndParseService";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Start your task to download and parse the file
        startDownload();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Not used
        return null;
    }

    private void startDownload() {
        FirebaseJsonDownloader firebaseJsonDownloader = new FirebaseJsonDownloader("https://rgrpo-af967-default-rtdb.firebaseio.com/", "users");
        firebaseJsonDownloader.downloadJsonFile();
    }
}
