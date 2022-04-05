package com.dmw.noble.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Variant {

    @SerializedName("variant_id")
    @Expose
    private String variantId;
    @SerializedName("variant_name")
    @Expose
    private String variantName;
    @SerializedName("seating_capacity")
    @Expose
    private String seatingCapacity;
    @SerializedName("cubic_capacity")
    @Expose
    private String cubicCapacity;
    @SerializedName("fuel_type")
    @Expose
    private String fuelType;

    public String getVariantId() {
        return variantId;
    }

    public void setVariantId(String variantId) {
        this.variantId = variantId;
    }

    public String getVariantName() {
        return variantName;
    }

    public void setVariantName(String variantName) {
        this.variantName = variantName;
    }

    public String getSeatingCapacity() {
        return seatingCapacity;
    }

    public void setSeatingCapacity(String seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }

    public String getCubicCapacity() {
        return cubicCapacity;
    }

    public void setCubicCapacity(String cubicCapacity) {
        this.cubicCapacity = cubicCapacity;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

}