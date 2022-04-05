package com.dmw.noble.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Insurer {

    @SerializedName("insurer")
    @Expose
    private String insurer;
    @SerializedName("id")
    @Expose
    private String id;

    public String getInsurer() {
        return insurer;
    }

    public void setInsurer(String insurer) {
        this.insurer = insurer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}