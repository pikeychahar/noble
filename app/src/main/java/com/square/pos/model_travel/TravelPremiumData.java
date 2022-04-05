package com.square.pos.model_travel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Prahalad Chahar on 22/02/22.
 */
public class TravelPremiumData {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("plan_type")
    @Expose
    private String planType;
    @SerializedName("suminsured")
    @Expose
    private String suminsured;
    @SerializedName("data")
    @Expose
    private List<TravelPlanData> data = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public String getSuminsured() {
        return suminsured;
    }

    public void setSuminsured(String suminsured) {
        this.suminsured = suminsured;
    }

    public List<TravelPlanData> getData() {
        return data;
    }

    public void setData(List<TravelPlanData> data) {
        this.data = data;
    }

}