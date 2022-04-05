package com.dmw.noble.model_crm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CancellationData {

    @SerializedName("Status")
    @Expose
    private Boolean status;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private CancellationSingleData data;
    @SerializedName("reqData")
    @Expose
    private ReqData reqData;
    @SerializedName("altPolicy")
    @Expose
    private String altPolicy;
    @SerializedName("custLetter")
    @Expose
    private String custLetter;
    @SerializedName("cancelCheque")
    @Expose
    private String cancelCheque;
    @SerializedName("sucessDoc")
    @Expose
    private String sucessDoc;
    @SerializedName("IsDisabled")
    @Expose
    private String isDisabled;
    @SerializedName("addedBy")
    @Expose
    private String addedBy;
    @SerializedName("mappedTo")
    @Expose
    private String mappedTo;
    @SerializedName("curStatus")
    @Expose
    private String curStatus;
    @SerializedName("currentRemark")
    @Expose
    private String currentRemark;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CancellationSingleData getData() {
        return data;
    }

    public void setData(CancellationSingleData data) {
        this.data = data;
    }

    public ReqData getReqData() {
        return reqData;
    }

    public void setReqData(ReqData reqData) {
        this.reqData = reqData;
    }

    public String getAltPolicy() {
        return altPolicy;
    }

    public void setAltPolicy(String altPolicy) {
        this.altPolicy = altPolicy;
    }

    public String getCustLetter() {
        return custLetter;
    }

    public void setCustLetter(String custLetter) {
        this.custLetter = custLetter;
    }

    public String getCancelCheque() {
        return cancelCheque;
    }

    public void setCancelCheque(String cancelCheque) {
        this.cancelCheque = cancelCheque;
    }

    public String getSucessDoc() {
        return sucessDoc;
    }

    public void setSucessDoc(String sucessDoc) {
        this.sucessDoc = sucessDoc;
    }

    public String getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(String isDisabled) {
        this.isDisabled = isDisabled;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public String getMappedTo() {
        return mappedTo;
    }

    public void setMappedTo(String mappedTo) {
        this.mappedTo = mappedTo;
    }

    public String getCurStatus() {
        return curStatus;
    }

    public void setCurStatus(String curStatus) {
        this.curStatus = curStatus;
    }

    public String getCurrentRemark() {
        return currentRemark;
    }

    public void setCurrentRemark(String currentRemark) {
        this.currentRemark = currentRemark;
    }

}