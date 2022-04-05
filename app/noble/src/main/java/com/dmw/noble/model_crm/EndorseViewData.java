package com.dmw.noble.model_crm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EndorseViewData implements Serializable {

    @SerializedName("SrId")
    @Expose
    private String srId;
    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("Source")
    @Expose
    private String source;
    @SerializedName("Salutation")
    @Expose
    private String salutation;
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("MobileNumber")
    @Expose
    private String mobileNumber;
    @SerializedName("EmailId")
    @Expose
    private String emailId;
    @SerializedName("Gender")
    @Expose
    private String gender;
    @SerializedName("Dob")
    @Expose
    private String dob;
    @SerializedName("HouseNumber")
    @Expose
    private String houseNumber;
    @SerializedName("Location")
    @Expose
    private String location;
    @SerializedName("Pincode")
    @Expose
    private String pincode;
    @SerializedName("State")
    @Expose
    private String state;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("PancardNo")
    @Expose
    private String pancardNo;
    @SerializedName("AadharcardNo")
    @Expose
    private String aadharcardNo;
    @SerializedName("GstIn")
    @Expose
    private String gstIn;
    @SerializedName("RegistrationNo")
    @Expose
    private String registrationNo;
    @SerializedName("RegistrationYear")
    @Expose
    private String registrationYear;
    @SerializedName("GadiType")
    @Expose
    private String gadiType;
    @SerializedName("Make")
    @Expose
    private String make;
    @SerializedName("Model")
    @Expose
    private String model;
    @SerializedName("Variant")
    @Expose
    private String variant;
    @SerializedName("FuelType")
    @Expose
    private String fuelType;
    @SerializedName("EngineNo")
    @Expose
    private String engineNo;
    @SerializedName("ChassisNo")
    @Expose
    private String chassisNo;
    @SerializedName("CubicCapacity")
    @Expose
    private String cubicCapacity;
    @SerializedName("CngIdvStatus")
    @Expose
    private String cngIdvStatus;
    @SerializedName("CngIdv")
    @Expose
    private String cngIdv;
    @SerializedName("PaOwnerDriver")
    @Expose
    private String paOwnerDriver;
    @SerializedName("UnnamedPa")
    @Expose
    private String unnamedPa;
    @SerializedName("LegalLiability")
    @Expose
    private String legalLiability;
    @SerializedName("LegalLiabilityForEmp")
    @Expose
    private String legalLiabilityForEmp;
    @SerializedName("Ncb")
    @Expose
    private String ncb;
    @SerializedName("FinancierName")
    @Expose
    private String financierName;
    @SerializedName("NomineeFullName")
    @Expose
    private String nomineeFullName;
    @SerializedName("NomineeDob")
    @Expose
    private String nomineeDob;
    @SerializedName("NomineeRelation")
    @Expose
    private String nomineeRelation;
    @SerializedName("PreviousPolicyNo")
    @Expose
    private String previousPolicyNo;
    @SerializedName("PreviousPolicyExpiry")
    @Expose
    private String previousPolicyExpiry;
    @SerializedName("PreviouPolicyNcb")
    @Expose
    private String previouPolicyNcb;
    @SerializedName("PreviousPolicyInsurer")
    @Expose
    private String previousPolicyInsurer;

    @SerializedName("oldString")
    @Expose
    private String oldString;
    @SerializedName("success")
    @Expose
    private Boolean success;

    public String getSrId() {
        return srId;
    }

    public void setSrId(String srId) {
        this.srId = srId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
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

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getGstIn() {
        return gstIn;
    }

    public void setGstIn(String gstIn) {
        this.gstIn = gstIn;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getRegistrationYear() {
        return registrationYear;
    }

    public void setRegistrationYear(String registrationYear) {
        this.registrationYear = registrationYear;
    }

    public String getGadiType() {
        return gadiType;
    }

    public void setGadiType(String gadiType) {
        this.gadiType = gadiType;
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

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
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

    public String getCubicCapacity() {
        return cubicCapacity;
    }

    public void setCubicCapacity(String cubicCapacity) {
        this.cubicCapacity = cubicCapacity;
    }

    public String getCngIdvStatus() {
        return cngIdvStatus;
    }

    public void setCngIdvStatus(String cngIdvStatus) {
        this.cngIdvStatus = cngIdvStatus;
    }

    public String getCngIdv() {
        return cngIdv;
    }

    public void setCngIdv(String cngIdv) {
        this.cngIdv = cngIdv;
    }

    public String getPaOwnerDriver() {
        return paOwnerDriver;
    }

    public void setPaOwnerDriver(String paOwnerDriver) {
        this.paOwnerDriver = paOwnerDriver;
    }

    public String getUnnamedPa() {
        return unnamedPa;
    }

    public void setUnnamedPa(String unnamedPa) {
        this.unnamedPa = unnamedPa;
    }

    public String getLegalLiability() {
        return legalLiability;
    }

    public void setLegalLiability(String legalLiability) {
        this.legalLiability = legalLiability;
    }

    public String getLegalLiabilityForEmp() {
        return legalLiabilityForEmp;
    }

    public void setLegalLiabilityForEmp(String legalLiabilityForEmp) {
        this.legalLiabilityForEmp = legalLiabilityForEmp;
    }

    public String getNcb() {
        return ncb;
    }

    public void setNcb(String ncb) {
        this.ncb = ncb;
    }

    public String getFinancierName() {
        return financierName;
    }

    public void setFinancierName(String financierName) {
        this.financierName = financierName;
    }

    public String getNomineeFullName() {
        return nomineeFullName;
    }

    public void setNomineeFullName(String nomineeFullName) {
        this.nomineeFullName = nomineeFullName;
    }

    public String getNomineeDob() {
        return nomineeDob;
    }

    public void setNomineeDob(String nomineeDob) {
        this.nomineeDob = nomineeDob;
    }

    public String getNomineeRelation() {
        return nomineeRelation;
    }

    public void setNomineeRelation(String nomineeRelation) {
        this.nomineeRelation = nomineeRelation;
    }

    public String getPreviousPolicyNo() {
        return previousPolicyNo;
    }

    public void setPreviousPolicyNo(String previousPolicyNo) {
        this.previousPolicyNo = previousPolicyNo;
    }

    public String getPreviousPolicyExpiry() {
        return previousPolicyExpiry;
    }

    public void setPreviousPolicyExpiry(String previousPolicyExpiry) {
        this.previousPolicyExpiry = previousPolicyExpiry;
    }

    public String getPreviouPolicyNcb() {
        return previouPolicyNcb;
    }

    public void setPreviouPolicyNcb(String previouPolicyNcb) {
        this.previouPolicyNcb = previouPolicyNcb;
    }

    public String getPreviousPolicyInsurer() {
        return previousPolicyInsurer;
    }

    public void setPreviousPolicyInsurer(String previousPolicyInsurer) {
        this.previousPolicyInsurer = previousPolicyInsurer;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
    public String getOldString() {
        return oldString;
    }


}