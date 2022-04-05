package com.square.pos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Review {

    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("quotation_id")
    @Expose
    private String quotationId;
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("idv")
    @Expose
    private String idv;
    @SerializedName("net_premium")
    @Expose
    private String netPremium;
    @SerializedName("tax")
    @Expose
    private String tax;
    @SerializedName("policy_start_date")
    @Expose
    private String policyStartDate;
    @SerializedName("policy_end_date")
    @Expose
    private String policyEndDate;
    @SerializedName("tenure")
    @Expose
    private String tenure;
    @SerializedName("premium")
    @Expose
    private String premium;
    @SerializedName("tp_only")
    @Expose
    private String tpOnly;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("gst_no")
    @Expose
    private String gstNo;
    @SerializedName("pan_no")
    @Expose
    private String panNo;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("email_proposal")
    @Expose
    private String emailProposal;
    @SerializedName("mobile_proposal")
    @Expose
    private String mobileProposal;
    @SerializedName("financier_city")
    @Expose
    private String financierCity;
    @SerializedName("financier_name")
    @Expose
    private String financierName;
    @SerializedName("hypothecation")
    @Expose
    private String hypothecation;
    @SerializedName("chassies_no")
    @Expose
    private String chassiesNo;
    @SerializedName("engine_no")
    @Expose
    private String engineNo;
    @SerializedName("pre_policy_no")
    @Expose
    private String prePolicyNo;
    @SerializedName("nominee_name")
    @Expose
    private String nomineeName;
    @SerializedName("nominee_relation")
    @Expose
    private String nomineeRelation;
    @SerializedName("nominee_dob")
    @Expose
    private String nomineeDob;
    @SerializedName("vehicle")
    @Expose
    private String vehicle;

    @SerializedName("registration_no")
    @Expose
    private String registrationNo;

    @SerializedName("new_gadi")
    @Expose
    private String newVehicle;

    @SerializedName("vehicleType")
    @Expose
    private String vehicleType;

    @SerializedName("breakingAllowed")
    @Expose
    private String breakingAllowed;

    @SerializedName("inspectionRaised")
    @Expose
    private String inspectionRaised;

    @SerializedName("doc_status")
    @Expose
    private String docStatus;

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getNewVehicle() {
        return newVehicle;
    }

    public void setNewVehicle(String newVehicle) {
        this.newVehicle = newVehicle;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getBreakingAllowed() {
        return breakingAllowed;
    }

    public String getInspectionRaised() {
        return inspectionRaised;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getDocStatus() {
        return docStatus;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(String quotationId) {
        this.quotationId = quotationId;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
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

    public String getNetPremium() {
        return netPremium;
    }

    public void setNetPremium(String netPremium) {
        this.netPremium = netPremium;
    }

    public String getTax() {
        return tax;
    }

    public String getPolicyStartDate() {
        return policyStartDate;
    }

    public void setPolicyStartDate(String policyStartDate) {
        this.policyStartDate = policyStartDate;
    }

    public String getPolicyEndDate() {
        return policyEndDate;
    }

    public void setPolicyEndDate(String policyEndDate) {
        this.policyEndDate = policyEndDate;
    }

    public String getTenure() {
        return tenure;
    }

    public void setTenure(String tenure) {
        this.tenure = tenure;
    }

    public String getPremium() {
        return premium;
    }

    public void setPremium(String premium) {
        this.premium = premium;
    }

    public String getTpOnly() {
        return tpOnly;
    }

    public void setTpOnly(String tpOnly) {
        this.tpOnly = tpOnly;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGstNo() {
        return gstNo;
    }

    public String getPanNo() {
        return panNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmailProposal() {
        return emailProposal;
    }

    public String getMobileProposal() {
        return mobileProposal;
    }

    public String getFinancierCity() {
        return financierCity;
    }

    public String getFinancierName() {
        return financierName;
    }

    public String getHypothecation() {
        return hypothecation;
    }

    public String getChassiesNo() {
        return chassiesNo;
    }

    public String getEngineNo() {
        return engineNo;
    }

    public String getPrePolicyNo() {
        return prePolicyNo;
    }

    public void setPrePolicyNo(String prePolicyNo) {
        this.prePolicyNo = prePolicyNo;
    }

    public String getNomineeName() {
        return nomineeName;
    }

    public String getNomineeRelation() {
        return nomineeRelation;
    }

    public void setNomineeRelation(String nomineeRelation) {
        this.nomineeRelation = nomineeRelation;
    }

    public String getNomineeDob() {
        return nomineeDob;
    }
}