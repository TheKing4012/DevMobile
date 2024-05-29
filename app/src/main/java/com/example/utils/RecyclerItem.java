package com.example.utils;

import android.graphics.drawable.Drawable;

import java.security.InvalidParameterException;
import java.util.Objects;

public class RecyclerItem {
    private String text1;
    private String text2;
    private String imageStatus; // can be "check", "pending", "deny" or null

    public RecyclerItem(String text1, String text2, String imageStatus) {
        this.text1 = text1;
        this.text2 = text2;
        this.imageStatus = imageStatus;
    }

    // Getters and setters
    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public String getImageStatus() {
        return imageStatus;
    }

    public void setImageStatus(String imageStatus) {
        this.imageStatus = imageStatus;
    }
}
