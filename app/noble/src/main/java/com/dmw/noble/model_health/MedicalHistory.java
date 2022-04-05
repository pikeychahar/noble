package com.dmw.noble.model_health;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MedicalHistory {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("quoteid")
    @Expose
    private String quoteid;
    @SerializedName("premium")
    @Expose
    private Premium premium;
    @SerializedName("response")
    @Expose
    private Response response;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getQuoteid() {
        return quoteid;
    }

    public void setQuoteid(String quoteid) {
        this.quoteid = quoteid;
    }

    public Premium getPremium() {
        return premium;
    }

    public void setPremium(Premium premium) {
        this.premium = premium;
    }
    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}