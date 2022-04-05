package com.dmw.noble.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VariantList {
    @SerializedName("variant")
    @Expose
    private List<Variant> variant = null;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<Variant> getVariant() {
        return variant;
    }

    public void setVariant(List<Variant> variant) {
        this.variant = variant;
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