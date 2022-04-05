package com.square.pos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DocWallet {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("agent_user_id")
    @Expose
    private String agentUserId;
    @SerializedName("added_by")
    @Expose
    private String addedBy;
    @SerializedName("doc_type")
    @Expose
    private String docType;
    @SerializedName("policy_number")
    @Expose
    private String policyNumber;
    @SerializedName("vehicle_reg_no")
    @Expose
    private String vehicleRegNo;
    @SerializedName("rc_front_image")
    @Expose
    private String rcFrontImage;
    @SerializedName("rc_back_image")
    @Expose
    private String rcBackImage;
    @SerializedName("gst_document")
    @Expose
    private String gstDocument;
    @SerializedName("ins_document")
    @Expose
    private String insDocument;
    @SerializedName("other_document")
    @Expose
    private String otherDocument;
    @SerializedName("client_party_name")
    @Expose
    private String clientPartyName;
    @SerializedName("client_party_contact")
    @Expose
    private String clientPartyContact;
    @SerializedName("mandate_document")
    @Expose
    private String mandateDocument;
    @SerializedName("proposal_document")
    @Expose
    private String proposalDocument;
    @SerializedName("kyc_document")
    @Expose
    private String kycDocument;

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

    public String getAgentUserId() {
        return agentUserId;
    }

    public void setAgentUserId(String agentUserId) {
        this.agentUserId = agentUserId;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getVehicleRegNo() {
        return vehicleRegNo;
    }

    public void setVehicleRegNo(String vehicleRegNo) {
        this.vehicleRegNo = vehicleRegNo;
    }

    public String getRcFrontImage() {
        return rcFrontImage;
    }

    public void setRcFrontImage(String rcFrontImage) {
        this.rcFrontImage = rcFrontImage;
    }

    public String getRcBackImage() {
        return rcBackImage;
    }

    public void setRcBackImage(String rcBackImage) {
        this.rcBackImage = rcBackImage;
    }

    public String getGstDocument() {
        return gstDocument;
    }

    public void setGstDocument(String gstDocument) {
        this.gstDocument = gstDocument;
    }

    public String getInsDocument() {
        return insDocument;
    }

    public void setInsDocument(String insDocument) {
        this.insDocument = insDocument;
    }

    public String getOtherDocument() {
        return otherDocument;
    }

    public void setOtherDocument(String otherDocument) {
        this.otherDocument = otherDocument;
    }

    public String getClientPartyName() {
        return clientPartyName;
    }

    public void setClientPartyName(String clientPartyName) {
        this.clientPartyName = clientPartyName;
    }

    public String getClientPartyContact() {
        return clientPartyContact;
    }

    public void setClientPartyContact(String clientPartyContact) {
        this.clientPartyContact = clientPartyContact;
    }

    public String getMandateDocument() {
        return mandateDocument;
    }

    public void setMandateDocument(String mandateDocument) {
        this.mandateDocument = mandateDocument;
    }

    public String getProposalDocument() {
        return proposalDocument;
    }

    public void setProposalDocument(String proposalDocument) {
        this.proposalDocument = proposalDocument;
    }

    public String getKycDocument() {
        return kycDocument;
    }

    public void setKycDocument(String kycDocument) {
        this.kycDocument = kycDocument;
    }

}