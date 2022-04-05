package com.dmw.noble.model_health.compare;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Company {

    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("suminsured")
    @Expose
    private String suminsured;
    @SerializedName("subplan")
    @Expose
    private String subplan;
    @SerializedName("plan")
    @Expose
    private String plan;
    @SerializedName("tenure")
    @Expose
    private String tenure;
    @SerializedName("premium")
    @Expose
    private String premium;

    @SerializedName("logo")
    @Expose
    private String logo;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSuminsured() {
        return suminsured;
    }

    public void setSuminsured(String suminsured) {
        this.suminsured = suminsured;
    }

    public String getSubplan() {
        return subplan;
    }

    public void setSubplan(String subplan) {
        this.subplan = subplan;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getTenure() {
        return tenure;
    }

    public void setTenure(String tenure) {
        this.tenure = tenure;
    }

    public String getPremium() {
        return premium;
    }

    public void setPremium(String premium) {
        this.premium = premium;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}