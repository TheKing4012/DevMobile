package com.example.utils;

import android.graphics.drawable.Drawable;

import java.security.InvalidParameterException;
import java.util.Objects;

public class RecyclerItem {
    private Offer offer;
    private String text2;
    private String imageStatus; // can be "check", "pending", "deny" or null

    public RecyclerItem(Offer offer, String imageStatus) {
        this.offer = offer;
        this.imageStatus = imageStatus;
    }

    // Getters and setters
    public String getText1() {
        return offer.getTitle();
    }

    public String getText2() {
        return CommonHelper.shortenText(offer.getDescription());
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

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }
}
