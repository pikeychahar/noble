package com.square.pos.model_pos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AgentDetail {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("alternate_contact_no")
    @Expose
    private String alternateContactNo;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("qualification")
    @Expose
    private String qualification;
    @SerializedName("pancard_no")
    @Expose
    private String pancardNo;
    @SerializedName("aadharcard_no")
    @Expose
    private String aadharcardNo;
    @SerializedName("address1")
    @Expose
    private String address1;
    @SerializedName("address2")
    @Expose
    private String address2;
    @SerializedName("address3")
    @Expose
    private String address3;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("pincode")
    @Expose
    private String pincode;
    @SerializedName("bank_id")
    @Expose
    private String bankId;
    @SerializedName("bank_name")
    @Expose
    private String bankName;
    @SerializedName("branch_name")
    @Expose
    private String branchName;
    @SerializedName("account_type_id")
    @Expose
    private String accountTypeId;
    @SerializedName("account_type")
    @Expose
    private String accountType;
    @SerializedName("account_no")
    @Expose
    private String accountNo;
    @SerializedName("ifsc")
    @Expose
    private String ifsc;
    @SerializedName("refreance")
    @Expose
    private String refreance;
    @SerializedName("certificate")
    @Expose
    private String certificate;
    @SerializedName("pancard_image")
    @Expose
    private String pancardImage;
    @SerializedName("aadharcard_image")
    @Expose
    private String aadharcardImage;
    @SerializedName("aadharcard_image_back")
    @Expose
    private String aadharcardImageBack;
    @SerializedName("qualification_image")
    @Expose
    private String qualificationImage;
    @SerializedName("cheque_image")
    @Expose
    private String chequeImage;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("signature_image")
    @Expose
    private String signatureImage;

    @SerializedName("rm_name")
    @Expose
    private String rmName;

    @SerializedName("password_update_date")
    @Expose
    private Integer changedPassDays;

    @SerializedName("rm_contact")
    @Expose
    private String rmContact;

    @SerializedName("pos_status")
    @Expose
    private String posStatus;

    public String getCreateMobilePermission() {
        return createMobilePermission;
    }

    public void setCreateMobilePermission(String createMobilePermission) {
        this.createMobilePermission = createMobilePermission;
    }

    @SerializedName("create_mobile_permission")
    @Expose
    private String createMobilePermission;


    @SerializedName("badgeColor")
    @Expose
    private String badgeColor;

    public String getBadgeColor() {
        return badgeColor;
    }

    public void setBadgeColor(String badgeColor) {
        this.posBadge = posBadge;
    }

    public String getPosBadge() {
        return posBadge;
    }

    public void setPosBadge(String posBadge) {
        this.posBadge = posBadge;
    }

    @SerializedName("posBadge")
    @Expose
    private String posBadge;

    public String getRmName() {
        return rmName;
    }

    public void setRmName(String rmName) {
        this.rmName = rmName;
    }

    public String getRmContact() {
        return rmContact;
    }

    public void setRmContact(String rmContact) {
        this.rmContact = rmContact;
    }

    public Integer getChangedPassDays() {
        return changedPassDays;
    }

    public void setChangedPassDays(Integer changedPassDays) {
        this.changedPassDays = changedPassDays;
    }

    public String getPosStatus() {
        return posStatus;
    }

    public void setPosStatus(String posStatus) {
        this.posStatus = posStatus;
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

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getPancardNo() {
        return pancardNo;
    }

    public void setPancardNo(String pancardNo) {
        this.pancardNo = pancardNo;
    }

    public String getAadharcardNo() {
        return aadharcardNo;
    }

    public void setAadharcardNo(String aadharcardNo) {
        this.aadharcardNo = aadharcardNo;
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

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getAccountTypeId() {
        return accountTypeId;
    }

    public void setAccountTypeId(String accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getRefreance() {
        return refreance;
    }

    public void setRefreance(String refreance) {
        this.refreance = refreance;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getPancardImage() {
        return pancardImage;
    }

    public void setPancardImage(String pancardImage) {
        this.pancardImage = pancardImage;
    }

    public String getAadharcardImage() {
        return aadharcardImage;
    }

    public void setAadharcardImage(String aadharcardImage) {
        this.aadharcardImage = aadharcardImage;
    }

    public String getAadharcardImageBack() {
        return aadharcardImageBack;
    }

    public void setAadharcardImageBack(String aadharcardImageBack) {
        this.aadharcardImageBack = aadharcardImageBack;
    }

    public String getQualificationImage() {
        return qualificationImage;
    }

    public void setQualificationImage(String qualificationImage) {
        this.qualificationImage = qualificationImage;
    }

    public String getChequeImage() {
        return chequeImage;
    }

    public void setChequeImage(String chequeImage) {
        this.chequeImage = chequeImage;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSignatureImage() {
        return signatureImage;
    }

    public void setSignatureImage(String signatureImage) {
        this.signatureImage = signatureImage;
    }

}