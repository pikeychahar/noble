package com.square.pos.model_crm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ClaimDatum implements Serializable {
    @SerializedName("SrNo")
    @Expose
    private Integer srNo;
    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("Claim_Id")
    @Expose
    private String claimId;
    @SerializedName("Claim_Creator")
    @Expose
    private String claimCreator;
    @SerializedName("Claim_Manager")
    @Expose
    private String claimManager;
    @SerializedName("LossType")
    @Expose
    private String lossType;
    @SerializedName("CauseOfLossType")
    @Expose
    private String causeOfLossType;
    @SerializedName("Intimated_To_Insurer")
    @Expose
    private String intimatedToInsurer;
    @SerializedName("Survey_Status")
    @Expose
    private String surveyStatus;
    @SerializedName("Spot_Survey_Status")
    @Expose
    private String spotSurveyStatus;
    @SerializedName("Add_Stamp")
    @Expose
    private String addStamp;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Quotation_Id")
    @Expose
    private String quotationId;

    public Integer getSrNo() {
        return srNo;
    }

    public void setSrNo(Integer srNo) {
        this.srNo = srNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClaimId() {
        return claimId;
    }

    public void setClaimId(String claimId) {
        this.claimId = claimId;
    }

    public String getClaimCreator() {
        return claimCreator;
    }

    public void setClaimCreator(String claimCreator) {
        this.claimCreator = claimCreator;
    }

    public String getClaimManager() {
        return claimManager;
    }

    public void setClaimManager(String claimManager) {
        this.claimManager = claimManager;
    }

    public String getLossType() {
        return lossType;
    }

    public void setLossType(String lossType) {
        this.lossType = lossType;
    }

    public String getCauseOfLossType() {
        return causeOfLossType;
    }

    public void setCauseOfLossType(String causeOfLossType) {
        this.causeOfLossType = causeOfLossType;
    }

    public String getIntimatedToInsurer() {
        return intimatedToInsurer;
    }

    public void setIntimatedToInsurer(String intimatedToInsurer) {
        this.intimatedToInsurer = intimatedToInsurer;
    }

    public String getSurveyStatus() {
        return surveyStatus;
    }

    public void setSurveyStatus(String surveyStatus) {
        this.surveyStatus = surveyStatus;
    }

    public String getSpotSurveyStatus() {
        return spotSurveyStatus;
    }

    public void setSpotSurveyStatus(String spotSurveyStatus) {
        this.spotSurveyStatus = spotSurveyStatus;
    }

    public String getAddStamp() {
        return addStamp;
    }

    public void setAddStamp(String addStamp) {
        this.addStamp = addStamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(String quotationId) {
        this.quotationId = quotationId;
    }

}