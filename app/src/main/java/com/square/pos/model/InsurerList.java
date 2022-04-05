package com.square.pos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InsurerList {

    @SerializedName("insurer")
    @Expose
    private List<Insurer> insurer = null;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<Insurer> getInsurer() {
        return insurer;
    }

    public void setInsurer(List<Insurer> insurer) {
        this.insurer = insurer;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}