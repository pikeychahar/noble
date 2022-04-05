package com.dmw.noble.model_travel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Prahalad Chahar on 22/02/22.
 */

public class CoverageDetail {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("Coverage")
    @Expose
    private String coverage;
    @SerializedName("sum_insured")
    @Expose
    private String sumInsured;
    @SerializedName("sum_insured_currency")
    @Expose
    private String sumInsuredCurrency;
    @SerializedName("deductible_time_excess")
    @Expose
    private String deductibleTimeExcess;
    @SerializedName("plan_type")
    @Expose
    private String planType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCoverage() {
        return coverage;
    }

    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }

    public String getSumInsured() {
        return sumInsured;
    }

    public void setSumInsured(String sumInsured) {
        this.sumInsured = sumInsured;
    }

    public String getSumInsuredCurrency() {
        return sumInsuredCurrency;
    }

    public void setSumInsuredCurrency(String sumInsuredCurrency) {
        this.sumInsuredCurrency = sumInsuredCurrency;
    }

    public String getDeductibleTimeExcess() {
        return deductibleTimeExcess;
    }

    public void setDeductibleTimeExcess(String deductibleTimeExcess) {
        this.deductibleTimeExcess = deductibleTimeExcess;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

}