package com.square.pos.model_health.v2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("gross")
    @Expose
    private Float gross;
    @SerializedName("net")
    @Expose
    private Float net;
    @SerializedName("gst")
    @Expose
    private Float gst;
    @SerializedName("extra")
    @Expose
    private String extra;
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("status")
    @Expose
    private Float status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("plan_name")
    @Expose
    private String planName;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("suminsured")
    @Expose
    private String suminsured;
    @SerializedName("plan_code")
    @Expose
    private String planCode;
    @SerializedName("feat_plan_name")
    @Expose
    private String featPlanName;
    @SerializedName("feat_sub_plan_name")
    @Expose
    private String featSubPlanName;
    @SerializedName("app_key")
    @Expose
    private String appKey;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getFeatSubPlanName() {
        return featSubPlanName;
    }

    public void setFeatSubPlanName(String featSubPlanName) {
        this.featSubPlanName = featSubPlanName;
    }

    public String getFeatPlanName() {
        return featPlanName;
    }

    public void setFeatPlanName(String featPlanName) {
        this.featPlanName = featPlanName;
    }

    public Float getGross() {
        return gross;
    }

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public void setGross(Float gross) {
        this.gross = gross;
    }

    public Float getNet() {
        return net;
    }

    public void setNet(Float net) {
        this.net = net;
    }

    public Float getGst() {
        return gst;
    }

    public void setGst(Float gst) {
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

    public Float getStatus() {
        return status;
    }

    public void setStatus(Float status) {
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
}