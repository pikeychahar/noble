package com.square.pos.model_digit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PremiumResponse {

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
    @SerializedName("od")
    @Expose
    private String od;
    @SerializedName("ncb")
    @Expose
    private String ncb;
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

}