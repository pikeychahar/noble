package com.square.pos.model_travel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Prahalad Chahar on 22/02/22.
 */
public class TravelPlanData {

    @SerializedName("premium")
    @Expose
    private Integer premium;
    @SerializedName("plan")
    @Expose
    private String plan;
    @SerializedName("coverage_detail")
    @Expose
    private List<CoverageDetail> coverageDetail = null;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("geography")
    @Expose
    private String geography;
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("plan_name")
    @Expose
    private String planName;

    public Integer getPremium() {
        return premium;
    }

    public void setPremium(Integer premium) {
        this.premium = premium;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public List<CoverageDetail> getCoverageDetail() {
        return coverageDetail;
    }

    public void setCoverageDetail(List<CoverageDetail> coverageDetail) {
        this.coverageDetail = coverageDetail;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getGeography() {
        return geography;
    }

    public void setGeography(String geography) {
        this.geography = geography;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

}