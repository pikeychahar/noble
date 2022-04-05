package com.square.pos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DocsWallet implements Serializable {

    @SerializedName("added_by")
    @Expose
    private String addedBy;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("agent_id")
    @Expose
    private String agentId;
    @SerializedName("doc_id")
    @Expose
    private String docId;
    @SerializedName("policy_no")
    @Expose
    private String policyNo;
    @SerializedName("vehicle_reg_no")
    @Expose
    private String vehicleRegNo;
    @SerializedName("client_party_name")
    @Expose
    private String clientPartyName;
    @SerializedName("client_party_contact")
    @Expose
    private String clientPartyContact;
    @SerializedName("gst_document")
    @Expose
    private String gstDocument;
    @SerializedName("rc_front_image")
    @Expose
    private String rcFrontImage;
    @SerializedName("rc_back_image")
    @Expose
    private String rcBackImage;
    @SerializedName("ins_document")
    @Expose
    private String insDocument;
    @SerializedName("other_document")
    @Expose
    private String otherDocument;
    @SerializedName("mandate_document")
    @Expose
    private String mandateDocument;
    @SerializedName("proposal_document")
    @Expose
    private String proposalDocument;
    @SerializedName("kyc_document")
    @Expose
    private String kycDocument;
    @SerializedName("insert_date")
    @Expose
    private String insertDate;

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

    public String getVehicleRegNo() {
        return vehicleRegNo;
    }

    public void setVehicleRegNo(String vehicleRegNo) {
        this.vehicleRegNo = vehicleRegNo;
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

    public String getGstDocument() {
        return gstDocument;
    }

    public void setGstDocument(String gstDocument) {
        this.gstDocument = gstDocument;
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

    public String getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(String insertDate) {
        this.insertDate = insertDate;
    }

}