package com.square.pos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Filter {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("pa_cover")
    @Expose
    private String paCover;
    @SerializedName("zero_dept")
    @Expose
    private String zeroDept;
    @SerializedName("cover_for")
    @Expose
    private String coverFor;
    @SerializedName("idv")
    @Expose
    private String idv;
    @SerializedName("tp_only")
    @Expose
    private String tpOnly;
    @SerializedName("cover")
    @Expose
    private String cover;
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

    public String getPaCover() {
        return paCover;
    }

    public void setPaCover(String paCover) {
        this.paCover = paCover;
    }

    public String getZeroDept() {
        return zeroDept;
    }

    public void setZeroDept(String zeroDept) {
        this.zeroDept = zeroDept;
    }

    public String getCoverFor() {
        return coverFor;
    }

    public void setCoverFor(String coverFor) {
        this.coverFor = coverFor;
    }

    public String getIdv() {
        return idv;
    }

    public void setIdv(String idv) {
        this.idv = idv;
    }

    public String getTpOnly() {
        return tpOnly;
    }

    public void setTpOnly(String tpOnly) {
        this.tpOnly = tpOnly;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
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