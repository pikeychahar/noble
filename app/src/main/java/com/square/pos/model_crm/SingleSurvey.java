package com.square.pos.model_crm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SingleSurvey {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("surveryId")
    @Expose
    private String surveryId;
    @SerializedName("customerName")
    @Expose
    private String customerName;
    @SerializedName("customerEmail")
    @Expose
    private String customerEmail;
    @SerializedName("customerMobile")
    @Expose
    private String customerMobile;
    @SerializedName("customerEmailAlt")
    @Expose
    private String customerEmailAlt;
    @SerializedName("customerMobileAlt")
    @Expose
    private String customerMobileAlt;
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
    @SerializedName("employeeName")
    @Expose
    private String employeeName;
    @SerializedName("employeeMobile")
    @Expose
    private String employeeMobile;
    @SerializedName("agentName")
    @Expose
    private String agentName;
    @SerializedName("agentMobile")
    @Expose
    private String agentMobile;
    @SerializedName("product")
    @Expose
    private String product;
    @SerializedName("manufactureYear")
    @Expose
    private String manufactureYear;
    @SerializedName("insurer")
    @Expose
    private String insurer;
    @SerializedName("registrationNo")
    @Expose
    private String registrationNo;
    @SerializedName("rto")
    @Expose
    private String rto;
    @SerializedName("make")
    @Expose
    private String make;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("variant")
    @Expose
    private String variant;
    @SerializedName("engineNo")
    @Expose
    private String engineNo;
    @SerializedName("chassisNo")
    @Expose
    private String chassisNo;
    @SerializedName("insert_date")
    @Expose
    private String insertDate;
    @SerializedName("inspectionMode")
    @Expose
    private String inspectionMode;
    @SerializedName("rcFront")
    @Expose
    private String rcFront;
    @SerializedName("rcBack")
    @Expose
    private String rcBack;
    @SerializedName("quotationDoc")
    @Expose
    private String quotationDoc;
    @SerializedName("inspectionReportDoc")
    @Expose
    private String inspectionReportDoc;
    @SerializedName("addedBy")
    @Expose
    private String addedBy;
    @SerializedName("mappedTo")
    @Expose
    private String mappedTo;
    @SerializedName("curStatus")
    @Expose
    private String curStatus;
    @SerializedName("currentRemark")
    @Expose
    private String currentRemark;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSurveryId() {
        return surveryId;
    }

    public void setSurveryId(String surveryId) {
        this.surveryId = surveryId;
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

    public String getCustomerEmailAlt() {
        return customerEmailAlt;
    }

    public void setCustomerEmailAlt(String customerEmailAlt) {
        this.customerEmailAlt = customerEmailAlt;
    }

    public String getCustomerMobileAlt() {
        return customerMobileAlt;
    }

    public void setCustomerMobileAlt(String customerMobileAlt) {
        this.customerMobileAlt = customerMobileAlt;
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

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeMobile() {
        return employeeMobile;
    }

    public void setEmployeeMobile(String employeeMobile) {
        this.employeeMobile = employeeMobile;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentMobile() {
        return agentMobile;
    }

    public void setAgentMobile(String agentMobile) {
        this.agentMobile = agentMobile;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getManufactureYear() {
        return manufactureYear;
    }

    public void setManufactureYear(String manufactureYear) {
        this.manufactureYear = manufactureYear;
    }

    public String getInsurer() {
        return insurer;
    }

    public void setInsurer(String insurer) {
        this.insurer = insurer;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getRto() {
        return rto;
    }

    public void setRto(String rto) {
        this.rto = rto;
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

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public String getEngineNo() {
        return engineNo;
    }

    public void setEngineNo(String engineNo) {
        this.engineNo = engineNo;
    }

    public String getChassisNo() {
        return chassisNo;
    }

    public void setChassisNo(String chassisNo) {
        this.chassisNo = chassisNo;
    }

    public String getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(String insertDate) {
        this.insertDate = insertDate;
    }

    public String getInspectionMode() {
        return inspectionMode;
    }

    public void setInspectionMode(String inspectionMode) {
        this.inspectionMode = inspectionMode;
    }

    public String getRcFront() {
        return rcFront;
    }

    public void setRcFront(String rcFront) {
        this.rcFront = rcFront;
    }

    public String getRcBack() {
        return rcBack;
    }

    public void setRcBack(String rcBack) {
        this.rcBack = rcBack;
    }

    public String getQuotationDoc() {
        return quotationDoc;
    }

    public void setQuotationDoc(String quotationDoc) {
        this.quotationDoc = quotationDoc;
    }

    public String getInspectionReportDoc() {
        return inspectionReportDoc;
    }

    public void setInspectionReportDoc(String inspectionReportDoc) {
        this.inspectionReportDoc = inspectionReportDoc;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public String getMappedTo() {
        return mappedTo;
    }

    public void setMappedTo(String mappedTo) {
        this.mappedTo = mappedTo;
    }

    public String getCurStatus() {
        return curStatus;
    }

    public void setCurStatus(String curStatus) {
        this.curStatus = curStatus;
    }

    public String getCurrentRemark() {
        return currentRemark;
    }

    public void setCurrentRemark(String currentRemark) {
        this.currentRemark = currentRemark;
    }

}