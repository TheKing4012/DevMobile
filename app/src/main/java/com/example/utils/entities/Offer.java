package com.example.utils.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public class Offer implements Parcelable {
    private String title;
    private String description;
    private String status;
    private String zone;
    private String remuneration;
    private String period;
    private String employerID;
    private Map<String, Candidate> candidates;
    private String offerId;

    public Offer() {}

    public Offer(String title, String description, String type, String zone, String remuneration, String period, String employerID, Map<String, Candidate> candidates, String offerId) {
        this.title = title;
        this.description = description;
        this.status = type;
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
        status = in.readString();
        zone = in.readString();
        remuneration = in.readString();
        period = in.readString();
        employerID = in.readString();
        offerId = in.readString();
        candidates = new HashMap<>();
        in.readMap(candidates, Candidate.class.getClassLoader());
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Map<String, Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(Map<String, Candidate> candidates) {
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
        dest.writeString(status);
        dest.writeString(zone);
        dest.writeString(remuneration);
        dest.writeString(period);
        dest.writeString(employerID);
        dest.writeString(offerId);
        dest.writeMap(candidates);
    }
}
