package com.example.utils.listeners;

import java.io.File;

public interface DownloadCompleteListener {
    void onDownloadComplete(File localFile);
    void onDownloadFailed(String errorMessage);
}
