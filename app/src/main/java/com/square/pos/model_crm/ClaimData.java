package com.square.pos.model_crm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClaimData {

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
@SerializedName("PolicyType")
@Expose
private String policyType;
@SerializedName("LossType")
@Expose
private String lossType;
@SerializedName("Date_Time_Lose")
@Expose
private String dateTimeLose;
@SerializedName("Intimated_To_Insurer")
@Expose
private String intimatedToInsurer;
@SerializedName("Survey_Status")
@Expose
private String surveyStatus;
@SerializedName("Add_Stamp")
@Expose
private String addStamp;
@SerializedName("Status")
@Expose
private String status;
@SerializedName("Mobile_No")
@Expose
private String mobileNo;
@SerializedName("PlaceOfAccident")
@Expose
private String placeOfAccident;
@SerializedName("NearByLandMark")
@Expose
private String nearByLandMark;
@SerializedName("AccidentalPincode")
@Expose
private String accidentalPincode;
@SerializedName("Address")
@Expose
private String address;
@SerializedName("Intimation_Date_Time")
@Expose
private String intimationDateTime;
@SerializedName("Claim_Intimation_No")
@Expose
private String claimIntimationNo;
@SerializedName("Survey_Date_Time")
@Expose
private String surveyDateTime;
@SerializedName("Surveyor_Name")
@Expose
private String surveyorName;
@SerializedName("Surveyor_Mobile")
@Expose
private String surveyorMobile;
@SerializedName("Update_Stamp")
@Expose
private String updateStamp;
@SerializedName("Document")
@Expose
private List<Document> document = null;

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

public String getPolicyType() {
return policyType;
}

public void setPolicyType(String policyType) {
this.policyType = policyType;
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

public String getMobileNo() {
return mobileNo;
}

public void setMobileNo(String mobileNo) {
this.mobileNo = mobileNo;
}

public String getPlaceOfAccident() {
return placeOfAccident;
}

public void setPlaceOfAccident(String placeOfAccident) {
this.placeOfAccident = placeOfAccident;
}

public String getNearByLandMark() {
return nearByLandMark;
}

public void setNearByLandMark(String nearByLandMark) {
this.nearByLandMark = nearByLandMark;
}

public String getAccidentalPincode() {
return accidentalPincode;
}

public void setAccidentalPincode(String accidentalPincode) {
this.accidentalPincode = accidentalPincode;
}

public String getAddress() {
return address;
}

public void setAddress(String address) {
this.address = address;
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

public String getSurveyDateTime() {
return surveyDateTime;
}

public void setSurveyDateTime(String surveyDateTime) {
this.surveyDateTime = surveyDateTime;
}

public String getSurveyorName() {
return surveyorName;
}

public void setSurveyorName(String surveyorName) {
this.surveyorName = surveyorName;
}

public String getSurveyorMobile() {
return surveyorMobile;
}

public void setSurveyorMobile(String surveyorMobile) {
this.surveyorMobile = surveyorMobile;
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
