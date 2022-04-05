package com.dmw.noble.model_life;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LifeProposal implements Serializable {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("title_value")
    @Expose
    private String titleValue;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("gender_value")
    @Expose
    private String genderValue;
    @SerializedName("marrital_status")
    @Expose
    private String marritalStatus;
    @SerializedName("marrital_status_value")
    @Expose
    private String marritalStatusValue;
    @SerializedName("proposer_mobile")
    @Expose
    private String proposerMobile;
    @SerializedName("proposer_email")
    @Expose
    private String proposerEmail;
    @SerializedName("annual_income")
    @Expose
    private String annualIncome;
    @SerializedName("occuption")
    @Expose
    private String occuption;
    @SerializedName("occuption_value")
    @Expose
    private String occuptionValue;
    @SerializedName("education")
    @Expose
    private String education;
    @SerializedName("education_value")
    @Expose
    private String educationValue;
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
    @SerializedName("pincode")
    @Expose
    private String pincode;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("nominee_title")
    @Expose
    private String nomineeTitle;
    @SerializedName("nominee_title_value")
    @Expose
    private String nomineeTitleValue;
    @SerializedName("nominee_first_name")
    @Expose
    private String nomineeFirstName;
    @SerializedName("nominee_last_name")
    @Expose
    private String nomineeLastName;
    @SerializedName("nominee_gender")
    @Expose
    private String nomineeGender;
    @SerializedName("nominee_gender_value")
    @Expose
    private String nomineeGenderValue;
    @SerializedName("nominee_marrital_status")
    @Expose
    private String nomineeMarritalStatus;
    @SerializedName("nominee_marrital_status_value")
    @Expose
    private String nomineeMarritalStatusValue;
    @SerializedName("nominee_relation")
    @Expose
    private String nomineeRelation;
    @SerializedName("nominee_relation_value")
    @Expose
    private String nomineeRelationValue;
    @SerializedName("nominee_mobile_no")
    @Expose
    private String nomineeMobileNo;
    @SerializedName("nominee_dob")
    @Expose
    private String nomineeDob;
    @SerializedName("appointee_title")
    @Expose
    private String appointeeTitle;
    @SerializedName("appointee_title_value")
    @Expose
    private String appointeeTitleValue;
    @SerializedName("appointee_first_name")
    @Expose
    private String appointeeFirstName;
    @SerializedName("appointee_last_name")
    @Expose
    private String appointeeLastName;
    @SerializedName("appointee_gender")
    @Expose
    private String appointeeGender;
    @SerializedName("appointee_gender_value")
    @Expose
    private String appointeeGenderValue;
    @SerializedName("appointee_marrital_status")
    @Expose
    private String appointeeMarritalStatus;
    @SerializedName("appointee_marrital_status_value")
    @Expose
    private String appointeeMarritalStatusValue;
    @SerializedName("appointee_relation")
    @Expose
    private String appointeeRelation;
    @SerializedName("appointee_relation_value")
    @Expose
    private String appointeeRelationValue;
    @SerializedName("appointee_mobile_no")
    @Expose
    private String appointeeMobileNo;
    @SerializedName("appointee_dob")
    @Expose
    private String appointeeDob;
    @SerializedName("businessBelongs")
    @Expose
    private String businessBelongs;
    @SerializedName("businessBelongs_value")
    @Expose
    private String businessBelongsValue;
    @SerializedName("existingCover")
    @Expose
    private String existingCover;
    @SerializedName("EmpName")
    @Expose
    private String empName;
    @SerializedName("EmpAddress")
    @Expose
    private String empAddress;
    @SerializedName("EmpSector")
    @Expose
    private String empSector;
    @SerializedName("spouseName")
    @Expose
    private String spouseName;
    @SerializedName("net")
    @Expose
    private Integer net;
    @SerializedName("tax")
    @Expose
    private Integer tax;
    @SerializedName("final")
    @Expose
    private Integer _final;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("tobaccoUser")
    @Expose
    private String tobaccoUser;
    @SerializedName("sumInsured")
    @Expose
    private String sumInsured;
    @SerializedName("plan_name")
    @Expose
    private String planName;
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("msg")
    @Expose
    private String msg;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleValue() {
        return titleValue;
    }

    public void setTitleValue(String titleValue) {
        this.titleValue = titleValue;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGenderValue() {
        return genderValue;
    }

    public void setGenderValue(String genderValue) {
        this.genderValue = genderValue;
    }

    public String getMarritalStatus() {
        return marritalStatus;
    }

    public void setMarritalStatus(String marritalStatus) {
        this.marritalStatus = marritalStatus;
    }

    public String getMarritalStatusValue() {
        return marritalStatusValue;
    }

    public void setMarritalStatusValue(String marritalStatusValue) {
        this.marritalStatusValue = marritalStatusValue;
    }

    public String getProposerMobile() {
        return proposerMobile;
    }

    public void setProposerMobile(String proposerMobile) {
        this.proposerMobile = proposerMobile;
    }

    public String getProposerEmail() {
        return proposerEmail;
    }

    public void setProposerEmail(String proposerEmail) {
        this.proposerEmail = proposerEmail;
    }

    public String getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(String annualIncome) {
        this.annualIncome = annualIncome;
    }

    public String getOccuption() {
        return occuption;
    }

    public void setOccuption(String occuption) {
        this.occuption = occuption;
    }

    public String getOccuptionValue() {
        return occuptionValue;
    }

    public void setOccuptionValue(String occuptionValue) {
        this.occuptionValue = occuptionValue;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getEducationValue() {
        return educationValue;
    }

    public void setEducationValue(String educationValue) {
        this.educationValue = educationValue;
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

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNomineeTitle() {
        return nomineeTitle;
    }

    public void setNomineeTitle(String nomineeTitle) {
        this.nomineeTitle = nomineeTitle;
    }

    public String getNomineeTitleValue() {
        return nomineeTitleValue;
    }

    public void setNomineeTitleValue(String nomineeTitleValue) {
        this.nomineeTitleValue = nomineeTitleValue;
    }

    public String getNomineeFirstName() {
        return nomineeFirstName;
    }

    public void setNomineeFirstName(String nomineeFirstName) {
        this.nomineeFirstName = nomineeFirstName;
    }

    public String getNomineeLastName() {
        return nomineeLastName;
    }

    public void setNomineeLastName(String nomineeLastName) {
        this.nomineeLastName = nomineeLastName;
    }

    public String getNomineeGender() {
        return nomineeGender;
    }

    public void setNomineeGender(String nomineeGender) {
        this.nomineeGender = nomineeGender;
    }

    public String getNomineeGenderValue() {
        return nomineeGenderValue;
    }

    public void setNomineeGenderValue(String nomineeGenderValue) {
        this.nomineeGenderValue = nomineeGenderValue;
    }

    public String getNomineeMarritalStatus() {
        return nomineeMarritalStatus;
    }

    public void setNomineeMarritalStatus(String nomineeMarritalStatus) {
        this.nomineeMarritalStatus = nomineeMarritalStatus;
    }

    public String getNomineeMarritalStatusValue() {
        return nomineeMarritalStatusValue;
    }

    public void setNomineeMarritalStatusValue(String nomineeMarritalStatusValue) {
        this.nomineeMarritalStatusValue = nomineeMarritalStatusValue;
    }

    public String getNomineeRelation() {
        return nomineeRelation;
    }

    public void setNomineeRelation(String nomineeRelation) {
        this.nomineeRelation = nomineeRelation;
    }

    public String getNomineeRelationValue() {
        return nomineeRelationValue;
    }

    public void setNomineeRelationValue(String nomineeRelationValue) {
        this.nomineeRelationValue = nomineeRelationValue;
    }

    public String getNomineeMobileNo() {
        return nomineeMobileNo;
    }

    public void setNomineeMobileNo(String nomineeMobileNo) {
        this.nomineeMobileNo = nomineeMobileNo;
    }

    public String getNomineeDob() {
        return nomineeDob;
    }

    public void setNomineeDob(String nomineeDob) {
        this.nomineeDob = nomineeDob;
    }

    public String getAppointeeTitle() {
        return appointeeTitle;
    }

    public void setAppointeeTitle(String appointeeTitle) {
        this.appointeeTitle = appointeeTitle;
    }

    public String getAppointeeTitleValue() {
        return appointeeTitleValue;
    }

    public void setAppointeeTitleValue(String appointeeTitleValue) {
        this.appointeeTitleValue = appointeeTitleValue;
    }

    public String getAppointeeFirstName() {
        return appointeeFirstName;
    }

    public void setAppointeeFirstName(String appointeeFirstName) {
        this.appointeeFirstName = appointeeFirstName;
    }

    public String getAppointeeLastName() {
        return appointeeLastName;
    }

    public void setAppointeeLastName(String appointeeLastName) {
        this.appointeeLastName = appointeeLastName;
    }

    public String getAppointeeGender() {
        return appointeeGender;
    }

    public void setAppointeeGender(String appointeeGender) {
        this.appointeeGender = appointeeGender;
    }

    public String getAppointeeGenderValue() {
        return appointeeGenderValue;
    }

    public void setAppointeeGenderValue(String appointeeGenderValue) {
        this.appointeeGenderValue = appointeeGenderValue;
    }

    public String getAppointeeMarritalStatus() {
        return appointeeMarritalStatus;
    }

    public void setAppointeeMarritalStatus(String appointeeMarritalStatus) {
        this.appointeeMarritalStatus = appointeeMarritalStatus;
    }

    public String getAppointeeMarritalStatusValue() {
        return appointeeMarritalStatusValue;
    }

    public void setAppointeeMarritalStatusValue(String appointeeMarritalStatusValue) {
        this.appointeeMarritalStatusValue = appointeeMarritalStatusValue;
    }

    public String getAppointeeRelation() {
        return appointeeRelation;
    }

    public void setAppointeeRelation(String appointeeRelation) {
        this.appointeeRelation = appointeeRelation;
    }

    public String getAppointeeRelationValue() {
        return appointeeRelationValue;
    }

    public void setAppointeeRelationValue(String appointeeRelationValue) {
        this.appointeeRelationValue = appointeeRelationValue;
    }

    public String getAppointeeMobileNo() {
        return appointeeMobileNo;
    }

    public void setAppointeeMobileNo(String appointeeMobileNo) {
        this.appointeeMobileNo = appointeeMobileNo;
    }

    public String getAppointeeDob() {
        return appointeeDob;
    }

    public void setAppointeeDob(String appointeeDob) {
        this.appointeeDob = appointeeDob;
    }

    public String getBusinessBelongs() {
        return businessBelongs;
    }

    public void setBusinessBelongs(String businessBelongs) {
        this.businessBelongs = businessBelongs;
    }

    public String getBusinessBelongsValue() {
        return businessBelongsValue;
    }

    public void setBusinessBelongsValue(String businessBelongsValue) {
        this.businessBelongsValue = businessBelongsValue;
    }

    public String getExistingCover() {
        return existingCover;
    }

    public void setExistingCover(String existingCover) {
        this.existingCover = existingCover;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpAddress() {
        return empAddress;
    }

    public void setEmpAddress(String empAddress) {
        this.empAddress = empAddress;
    }

    public String getEmpSector() {
        return empSector;
    }

    public void setEmpSector(String empSector) {
        this.empSector = empSector;
    }

    public String getSpouseName() {
        return spouseName;
    }

    public void setSpouseName(String spouseName) {
        this.spouseName = spouseName;
    }

    public Integer getNet() {
        return net;
    }

    public void setNet(Integer net) {
        this.net = net;
    }

    public Integer getTax() {
        return tax;
    }

    public void setTax(Integer tax) {
        this.tax = tax;
    }

    public Integer getFinal() {
        return _final;
    }

    public void setFinal(Integer _final) {
        this._final = _final;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTobaccoUser() {
        return tobaccoUser;
    }

    public void setTobaccoUser(String tobaccoUser) {
        this.tobaccoUser = tobaccoUser;
    }

    public String getSumInsured() {
        return sumInsured;
    }

    public void setSumInsured(String sumInsured) {
        this.sumInsured = sumInsured;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

}