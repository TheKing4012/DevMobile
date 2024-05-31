package com.example.utils.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Candidate implements Parcelable {
    private String candidateId; // L'ID du candidat dans Firebase
    private String country;
    private String dateOfBirth;
    private String description;
    private String pdfUrl;
    private String status;
    private String answer;
    private String name;
    private String surname;
    private String email; // Nouveau champ : adresse email
    private String phoneNumber; // Nouveau champ : numéro de téléphone
    private String city; // Nouveau champ : ville

    // Constructeur par défaut nécessaire pour Firebase
    public Candidate() {
    }

    public Candidate(String candidateId, String country, String dateOfBirth, String description, String pdfUrl, String status, String answer, String name, String surname, String email, String phoneNumber, String city) {
        this.candidateId = candidateId;
        this.country = country;
        this.dateOfBirth = dateOfBirth;
        this.description = description;
        this.pdfUrl = pdfUrl;
        this.status = status;
        this.answer = answer;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.city = city;
    }

    protected Candidate(Parcel in) {
        candidateId = in.readString();
        country = in.readString();
        dateOfBirth = in.readString();
        description = in.readString();
        pdfUrl = in.readString();
        status = in.readString();
        answer = in.readString();
        name = in.readString();
        surname = in.readString();
        email = in.readString();
        phoneNumber = in.readString();
        city = in.readString();
    }

    public static final Creator<Candidate> CREATOR = new Creator<Candidate>() {
        @Override
        public Candidate createFromParcel(Parcel in) {
            return new Candidate(in);
        }

        @Override
        public Candidate[] newArray(int size) {
            return new Candidate[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(candidateId);
        dest.writeString(country);
        dest.writeString(dateOfBirth);
        dest.writeString(description);
        dest.writeString(pdfUrl);
        dest.writeString(status);
        dest.writeString(answer);
        dest.writeString(name);
        dest.writeString(surname);
        dest.writeString(email);
        dest.writeString(phoneNumber);
        dest.writeString(city);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
