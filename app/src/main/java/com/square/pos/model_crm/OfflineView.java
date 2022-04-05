package com.square.pos.model_crm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OfflineView {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("Status")
    @Expose
    private String quoteStatus;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private OffLineViewData data;
    @SerializedName("AssignBy")
    @Expose
    private String assignBy;
    @SerializedName("RejectedStatus")
    @Expose
    private String rejectedStatus;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public OffLineViewData getData() {
        return data;
    }

    public void setData(OffLineViewData data) {
        this.data = data;
    }

    public String getAssignBy() {
        return assignBy;
    }

    public void setAssignBy(String assignBy) {
        this.assignBy = assignBy;
    }

    public String getRejectedStatus() {
        return rejectedStatus;
    }

    public void setRejectedStatus(String rejectedStatus) {
        this.rejectedStatus = rejectedStatus;
    }

    public String getQuoteStatus() {
        return quoteStatus;
    }

    public void setQuoteStatus(String quoteStatus) {
        this.quoteStatus = quoteStatus;
    }
}