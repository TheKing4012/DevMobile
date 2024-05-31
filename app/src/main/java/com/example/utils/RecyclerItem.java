package com.example.utils;

import com.example.utils.entities.Candidate;
import com.example.utils.entities.Offer;
import com.example.utils.helpers.CommonHelper;

public class RecyclerItem {
    private Offer offer;
    private Candidate candidate;
    private String text2;
    private String imageStatus; // can be "check", "pending", "deny" or null

    public RecyclerItem(Offer offer, String imageStatus) {
        this.offer = offer;
        this.imageStatus = imageStatus;
    }

    public RecyclerItem(Candidate candidate, String imageStatus) {
        this.candidate = candidate;
        this.imageStatus = imageStatus;
    }

    // Getters and setters
    public String getText1() {
        if(offer != null) {
            return offer.getTitle();
        }
        return candidate.getName() +" "+ candidate.getSurname();
    }

    public String getText2() {
        if(offer != null) {
            return CommonHelper.shortenText(offer.getDescription());
        }
        return "Click here to see his profile";
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

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }
}
