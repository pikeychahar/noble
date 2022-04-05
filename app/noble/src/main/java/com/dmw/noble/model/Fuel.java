package com.dmw.noble.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Fuel {

    @SerializedName("fuelType")
    @Expose
    private List<FuelType> fuelType = null;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<FuelType> getFuelType() {
        return fuelType;
    }

    public void setFuelType(List<FuelType> fuelType) {
        this.fuelType = fuelType;
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