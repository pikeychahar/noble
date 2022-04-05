package com.square.pos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("agent_id")
    @Expose
    private String agentId;
    @SerializedName("pos_status")
    @Expose
    private String posStatus;
    @SerializedName("create_pos_permission")
    @Expose
    private String createPosPermission;
    @SerializedName("create_mobile_permission")
    @Expose
    private String createMobilePermission;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("sp_id")
    @Expose
    private String spId;
    @SerializedName("employee_id")
    @Expose
    private String employeeId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("reference_id")
    @Expose
    private String referenceId;
    @SerializedName("alternate_contact_no")
    @Expose
    private String alternateContactNo;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("address1")
    @Expose
    private String address1;
    @SerializedName("address2")
    @Expose
    private String address2;
    @SerializedName("address3")
    @Expose
    private String address3;
    @SerializedName("pincode")
    @Expose
    private String pincode;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("city")
    @Expose
    private String city;

    @SerializedName("pos_agreement_status")
    @Expose
    private String posAgreement;

    @SerializedName("posBadge")
    @Expose
    private String posBadge;

    public String getPosBadge() {
        return posBadge;
    }

    @SerializedName("badgeColor")
    @Expose
    private String badgeColor;

    @SerializedName("token")
    @Expose
    private String token;

    public String getBadgeColor() {
        return badgeColor;
    }

    public void setBadgeColor(String badgeColor) {
        this.posBadge = posBadge;
    }

    public void setPosBadge(String posBadge) {
        this.posBadge = posBadge;
    }

    public String getPosAgreement() {
        return posAgreement;
    }

    public void setPosAgreement(String posAgreement) {
        this.posAgreement = posAgreement;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getPosStatus() {
        return posStatus;
    }

    public void setPosStatus(String posStatus) {
        this.posStatus = posStatus;
    }

    public String getCreatePosPermission() {
        return createPosPermission;
    }

    public void setCreatePosPermission(String createPosPermission) {
        this.createPosPermission = createPosPermission;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSpId() {
        return spId;
    }

    public void setSpId(String spId) {
        this.spId = spId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getAlternateContactNo() {
        return alternateContactNo;
    }

    public void setAlternateContactNo(String alternateContactNo) {
        this.alternateContactNo = alternateContactNo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
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

    public String getCreateMobilePermission() {
        return createMobilePermission;
    }

    public void setCreateMobilePermission(String createMobilePermission) {
        this.createMobilePermission = createMobilePermission;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}