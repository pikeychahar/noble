package com.dmw.noble.model_crm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EndorseData implements Serializable {

    @SerializedName("SrNo")
    @Expose
    private Integer srNo;
    @SerializedName("LOB")
    @Expose
    private String lob;
    @SerializedName("TypeName")
    @Expose
    private String typeName;
    @SerializedName("PolicyNo")
    @Expose
    private String policyNo;
    @SerializedName("CustomerName")
    @Expose
    private String customerName;
    @SerializedName("CustomerMobile")
    @Expose
    private String customerMobile;
    @SerializedName("DownloadUrl")
    @Expose
    private String downloadUrl;
    @SerializedName("Company")
    @Expose
    private String company;
    @SerializedName("Vehicle_No")
    @Expose
    private String vehicleNo;
    @SerializedName("Policy_Type")
    @Expose
    private String policyType;
    @SerializedName("Posting_Status_Web")
    @Expose
    private String postingStatusWeb;
    @SerializedName("SR_No")
    @Expose
    private String sRNo;
    @SerializedName("EncodedSrNo")
    @Expose
    private String encodedSrNo;

    public Integer getSrNo() {
        return srNo;
    }

    public void setSrNo(Integer srNo) {
        this.srNo = srNo;
    }

    public String getLob() {
        return lob;
    }

    public void setLob(String lob) {
        this.lob = lob;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getPolicyType() {
        return policyType;
    }

    public void setPolicyType(String policyType) {
        this.policyType = policyType;
    }

    public String getPostingStatusWeb() {
        return postingStatusWeb;
    }

    public void setPostingStatusWeb(String postingStatusWeb) {
        this.postingStatusWeb = postingStatusWeb;
    }

    public String getSRNo() {
        return sRNo;
    }

    public void setSRNo(String sRNo) {
        this.sRNo = sRNo;
    }

    public String getEncodedSrNo() {
        return encodedSrNo;
    }

    public void setEncodedSrNo(String encodedSrNo) {
        this.encodedSrNo = encodedSrNo;
    }

}