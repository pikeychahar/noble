package com.dmw.noble.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ManufactureList implements Serializable {


    @SerializedName("manufacture")
    @Expose
    private List<Manufacture> manufacture = null;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<Manufacture> getManufacture() {
        return manufacture;
    }

    public void setManufacture(List<Manufacture> manufacture) {
        this.manufacture = manufacture;
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