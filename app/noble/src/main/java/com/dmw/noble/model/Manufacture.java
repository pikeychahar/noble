package com.dmw.noble.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Manufacture implements Serializable {
    @SerializedName("make")
    @Expose
    private String make;

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

}