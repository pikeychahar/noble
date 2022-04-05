package com.dmw.noble.model_health;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HealthQuote {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("lastInsertId")
    @Expose
    private String lastInsertId;
    @SerializedName("quoteId")
    @Expose
    private String quoteId;
    @SerializedName("controll_id")
    @Expose
    private String controllId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("plan_type")
    @Expose
    private String planType;
    @SerializedName("sumInsured")
    @Expose
    private String sumInsured;
    @SerializedName("pincode")
    @Expose
    private String pincode;
    @SerializedName("selfAge")
    @Expose
    private String selfAge;
    @SerializedName("spouseAge")
    @Expose
    private String spouseAge;
    @SerializedName("son")
    @Expose
    private String son;
    @SerializedName("daughter")
    @Expose
    private String daughter;
    @SerializedName("father")
    @Expose
    private String father;
    @SerializedName("mother")
    @Expose
    private String mother;
    @SerializedName("fatherinlaw")
    @Expose
    private String fatherinlaw;
    @SerializedName("motherinlaw")
    @Expose
    private String motherinlaw;

    @SerializedName("company")
    @Expose
    private String company;

    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    @SerializedName("pdf_url")
    @Expose
    private String pdfUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastInsertId() {
        return lastInsertId;
    }

    public void setLastInsertId(String lastInsertId) {
        this.lastInsertId = lastInsertId;
    }

    public String getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }

    public String getControllId() {
        return controllId;
    }

    public void setControllId(String controllId) {
        this.controllId = controllId;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public String getSumInsured() {
        return sumInsured;
    }

    public void setSumInsured(String sumInsured) {
        this.sumInsured = sumInsured;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getSelfAge() {
        return selfAge;
    }

    public void setSelfAge(String selfAge) {
        this.selfAge = selfAge;
    }

    public String getSpouseAge() {
        return spouseAge;
    }

    public void setSpouseAge(String spouseAge) {
        this.spouseAge = spouseAge;
    }

    public String getSon() {
        return son;
    }

    public void setSon(String son) {
        this.son = son;
    }

    public String getDaughter() {
        return daughter;
    }

    public void setDaughter(String daughter) {
        this.daughter = daughter;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getMother() {
        return mother;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public String getFatherinlaw() {
        return fatherinlaw;
    }

    public void setFatherinlaw(String fatherinlaw) {
        this.fatherinlaw = fatherinlaw;
    }

    public String getMotherinlaw() {
        return motherinlaw;
    }

    public void setMotherinlaw(String motherinlaw) {
        this.motherinlaw = motherinlaw;
    }

}