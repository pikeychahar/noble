package com.square.pos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtherInformation {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("manufacture_date")
    @Expose
    private String manufactureDate;
    @SerializedName("purchase_date")
    @Expose
    private String purchaseDate;
    @SerializedName("registration_date")
    @Expose
    private String registrationDate;
    @SerializedName("policy_expiery_date")
    @Expose
    private String policyExpieryDate;
    @SerializedName("vehicle_owned_by")
    @Expose
    private String vehicleOwnedBy;
    @SerializedName("owner_change")
    @Expose
    private String ownerChange;
    @SerializedName("claim_expiring_policy")
    @Expose
    private String claimExpiringPolicy;
    @SerializedName("ncb_old")
    @Expose
    private String ncbOld;

    @SerializedName("query")
    @Expose
    private String query;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

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

    public String getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(String manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getPolicyExpieryDate() {
        return policyExpieryDate;
    }

    public void setPolicyExpieryDate(String policyExpieryDate) {
        this.policyExpieryDate = policyExpieryDate;
    }

    public String getVehicleOwnedBy() {
        return vehicleOwnedBy;
    }

    public void setVehicleOwnedBy(String vehicleOwnedBy) {
        this.vehicleOwnedBy = vehicleOwnedBy;
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

    public String getNcbOld() {
        return ncbOld;
    }

    public void setNcbOld(String ncbOld) {
        this.ncbOld = ncbOld;
    }

}