package com.example.utils.entities;

import java.util.Map;

public class Candidate {
    private String status;
    private String name;

    public Candidate() {}

    public Candidate(String status, String name) {
        this.status = status;
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
