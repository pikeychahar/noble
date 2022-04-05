package com.dmw.noble.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Session {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("URL")
    @Expose
    private String uRL;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("SuccessMsg")
    @Expose
    private String successMsg;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getURL() {
        return uRL;
    }

    public void setURL(String uRL) {
        this.uRL = uRL;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }
}