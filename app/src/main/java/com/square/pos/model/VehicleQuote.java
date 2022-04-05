package com.square.pos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VehicleQuote {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("quotation_id")
    @Expose
    private String quotationId;
    @SerializedName("controll_id")
    @Expose
    private String controllId;
    @SerializedName("registration_no")
    @Expose
    private String registrationNo;
    @SerializedName("gadi_type")
    @Expose
    private String gadiType;
    @SerializedName("make")
    @Expose
    private String make;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("fuel_type")
    @Expose
    private String fuelType;
    @SerializedName("variant")
    @Expose
    private String variant;
    @SerializedName("reg_year")
    @Expose
    private String regYear;
    @SerializedName("previous_insurer")
    @Expose
    private String previousInsurer;
    @SerializedName("policy_type")
    @Expose
    private String policyType;
    @SerializedName("policy_expiery")
    @Expose
    private String policyExpiery;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("dont_know")
    @Expose
    private String dontKnow;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("type_id")
    @Expose
    private String typeId;
    @SerializedName("insertId")
    @Expose
    private String insertId;

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

    public String getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(String quotationId) {
        this.quotationId = quotationId;
    }

    public String getControllId() {
        return controllId;
    }

    public void setControllId(String controllId) {
        this.controllId = controllId;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getGadiType() {
        return gadiType;
    }

    public void setGadiType(String gadiType) {
        this.gadiType = gadiType;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
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

    public String getRegYear() {
        return regYear;
    }

    public void setRegYear(String regYear) {
        this.regYear = regYear;
    }

    public String getPreviousInsurer() {
        return previousInsurer;
    }

    public void setPreviousInsurer(String previousInsurer) {
        this.previousInsurer = previousInsurer;
    }

    public String getPolicyType() {
        return policyType;
    }

    public void setPolicyType(String policyType) {
        this.policyType = policyType;
    }

    public String getPolicyExpiery() {
        return policyExpiery;
    }

    public void setPolicyExpiery(String policyExpiery) {
        this.policyExpiery = policyExpiery;
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

    public String getDontKnow() {
        return dontKnow;
    }

    public void setDontKnow(String dontKnow) {
        this.dontKnow = dontKnow;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getInsertId() {
        return insertId;
    }

    public void setInsertId(String insertId) {
        this.insertId = insertId;
    }

}