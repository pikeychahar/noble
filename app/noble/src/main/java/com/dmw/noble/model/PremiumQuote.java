package com.dmw.noble.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PremiumQuote {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("idv")
    @Expose
    private String idv;
    @SerializedName("idv_min")
    @Expose
    private Integer idvMin;
    @SerializedName("idv_max")
    @Expose
    private Integer idvMax;
    @SerializedName("od")
    @Expose
    private String od;
    @SerializedName("ncb")
    @Expose
    private String ncb;
    @SerializedName("current_ncb")
    @Expose
    private String currentNcb;
    @SerializedName("tenure")
    @Expose
    private String tenure;
    @SerializedName("policy_type")
    @Expose
    private String policyType;
    @SerializedName("zero_dept")
    @Expose
    private String zeroDept;
    @SerializedName("tp")
    @Expose
    private String tp;
    @SerializedName("pa")
    @Expose
    private String pa;
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("net")
    @Expose
    private String net;
    @SerializedName("gst")
    @Expose
    private String gst;
    @SerializedName("tata_flag")
    @Expose
    private String tataFlag;

    @SerializedName("total_premium")
    @Expose
    private String totalPremium;

    @SerializedName("pacoverfor_unnamed_person")
    @Expose
    private String unNamedPA;

    @SerializedName("legal_liability_paid_driver")
    @Expose
    private String paidDriver;

    @SerializedName("start_date")
    @Expose
    private String startDate;

    @SerializedName("end_date")
    @Expose
    private String endDate;

    @SerializedName("tppd_restricted_to")
    @Expose
    private String tppd;
    @SerializedName("previous_insurer")
    @Expose
    private String previousInsurer;

    @SerializedName("road_side_assistance")
    @Expose
    private String roadSideAssistance;

    public String getRoadSideAssistance() {
        return roadSideAssistance;
    }

    public String getTppd() {
        return tppd;
    }

    public void setTppd(String tppd) {
        this.tppd = tppd;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    public String getUnNamedPA() {
        return unNamedPA;
    }

    public void setUnNamedPA(String unNamedPA) {
        this.unNamedPA = unNamedPA;
    }

    public String getPaidDriver() {
        return paidDriver;
    }

    public void setPaidDriver(String paidDriver) {
        this.paidDriver = paidDriver;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getIdv() {
        return idv;
    }

    public void setIdv(String idv) {
        this.idv = idv;
    }

    public Integer getIdvMin() {
        return idvMin;
    }

    public void setIdvMin(Integer idvMin) {
        this.idvMin = idvMin;
    }

    public Integer getIdvMax() {
        return idvMax;
    }

    public void setIdvMax(Integer idvMax) {
        this.idvMax = idvMax;
    }

    public String getOd() {
        return od;
    }

    public void setOd(String od) {
        this.od = od;
    }

    public String getNcb() {
        return ncb;
    }

    public void setNcb(String ncb) {
        this.ncb = ncb;
    }

    public String getTenure() {
        return tenure;
    }

    public void setTenure(String tenure) {
        this.tenure = tenure;
    }

    public String getPolicyType() {
        return policyType;
    }

    public void setPolicyType(String policyType) {
        this.policyType = policyType;
    }

    public String getZeroDept() {
        return zeroDept;
    }

    public void setZeroDept(String zeroDept) {
        this.zeroDept = zeroDept;
    }

    public String getTp() {
        return tp;
    }

    public void setTp(String tp) {
        this.tp = tp;
    }

    public String getPa() {
        return pa;
    }

    public void setPa(String pa) {
        this.pa = pa;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getNet() {
        return net;
    }

    public void setNet(String net) {
        this.net = net;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getTataFlag() {
        return tataFlag;
    }

    public void setTataFlag(String tataFlag) {
        this.tataFlag = tataFlag;
    }

    public String getTotalPremium() {
        return totalPremium;
    }

    public void setTotalPremium(String totalPremium) {
        this.totalPremium = totalPremium;
    }

    public String getCurrentNcb() {
        return currentNcb;
    }

    public void setCurrentNcb(String currentNcb) {
        this.currentNcb = currentNcb;
    }

    public String getPreviousInsurer() {
        return previousInsurer;
    }

    public void setPreviousInsurer(String previousInsurer) {
        this.previousInsurer = previousInsurer;
    }

}