package com.square.pos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Owner {

    @SerializedName("status")
    @Expose
    private String success;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("agent_id")
    @Expose
    private String agentId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("quotation_id")
    @Expose
    private String quotationId;
    @SerializedName("salutation")
    @Expose
    private String salutation;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("middle_name")
    @Expose
    private String middleName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("registration_pincode")
    @Expose
    private String registrationPincode;
    @SerializedName("registration_city")
    @Expose
    private String registrationCity;
    @SerializedName("registration_address3")
    @Expose
    private String registrationAddress3;
    @SerializedName("registration_address2")
    @Expose
    private String registrationAddress2;
    @SerializedName("registration_address1")
    @Expose
    private String registrationAddress1;
    @SerializedName("registration_address")
    @Expose
    private String registrationAddress;
    @SerializedName("gst_no")
    @Expose
    private String gstNo;
    @SerializedName("pan_no")
    @Expose
    private String panNo;
    @SerializedName("pincode")
    @Expose
    private String pincode;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("address3")
    @Expose
    private String address3;
    @SerializedName("address2")
    @Expose
    private String address2;
    @SerializedName("address1")
    @Expose
    private String address1;
    @SerializedName("nationality")
    @Expose
    private String nationality;
    @SerializedName("additional_contact")
    @Expose
    private String additionalContact;
    @SerializedName("email_proposal")
    @Expose
    private String emailProposal;
    @SerializedName("mobile_proposal")
    @Expose
    private String mobileProposal;
    @SerializedName("occupation")
    @Expose
    private String occupation;
    @SerializedName("marital_status")
    @Expose
    private String maritalStatus;
    @SerializedName("gender")
    @Expose
    private String gender;

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

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(String quotationId) {
        this.quotationId = quotationId;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getRegistrationPincode() {
        return registrationPincode;
    }

    public void setRegistrationPincode(String registrationPincode) {
        this.registrationPincode = registrationPincode;
    }

    public String getRegistrationCity() {
        return registrationCity;
    }

    public void setRegistrationCity(String registrationCity) {
        this.registrationCity = registrationCity;
    }

    public String getRegistrationAddress3() {
        return registrationAddress3;
    }

    public void setRegistrationAddress3(String registrationAddress3) {
        this.registrationAddress3 = registrationAddress3;
    }

    public String getRegistrationAddress2() {
        return registrationAddress2;
    }

    public void setRegistrationAddress2(String registrationAddress2) {
        this.registrationAddress2 = registrationAddress2;
    }

    public String getRegistrationAddress1() {
        return registrationAddress1;
    }

    public void setRegistrationAddress1(String registrationAddress1) {
        this.registrationAddress1 = registrationAddress1;
    }

    public String getRegistrationAddress() {
        return registrationAddress;
    }

    public void setRegistrationAddress(String registrationAddress) {
        this.registrationAddress = registrationAddress;
    }

    public String getGstNo() {
        return gstNo;
    }

    public void setGstNo(String gstNo) {
        this.gstNo = gstNo;
    }

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getAdditionalContact() {
        return additionalContact;
    }

    public void setAdditionalContact(String additionalContact) {
        this.additionalContact = additionalContact;
    }

    public String getEmailProposal() {
        return emailProposal;
    }

    public void setEmailProposal(String emailProposal) {
        this.emailProposal = emailProposal;
    }

    public String getMobileProposal() {
        return mobileProposal;
    }

    public void setMobileProposal(String mobileProposal) {
        this.mobileProposal = mobileProposal;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}