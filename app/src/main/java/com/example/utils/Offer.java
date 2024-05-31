package com.example.utils;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class Offer implements Parcelable {

    private String title;
    private String description;
    private String type;
    private String zone;
    private String remuneration;
    private String period;
    private String employerID;
    private List<String> candidates;
    private String offerId;

    // Constructeur par défaut (nécessaire pour Firebase)
    public Offer() {}

    // Constructeur avec tous les champs
    public Offer(String title, String description, String type, String zone, String remuneration, String period, String employerID, List<String> candidates, String offerId) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.zone = zone;
        this.remuneration = remuneration;
        this.period = period;
        this.employerID = employerID;
        this.candidates = candidates;
        this.offerId = offerId;
    }

    protected Offer(Parcel in) {
        title = in.readString();
        description = in.readString();
        type = in.readString();
        zone = in.readString();
        remuneration = in.readString();
        period = in.readString();
        employerID = in.readString();
        candidates = in.createStringArrayList();
        offerId = in.readString();
    }

    public static final Creator<Offer> CREATOR = new Creator<Offer>() {
        @Override
        public Offer createFromParcel(Parcel in) {
            return new Offer(in);
        }

        @Override
        public Offer[] newArray(int size) {
            return new Offer[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getRemuneration() {
        return remuneration;
    }

    public void setRemuneration(String remuneration) {
        this.remuneration = remuneration;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getEmployerID() {
        return employerID;
    }

    public void setEmployerID(String employerID) {
        this.employerID = employerID;
    }

    public List<String> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<String> candidates) {
        this.candidates = candidates;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(type);
        dest.writeString(zone);
        dest.writeString(remuneration);
        dest.writeString(period);
        dest.writeString(employerID);
        dest.writeStringList(candidates);
        dest.writeString(offerId);
    }
}
