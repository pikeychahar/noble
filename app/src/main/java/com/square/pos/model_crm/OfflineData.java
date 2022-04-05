package com.square.pos.model_crm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OfflineData {

    @SerializedName("SrNo")
    @Expose
    private Integer srNo;
    @SerializedName("CustomerName")
    @Expose
    private String customerName;
    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("CustomerEmail")
    @Expose
    private String customerEmail;
    @SerializedName("CustomerMobile")
    @Expose
    private String customerMobile;
    @SerializedName("BusniessType")
    @Expose
    private String busniessType;
    @SerializedName("Quotation_Id")
    @Expose
    private String quotationId;
    @SerializedName("AssignUser")
    @Expose
    private String assignUser;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Vehicle_No")
    @Expose
    private String vehicleNo;
    @SerializedName("AssignUsertype")
    @Expose
    private String assignUsertype;
    @SerializedName("AssignUserId")
    @Expose
    private String assignUserId;
    @SerializedName("CreateDate")
    @Expose
    private String createDate;

    public Integer getSrNo() {
        return srNo;
    }

    public void setSrNo(Integer srNo) {
        this.srNo = srNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public String getBusniessType() {
        return busniessType;
    }

    public void setBusniessType(String busniessType) {
        this.busniessType = busniessType;
    }

    public String getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(String quotationId) {
        this.quotationId = quotationId;
    }

    public String getAssignUser() {
        return assignUser;
    }

    public void setAssignUser(String assignUser) {
        this.assignUser = assignUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getAssignUsertype() {
        return assignUsertype;
    }

    public void setAssignUsertype(String assignUsertype) {
        this.assignUsertype = assignUsertype;
    }

    public String getAssignUserId() {
        return assignUserId;
    }

    public void setAssignUserId(String assignUserId) {
        this.assignUserId = assignUserId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
