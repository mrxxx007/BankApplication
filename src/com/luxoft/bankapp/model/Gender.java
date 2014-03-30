package com.luxoft.bankapp.model;

/**
 * Created by user on 3/25/2014.
 */
public enum Gender {
    MALE("Mr."), FEMALE("Mrs.");
    private String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    public String getSalut() {
        return gender;
    }
}

