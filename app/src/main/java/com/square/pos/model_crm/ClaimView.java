package com.square.pos.model_crm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClaimView {

    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("Claim_Id")
    @Expose
    private String claimId;
    @SerializedName("CreateUser_Id")
    @Expose
    private String createUserId;
    @SerializedName("CreateUser_Type")
    @Expose
    private String createUserType;
    @SerializedName("CurrentUser_Id")
    @Expose
    private String currentUserId;
    @SerializedName("CurrentUser_Type")
    @Expose
    private String currentUserType;
    @SerializedName("Quotation_Id")
    @Expose
    private String quotationId;
    @SerializedName("Intimated_To_Insurer")
    @Expose
    private String intimatedToInsurer;
    @SerializedName("Claim_Intimated_By")
    @Expose
    private String claimIntimatedBy;
    @SerializedName("Intimation_Date_Time")
    @Expose
    private String intimationDateTime;
    @SerializedName("Claim_Intimation_No")
    @Expose
    private String claimIntimationNo;
    @SerializedName("Intimator_Name")
    @Expose
    private String intimatorName;
    @SerializedName("Intimator_Contact_No")
    @Expose
    private String intimatorContactNo;
    @SerializedName("Alternate_No")
    @Expose
    private String alternateNo;
    @SerializedName("WhatsApp_No")
    @Expose
    private String whatsAppNo;
    @SerializedName("Mail_Id")
    @Expose
    private String mailId;
    @SerializedName("Reason_Delay_Intimation")
    @Expose
    private String reasonDelayIntimation;
    @SerializedName("CauseOfLossType")
    @Expose
    private String causeOfLossType;
    @SerializedName("LossType")
    @Expose
    private String lossType;
    @SerializedName("Date_Time_Lose")
    @Expose
    private String dateTimeLose;
    @SerializedName("Estimated_Amount")
    @Expose
    private String estimatedAmount;
    @SerializedName("Accident_Garage_Name")
    @Expose
    private String accidentGarageName;
    @SerializedName("Accident_Garage_Near_LandMark")
    @Expose
    private String accidentGarageNearLandMark;
    @SerializedName("Garage_Pincode")
    @Expose
    private String garagePincode;
    @SerializedName("Garage_State_Id")
    @Expose
    private String garageStateId;
    @SerializedName("Garage_District_Id")
    @Expose
    private String garageDistrictId;
    @SerializedName("Garage_City_Id")
    @Expose
    private String garageCityId;
    @SerializedName("Fir_Status")
    @Expose
    private String firStatus;
    @SerializedName("Fir_Remarks")
    @Expose
    private String firRemarks;
    @SerializedName("Driver_Name")
    @Expose
    private String driverName;
    @SerializedName("Driver_Contact_No")
    @Expose
    private String driverContactNo;
    @SerializedName("Driver_DL_No")
    @Expose
    private String driverDLNo;
    @SerializedName("Spot_Survey_Status")
    @Expose
    private String spotSurveyStatus;
    @SerializedName("Tp_Status")
    @Expose
    private String tpStatus;
    @SerializedName("Survey_Status")
    @Expose
    private String surveyStatus;
    @SerializedName("Add_Stamp")
    @Expose
    private String addStamp;
    @SerializedName("Update_Stamp")
    @Expose
    private String updateStamp;
    @SerializedName("Document")
    @Expose
    private List<Document> document = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserType() {
        return createUserType;
    }

    public void setCreateUserType(String createUserType) {
        this.createUserType = createUserType;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    public String getCurrentUserType() {
        return currentUserType;
    }

    public void setCurrentUserType(String currentUserType) {
        this.currentUserType = currentUserType;
    }

    public String getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(String quotationId) {
        this.quotationId = quotationId;
    }

    public String getIntimatedToInsurer() {
        return intimatedToInsurer;
    }

    public void setIntimatedToInsurer(String intimatedToInsurer) {
        this.intimatedToInsurer = intimatedToInsurer;
    }

    public String getClaimIntimatedBy() {
        return claimIntimatedBy;
    }

    public void setClaimIntimatedBy(String claimIntimatedBy) {
        this.claimIntimatedBy = claimIntimatedBy;
    }

    public String getIntimationDateTime() {
        return intimationDateTime;
    }

    public void setIntimationDateTime(String intimationDateTime) {
        this.intimationDateTime = intimationDateTime;
    }

    public String getClaimIntimationNo() {
        return claimIntimationNo;
    }

    public void setClaimIntimationNo(String claimIntimationNo) {
        this.claimIntimationNo = claimIntimationNo;
    }

    public String getIntimatorName() {
        return intimatorName;
    }

    public void setIntimatorName(String intimatorName) {
        this.intimatorName = intimatorName;
    }

    public String getIntimatorContactNo() {
        return intimatorContactNo;
    }

    public void setIntimatorContactNo(String intimatorContactNo) {
        this.intimatorContactNo = intimatorContactNo;
    }

    public String getAlternateNo() {
        return alternateNo;
    }

    public void setAlternateNo(String alternateNo) {
        this.alternateNo = alternateNo;
    }

    public String getWhatsAppNo() {
        return whatsAppNo;
    }

    public void setWhatsAppNo(String whatsAppNo) {
        this.whatsAppNo = whatsAppNo;
    }

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public String getReasonDelayIntimation() {
        return reasonDelayIntimation;
    }

    public void setReasonDelayIntimation(String reasonDelayIntimation) {
        this.reasonDelayIntimation = reasonDelayIntimation;
    }

    public String getCauseOfLossType() {
        return causeOfLossType;
    }

    public void setCauseOfLossType(String causeOfLossType) {
        this.causeOfLossType = causeOfLossType;
    }

    public String getLossType() {
        return lossType;
    }

    public void setLossType(String lossType) {
        this.lossType = lossType;
    }

    public String getDateTimeLose() {
        return dateTimeLose;
    }

    public void setDateTimeLose(String dateTimeLose) {
        this.dateTimeLose = dateTimeLose;
    }

    public String getEstimatedAmount() {
        return estimatedAmount;
    }

    public void setEstimatedAmount(String estimatedAmount) {
        this.estimatedAmount = estimatedAmount;
    }

    public String getAccidentGarageName() {
        return accidentGarageName;
    }

    public void setAccidentGarageName(String accidentGarageName) {
        this.accidentGarageName = accidentGarageName;
    }

    public String getAccidentGarageNearLandMark() {
        return accidentGarageNearLandMark;
    }

    public void setAccidentGarageNearLandMark(String accidentGarageNearLandMark) {
        this.accidentGarageNearLandMark = accidentGarageNearLandMark;
    }

    public String getGaragePincode() {
        return garagePincode;
    }

    public void setGaragePincode(String garagePincode) {
        this.garagePincode = garagePincode;
    }

    public String getGarageStateId() {
        return garageStateId;
    }

    public void setGarageStateId(String garageStateId) {
        this.garageStateId = garageStateId;
    }

    public String getGarageDistrictId() {
        return garageDistrictId;
    }

    public void setGarageDistrictId(String garageDistrictId) {
        this.garageDistrictId = garageDistrictId;
    }

    public String getGarageCityId() {
        return garageCityId;
    }

    public void setGarageCityId(String garageCityId) {
        this.garageCityId = garageCityId;
    }

    public String getFirStatus() {
        return firStatus;
    }

    public void setFirStatus(String firStatus) {
        this.firStatus = firStatus;
    }

    public String getFirRemarks() {
        return firRemarks;
    }

    public void setFirRemarks(String firRemarks) {
        this.firRemarks = firRemarks;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverContactNo() {
        return driverContactNo;
    }

    public void setDriverContactNo(String driverContactNo) {
        this.driverContactNo = driverContactNo;
    }

    public String getDriverDLNo() {
        return driverDLNo;
    }

    public void setDriverDLNo(String driverDLNo) {
        this.driverDLNo = driverDLNo;
    }

    public String getSpotSurveyStatus() {
        return spotSurveyStatus;
    }

    public void setSpotSurveyStatus(String spotSurveyStatus) {
        this.spotSurveyStatus = spotSurveyStatus;
    }

    public String getTpStatus() {
        return tpStatus;
    }

    public void setTpStatus(String tpStatus) {
        this.tpStatus = tpStatus;
    }

    public String getSurveyStatus() {
        return surveyStatus;
    }

    public void setSurveyStatus(String surveyStatus) {
        this.surveyStatus = surveyStatus;
    }

    public String getAddStamp() {
        return addStamp;
    }

    public void setAddStamp(String addStamp) {
        this.addStamp = addStamp;
    }

    public String getUpdateStamp() {
        return updateStamp;
    }

    public void setUpdateStamp(String updateStamp) {
        this.updateStamp = updateStamp;
    }

    public List<Document> getDocument() {
        return document;
    }

    public void setDocument(List<Document> document) {
        this.document = document;
    }

}
