package com.square.pos.model_crm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CancelledData implements Serializable {
    @SerializedName("SrNo")
    @Expose
    private Integer sNo;
    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("SrId")
    @Expose
    private String srId;
    @SerializedName("SR_NO")
    @Expose
    private String srNo;
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
    @SerializedName("Company")
    @Expose
    private String company;
    @SerializedName("DownloadUrl")
    @Expose
    private String downloadUrl;
    @SerializedName("Vehicle_No")
    @Expose
    private String vehicleNo;
    @SerializedName("Policy_Type")
    @Expose
    private String policyType;
    @SerializedName("AssignedTo")
    @Expose
    private String assignedTo;
    @SerializedName("AssignedToMobile")
    @Expose
    private String assignedToMobile;
    @SerializedName("InsertDate")
    @Expose
    private String insertDate;
    @SerializedName("cancelId")
    @Expose
    private String cancelId;
    @SerializedName("status")
    @Expose
    private String status;

    public Integer getSNo() {
        return sNo;
    }

    public void setSNo(Integer sNo) {
        this.sNo = sNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSrId() {
        return srId;
    }

    public void setSrId(String srId) {
        this.srId = srId;
    }

    public String getSrNo() {
        return srNo;
    }

    public void setSrNo(String srNo) {
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
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

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getAssignedToMobile() {
        return assignedToMobile;
    }

    public void setAssignedToMobile(String assignedToMobile) {
        this.assignedToMobile = assignedToMobile;
    }

    public String getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(String insertDate) {
        this.insertDate = insertDate;
    }

    public String getCancelId() {
        return cancelId;
    }

    public void setCancelId(String cancelId) {
        this.cancelId = cancelId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}