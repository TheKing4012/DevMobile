package com.example.utils;

import android.graphics.drawable.Drawable;

import java.security.InvalidParameterException;
import java.util.Objects;

public class RecyclerItem {
    private String text1;
    private String text2;
    private Drawable image = Drawable.createFromPath("@drawable/pending");

    public RecyclerItem(String text1, String text2) {
        this.text1 = text1;
        this.text2 = text2;
    }

    public RecyclerItem(String text1, String text2, String validite) {
        this.text1 = text1;
        this.text2 = text2;
        this.image = Drawable.createFromPath(validite);
    }

    public String getText1() {
        return text1;
    }

    public String getText2() {
        return text2;
    }

    public Drawable getImageView() {
        return image;
    }

    public void setImgView(String imgView) {
        if(Objects.equals(imgView, "check")){
            image = Drawable.createFromPath("@drawable/check");
        } else
        if(Objects.equals(imgView, "pending")){
            image = Drawable.createFromPath("@drawable/hourglass");
        } else
        if(Objects.equals(imgView, "deny")){
            image = Drawable.createFromPath("@drawable/deny");
        } else {
            throw new InvalidParameterException("le paramètre de setValidite doit être check, pending ou deny.");
        }
    }
}