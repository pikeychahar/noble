package com.square.pos.model_pos;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.square.pos.activity_pos.PreviousPolicyDocument;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.List;

public class Lead implements Serializable {


    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("leadId")
    @Expose
    private String leadId;
    @SerializedName("agent_id")
    @Expose
    private String agentId;
    @SerializedName("sp_id")
    @Expose
    private String spId;
    @SerializedName("employee_id")
    @Expose
    private String employeeId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("gadi_type")
    @Expose
    private String gadiType;
    @SerializedName("gadi_no")
    @Expose
    private String gadiNo;
    @SerializedName("file_type")
    @Expose
    private String fileType;
    @SerializedName("reg_year")
    @Expose
    private String regYear;
    @SerializedName("make")
    @Expose
    private String make;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("fuel_type")
    @Expose
    private String fuelType;
    @SerializedName("variant")
    @Expose
    private String variant;
    @SerializedName("policy_type")
    @Expose
    private String policyType;
    @SerializedName("previous_ins")
    @Expose
    private String previousIns;
    @SerializedName("policy_expiry_duration")
    @Expose
    private String policyExpiryDuration;
    @SerializedName("rc_front_image")
    @Expose
    private String rcFrontImage;
    @SerializedName("rc_back_image")
    @Expose
    private String rcBackImage;
    @SerializedName("invoice_image")
    @Expose
    private String invoiceImage;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("corporate_ins_type")
    @Expose
    private String corporateInsType;
    @SerializedName("organisation_name")
    @Expose
    private String organisationName;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("health_policy_type")
    @Expose
    private String healthPolicyType;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("pincode")
    @Expose
    private String pincode;
    @SerializedName("sum_assured")
    @Expose
    private String sumAssured;
    @SerializedName("plan_type")
    @Expose
    private String planType;
    @SerializedName("self_age")
    @Expose
    private String selfAge;
    @SerializedName("sons_age")
    @Expose
    private String sonsAge;
    @SerializedName("father_age")
    @Expose
    private String fatherAge;
    @SerializedName("mother_age")
    @Expose
    private String motherAge;
    @SerializedName("daughter_age")
    @Expose
    private String daughterAge;
    @SerializedName("expiry_date")
    @Expose
    private String expiryDate;
    @SerializedName("any_disease")
    @Expose
    private String anyDisease;
    @SerializedName("remarks")
    @Expose
    private String remarks;
    @SerializedName("kyc_documents")
    @Expose
    private String kycDocuments;
    @SerializedName("other_documents")
    @Expose
    private String otherDocuments;
    @SerializedName("previous_policy_documents")
    @Expose
    private List<PreviousPolicyDocument> previousPolicyDocuments = null;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("mapped_branch")
    @Expose
    private String mappedBranch;
    @SerializedName("mapped_employee")
    @Expose
    private String mappedEmployee;
    @SerializedName("added_by_employee")
    @Expose
    private String addedByEmployee;
    @SerializedName("in_process_status")
    @Expose
    private String inProcessStatus;
    @SerializedName("in_process_time")
    @Expose
    private String inProcessTime;
    @SerializedName("policy_premium")
    @Expose
    private String policyPremium;
    @SerializedName("policy_company_name")
    @Expose
    private String policyCompanyName;
    @SerializedName("policy_pdf")
    @Expose
    private String policyPdf;
    @SerializedName("rejection_reason")
    @Expose
    private String rejectionReason;
    @SerializedName("assigned_to_name")
    @Expose
    private String assignedToName;
    @SerializedName("assigned_to_mobile")
    @Expose
    private String assignedToMobile;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("insert_date")
    @Expose
    private String insertDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLeadId() {
        return leadId;
    }

    public void setLeadId(String leadId) {
        this.leadId = leadId;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getSpId() {
        return spId;
    }

    public void setSpId(String spId) {
        this.spId = spId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGadiType() {
        return gadiType;
    }

    public void setGadiType(String gadiType) {
        this.gadiType = gadiType;
    }

    public String getGadiNo() {
        return gadiNo;
    }

    public void setGadiNo(String gadiNo) {
        this.gadiNo = gadiNo;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getRegYear() {
        return regYear;
    }

    public void setRegYear(String regYear) {
        this.regYear = regYear;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public String getPolicyType() {
        return policyType;
    }

    public void setPolicyType(String policyType) {
        this.policyType = policyType;
    }

    public String getPreviousIns() {
        return previousIns;
    }

    public void setPreviousIns(String previousIns) {
        this.previousIns = previousIns;
    }

    public String getPolicyExpiryDuration() {
        return policyExpiryDuration;
    }

    public void setPolicyExpiryDuration(String policyExpiryDuration) {
        this.policyExpiryDuration = policyExpiryDuration;
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

    public String getInvoiceImage() {
        return invoiceImage;
    }

    public void setInvoiceImage(String invoiceImage) {
        this.invoiceImage = invoiceImage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCorporateInsType() {
        return corporateInsType;
    }

    public void setCorporateInsType(String corporateInsType) {
        this.corporateInsType = corporateInsType;
    }

    public String getOrganisationName() {
        return organisationName;
    }

    public void setOrganisationName(String organisationName) {
        this.organisationName = organisationName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHealthPolicyType() {
        return healthPolicyType;
    }

    public void setHealthPolicyType(String healthPolicyType) {
        this.healthPolicyType = healthPolicyType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getSumAssured() {
        return sumAssured;
    }

    public void setSumAssured(String sumAssured) {
        this.sumAssured = sumAssured;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public String getSelfAge() {
        return selfAge;
    }

    public void setSelfAge(String selfAge) {
        this.selfAge = selfAge;
    }

    public String getSonsAge() {
        return sonsAge;
    }

    public void setSonsAge(String sonsAge) {
        this.sonsAge = sonsAge;
    }

    public String getFatherAge() {
        return fatherAge;
    }

    public void setFatherAge(String fatherAge) {
        this.fatherAge = fatherAge;
    }

    public String getMotherAge() {
        return motherAge;
    }

    public void setMotherAge(String motherAge) {
        this.motherAge = motherAge;
    }

    public String getDaughterAge() {
        return daughterAge;
    }

    public void setDaughterAge(String daughterAge) {
        this.daughterAge = daughterAge;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getAnyDisease() {
        return anyDisease;
    }

    public void setAnyDisease(String anyDisease) {
        this.anyDisease = anyDisease;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getKycDocuments() {
        return kycDocuments;
    }

    public void setKycDocuments(String kycDocuments) {
        this.kycDocuments = kycDocuments;
    }

    public String getOtherDocuments() {
        return otherDocuments;
    }

    public void setOtherDocuments(String otherDocuments) {
        this.otherDocuments = otherDocuments;
    }

    public List<PreviousPolicyDocument> getPreviousPolicyDocuments() {
        return previousPolicyDocuments;
    }

    public void setPreviousPolicyDocuments(List<PreviousPolicyDocument> previousPolicyDocuments) {
        this.previousPolicyDocuments = previousPolicyDocuments;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getMappedBranch() {
        return mappedBranch;
    }

    public void setMappedBranch(String mappedBranch) {
        this.mappedBranch = mappedBranch;
    }

    public String getMappedEmployee() {
        return mappedEmployee;
    }

    public void setMappedEmployee(String mappedEmployee) {
        this.mappedEmployee = mappedEmployee;
    }

    public String getAddedByEmployee() {
        return addedByEmployee;
    }

    public void setAddedByEmployee(String addedByEmployee) {
        this.addedByEmployee = addedByEmployee;
    }

    public String getInProcessStatus() {
        return inProcessStatus;
    }

    public void setInProcessStatus(String inProcessStatus) {
        this.inProcessStatus = inProcessStatus;
    }

    public String getInProcessTime() {
        return inProcessTime;
    }

    public void setInProcessTime(String inProcessTime) {
        this.inProcessTime = inProcessTime;
    }

    public String getPolicyPremium() {
        return policyPremium;
    }

    public void setPolicyPremium(String policyPremium) {
        this.policyPremium = policyPremium;
    }

    public String getPolicyCompanyName() {
        return policyCompanyName;
    }

    public void setPolicyCompanyName(String policyCompanyName) {
        this.policyCompanyName = policyCompanyName;
    }

    public String getPolicyPdf() {
        return policyPdf;
    }

    public void setPolicyPdf(String policyPdf) {
        this.policyPdf = policyPdf;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public String getAssignedToName() {
        return assignedToName;
    }

    public void setAssignedToName(String assignedToName) {
        this.assignedToName = assignedToName;
    }

    public String getAssignedToMobile() {
        return assignedToMobile;
    }

    public void setAssignedToMobile(String assignedToMobile) {
        this.assignedToMobile = assignedToMobile;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(String insertDate) {
        this.insertDate = insertDate;
    }

}