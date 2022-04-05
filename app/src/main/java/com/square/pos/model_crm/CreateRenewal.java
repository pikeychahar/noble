package com.square.pos.model_crm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateRenewal {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("Url")
    @Expose
    private String url;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("lastInsertId")
    @Expose
    private String lastInsertId;
    @SerializedName("quotation_id")
    @Expose
    private String quotationId;
    @SerializedName("controll_id")
    @Expose
    private String controllId;
    @SerializedName("registration_year")
    @Expose
    private String registrationYear;
    @SerializedName("new_gadi")
    @Expose
    private String newGadi;
    @SerializedName("policy_start_date")
    @Expose
    private String policyStartDate;
    @SerializedName("policy_type")
    @Expose
    private String policyType;
    @SerializedName("policy_expiery")
    @Expose
    private String policyExpiery;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("registration_no")
    @Expose
    private String registrationNo;
    @SerializedName("isPrevious")
    @Expose
    private String isPrevious;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("vehicle_type")
    @Expose
    private String vehicleType;
    @SerializedName("tp_only")
    @Expose
    private String tpOnly;
    @SerializedName("previous_insurer")
    @Expose
    private String previousInsurer;
    @SerializedName("tp_expire_policy_cpmpany")
    @Expose
    private String tpExpirePolicyCpmpany;
    @SerializedName("tp_expire_policy_number")
    @Expose
    private String tpExpirePolicyNumber;
    @SerializedName("tp_policy_expire_date")
    @Expose
    private String tpPolicyExpireDate;
    @SerializedName("manufacture_date")
    @Expose
    private String manufactureDate;
    @SerializedName("registration_date")
    @Expose
    private String registrationDate;
    @SerializedName("purchase_date")
    @Expose
    private String purchaseDate;
    @SerializedName("policy_expiery_date")
    @Expose
    private String policyExpieryDate;
    @SerializedName("ncb_old")
    @Expose
    private String ncbOld;
    @SerializedName("company_id")
    @Expose
    private String companyId;
    @SerializedName("owner_change")
    @Expose
    private String ownerChange;
    @SerializedName("claim_expiring_policy")
    @Expose
    private String claimExpiringPolicy;
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
    @SerializedName("vehicle")
    @Expose
    private String vehicle;
    @SerializedName("sbiCode")
    @Expose
    private String sbiCode;
    @SerializedName("pcvCompany")
    @Expose
    private String pcvCompany;

    public String getSbiCode() {
        return sbiCode;
    }
    @SerializedName("pcvType")
    @Expose
    private String pcvType;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

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

    public String getRegistrationYear() {
        return registrationYear;
    }

    public void setRegistrationYear(String registrationYear) {
        this.registrationYear = registrationYear;
    }

    public String getNewGadi() {
        return newGadi;
    }

    public void setNewGadi(String newGadi) {
        this.newGadi = newGadi;
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

    public String getPolicyExpiery() {
        return policyExpiery;
    }

    public void setPolicyExpiery(String policyExpiery) {
        this.policyExpiery = policyExpiery;
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

    public String getIsPrevious() {
        return isPrevious;
    }

    public void setIsPrevious(String isPrevious) {
        this.isPrevious = isPrevious;
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

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getTpOnly() {
        return tpOnly;
    }

    public void setTpOnly(String tpOnly) {
        this.tpOnly = tpOnly;
    }

    public String getPreviousInsurer() {
        return previousInsurer;
    }

    public void setPreviousInsurer(String previousInsurer) {
        this.previousInsurer = previousInsurer;
    }

    public String getTpExpirePolicyCpmpany() {
        return tpExpirePolicyCpmpany;
    }

    public void setTpExpirePolicyCpmpany(String tpExpirePolicyCpmpany) {
        this.tpExpirePolicyCpmpany = tpExpirePolicyCpmpany;
    }

    public String getTpExpirePolicyNumber() {
        return tpExpirePolicyNumber;
    }

    public void setTpExpirePolicyNumber(String tpExpirePolicyNumber) {
        this.tpExpirePolicyNumber = tpExpirePolicyNumber;
    }

    public String getTpPolicyExpireDate() {
        return tpPolicyExpireDate;
    }

    public void setTpPolicyExpireDate(String tpPolicyExpireDate) {
        this.tpPolicyExpireDate = tpPolicyExpireDate;
    }

    public String getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(String manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getPolicyExpieryDate() {
        return policyExpieryDate;
    }

    public void setPolicyExpieryDate(String policyExpieryDate) {
        this.policyExpieryDate = policyExpieryDate;
    }

    public String getNcbOld() {
        return ncbOld;
    }

    public void setNcbOld(String ncbOld) {
        this.ncbOld = ncbOld;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getOwnerChange() {
        return ownerChange;
    }

    public void setOwnerChange(String ownerChange) {
        this.ownerChange = ownerChange;
    }

    public String getClaimExpiringPolicy() {
        return claimExpiringPolicy;
    }

    public void setClaimExpiringPolicy(String claimExpiringPolicy) {
        this.claimExpiringPolicy = claimExpiringPolicy;
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

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public void setSbiCode(String sbiCode) {
        this.sbiCode = sbiCode;
    }

    public String getPcvCompany() {
        return pcvCompany;
    }

    public void setPcvCompany(String pcvCompany) {
        this.pcvCompany = pcvCompany;
    }

    public String getPcvType() {
        return pcvType;
    }

    public void setPcvType(String pcvType) {
        this.pcvType = pcvType;
    }
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}