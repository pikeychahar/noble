package com.square.pos.model_life;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("max_limit")
    @Expose
    private Integer maxLimit;
    @SerializedName("policy_period")
    @Expose
    private Integer policyPeriod;
    @SerializedName("gross")
    @Expose
    private Integer gross;
    @SerializedName("net")
    @Expose
    private Integer net;
    @SerializedName("gst")
    @Expose
    private Integer gst;
    @SerializedName("extra")
    @Expose
    private String extra;
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("plan_name")
    @Expose
    private String planName;
    @SerializedName("suminsured")
    @Expose
    private String suminsured;

    @SerializedName("income_benefit")
    @Expose
    private String incomeBenefit;

    public String getIncomeBenefit() {
        return incomeBenefit;
    }

    public void setIncomeBenefit(String incomeBenefit) {
        this.incomeBenefit = incomeBenefit;
    }

    public String getCancerCare() {
        return cancerCare;
    }

    public void setCancerCare(String cancerCare) {
        this.cancerCare = cancerCare;
    }

    public String getCriticalIllness() {
        return criticalIllness;
    }

    public void setCriticalIllness(String criticalIllness) {
        this.criticalIllness = criticalIllness;
    }

    public String getAccidentDeathCover() {
        return accidentDeathCover;
    }

    public void setAccidentDeathCover(String accidentDeathCover) {
        this.accidentDeathCover = accidentDeathCover;
    }

    public String getPersonalAccident() {
        return personalAccident;
    }

    public void setPersonalAccident(String personalAccident) {
        this.personalAccident = personalAccident;
    }

    @SerializedName("cancer_care")
    @Expose
    private String cancerCare;

    @SerializedName("critical_Illness")
    @Expose
    private String criticalIllness;

    @SerializedName("accident_Death_Cover")
    @Expose
    private String accidentDeathCover;

    @SerializedName("personal_Accident_Cover_rider")
    @Expose
    private String personalAccident;


    public Integer getMaxLimit() {
        return maxLimit;
    }

    public void setMaxLimit(Integer maxLimit) {
        this.maxLimit = maxLimit;
    }

    public Integer getPolicyPeriod() {
        return policyPeriod;
    }

    public void setPolicyPeriod(Integer policyPeriod) {
        this.policyPeriod = policyPeriod;
    }

    public Integer getGross() {
        return gross;
    }

    public void setGross(Integer gross) {
        this.gross = gross;
    }

    public Integer getNet() {
        return net;
    }

    public void setNet(Integer net) {
        this.net = net;
    }

    public Integer getGst() {
        return gst;
    }

    public void setGst(Integer gst) {
        this.gst = gst;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getSuminsured() {
        return suminsured;
    }

    public void setSuminsured(String suminsured) {
        this.suminsured = suminsured;
    }

}