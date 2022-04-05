package com.square.pos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Prahalad Chahar on 2019-07-15.
 */
public class Policy {

    @SerializedName("quotation")
    @Expose
    private List<PolicyList> quotation = null;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<PolicyList> getQuotation() {
        return quotation;
    }

    public void setQuotation(List<PolicyList> quotation) {
        this.quotation = quotation;
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