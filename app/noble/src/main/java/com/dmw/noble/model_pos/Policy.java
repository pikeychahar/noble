package com.dmw.noble.model_pos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Policy {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("quotation_id")
    @Expose
    private String quotationId;
    @SerializedName("policy_start_date")
    @Expose
    private String policyStartDate;
    @SerializedName("policy_type")
    @Expose
    private String policyType;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("registration_no")
    @Expose
    private String registrationNo;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("vehicle")
    @Expose
    private String vehicle;
    @SerializedName("pdf")
    @Expose
    private String pdf;
    public Integer getRemainingDays() {
        return remainingDays;
    }

    public void setRemainingDays(Integer remainingDays) {
        this.remainingDays = remainingDays;
    }

    @SerializedName("remaing_days")
    @Expose
    private Integer remainingDays;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(String quotationId) {
        this.quotationId = quotationId;
    }

    public String getPolicyStartDate() {
        return policyStartDate;
    }

    public void setPolicyStartDate(String policyStartDate) {
        this.policyStartDate = policyStartDate;
    }

    public String getPolicyType() {
        return policyType;
    }

    public void setPolicyType(String policyType) {
        this.policyType = policyType;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

}