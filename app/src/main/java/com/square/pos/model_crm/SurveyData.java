package com.square.pos.model_crm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SurveyData {

    @SerializedName("sno")
    @Expose
    private Integer sno;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("surveyId")
    @Expose
    private String surveyId;
    @SerializedName("customerName")
    @Expose
    private String customerName;
    @SerializedName("customerEmail")
    @Expose
    private String customerEmail;
    @SerializedName("customerMobile")
    @Expose
    private String customerMobile;
    @SerializedName("customerAddress")
    @Expose
    private String customerAddress;
    @SerializedName("vehicleLocation")
    @Expose
    private String vehicleLocation;
    @SerializedName("pincode")
    @Expose
    private String pincode;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("product")
    @Expose
    private String product;
    @SerializedName("make")
    @Expose
    private String make;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("AssignedTo")
    @Expose
    private String assignedTo;
    @SerializedName("AssignedToMobile")
    @Expose
    private String assignedToMobile;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("InsertDate")
    @Expose
    private String insertDate;

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    @SerializedName("registrationNo")
    @Expose
    private String vehicleNo;

    public Integer getSno() {
        return sno;
    }

    public void setSno(Integer sno) {
        this.sno = sno;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
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

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getVehicleLocation() {
        return vehicleLocation;
    }

    public void setVehicleLocation(String vehicleLocation) {
        this.vehicleLocation = vehicleLocation;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(String insertDate) {
        this.insertDate = insertDate;
    }

}