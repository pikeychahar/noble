package com.dmw.noble.model_life;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AppointeeList {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("appointee_relation")
    @Expose
    private List<AppointeeRelation> appointeeRelation = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<AppointeeRelation> getAppointeeRelation() {
        return appointeeRelation;
    }

    public void setAppointeeRelation(List<AppointeeRelation> appointeeRelation) {
        this.appointeeRelation = appointeeRelation;
    }

}