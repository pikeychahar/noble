package com.square.pos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FilterCar {

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
    @SerializedName("anti_theft_device")
    @Expose
    private String antiTheftDevice;
    @SerializedName("vehicle_modified_for_handicap")
    @Expose
    private String vehicleModifiedForHandicap;
    @SerializedName("tppd_restricted_to")
    @Expose
    private String tppdRestrictedTo;
    @SerializedName("voluntary_excess")
    @Expose
    private String voluntaryExcess;
    @SerializedName("voluntary_excess_value")
    @Expose
    private String voluntaryExcessValue;
    @SerializedName("member_of_aai")
    @Expose
    private String memberOfAai;
    @SerializedName("association_name")
    @Expose
    private String associationName;
    @SerializedName("member_name")
    @Expose
    private String memberName;
    @SerializedName("membership_expiry_date")
    @Expose
    private String membershipExpiryDate;
    @SerializedName("electrical_accessory")
    @Expose
    private String electricalAccessory;
    @SerializedName("electrical_accessory_value")
    @Expose
    private String electricalAccessoryValue;
    @SerializedName("non_electrical_accessory")
    @Expose
    private String nonElectricalAccessory;
    @SerializedName("non_electrical_accessory_value")
    @Expose
    private String nonElectricalAccessoryValue;
    @SerializedName("fule_kit")
    @Expose
    private String fuleKit;
    @SerializedName("fule_kit_value")
    @Expose
    private String fuleKitValue;
    @SerializedName("pacoverfor_unnamed_person")
    @Expose
    private String pacoverforUnnamedPerson;
    @SerializedName("pacoverfor_unnamed_person_value")
    @Expose
    private String pacoverforUnnamedPersonValue;
    @SerializedName("legal_liability_employee")
    @Expose
    private String legalLiabilityEmployee;
    @SerializedName("legal_liability_employee_value")
    @Expose
    private String legalLiabilityEmployeeValue;
    @SerializedName("legal_liability_paid_driver")
    @Expose
    private String legalLiabilityPaidDriver;
    @SerializedName("legal_liability_paid_driver_value")
    @Expose
    private String legalLiabilityPaidDriverValue;
    @SerializedName("fiber_glass_fuel_tank")
    @Expose
    private String fiberGlassFuelTank;
    @SerializedName("emergency_cover")
    @Expose
    private String emergencyCover;
    @SerializedName("consumables")
    @Expose
    private String consumables;
    @SerializedName("tyre_cover")
    @Expose
    private String tyreCover;
    @SerializedName("ncb_protection")
    @Expose
    private String ncbProtection;
    @SerializedName("engine_protector")
    @Expose
    private String engineProtector;
    @SerializedName("return_invoice")
    @Expose
    private String returnInvoice;
    @SerializedName("loss_of_key")
    @Expose
    private String lossOfKey;
    @SerializedName("road_side_assistance")
    @Expose
    private String roadSideAssistance;
    @SerializedName("passenger_assist_cover")
    @Expose
    private String passengerAssistCover;
    @SerializedName("hydrostatic_lock_cover")
    @Expose
    private String hydrostaticLockCover;
    @SerializedName("hospital_cash_cover")
    @Expose
    private String hospitalCashCover;
    @SerializedName("loss_personal_belonging")
    @Expose
    private String lossPersonalBelonging;
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

    public String getAntiTheftDevice() {
        return antiTheftDevice;
    }

    public void setAntiTheftDevice(String antiTheftDevice) {
        this.antiTheftDevice = antiTheftDevice;
    }

    public String getVehicleModifiedForHandicap() {
        return vehicleModifiedForHandicap;
    }

    public void setVehicleModifiedForHandicap(String vehicleModifiedForHandicap) {
        this.vehicleModifiedForHandicap = vehicleModifiedForHandicap;
    }

    public String getTppdRestrictedTo() {
        return tppdRestrictedTo;
    }

    public void setTppdRestrictedTo(String tppdRestrictedTo) {
        this.tppdRestrictedTo = tppdRestrictedTo;
    }

    public String getVoluntaryExcess() {
        return voluntaryExcess;
    }

    public void setVoluntaryExcess(String voluntaryExcess) {
        this.voluntaryExcess = voluntaryExcess;
    }

    public String getVoluntaryExcessValue() {
        return voluntaryExcessValue;
    }

    public void setVoluntaryExcessValue(String voluntaryExcessValue) {
        this.voluntaryExcessValue = voluntaryExcessValue;
    }

    public String getMemberOfAai() {
        return memberOfAai;
    }

    public void setMemberOfAai(String memberOfAai) {
        this.memberOfAai = memberOfAai;
    }

    public String getAssociationName() {
        return associationName;
    }

    public void setAssociationName(String associationName) {
        this.associationName = associationName;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMembershipExpiryDate() {
        return membershipExpiryDate;
    }

    public void setMembershipExpiryDate(String membershipExpiryDate) {
        this.membershipExpiryDate = membershipExpiryDate;
    }

    public String getElectricalAccessory() {
        return electricalAccessory;
    }

    public void setElectricalAccessory(String electricalAccessory) {
        this.electricalAccessory = electricalAccessory;
    }

    public String getElectricalAccessoryValue() {
        return electricalAccessoryValue;
    }

    public void setElectricalAccessoryValue(String electricalAccessoryValue) {
        this.electricalAccessoryValue = electricalAccessoryValue;
    }

    public String getNonElectricalAccessory() {
        return nonElectricalAccessory;
    }

    public void setNonElectricalAccessory(String nonElectricalAccessory) {
        this.nonElectricalAccessory = nonElectricalAccessory;
    }

    public String getNonElectricalAccessoryValue() {
        return nonElectricalAccessoryValue;
    }

    public void setNonElectricalAccessoryValue(String nonElectricalAccessoryValue) {
        this.nonElectricalAccessoryValue = nonElectricalAccessoryValue;
    }

    public String getFuleKit() {
        return fuleKit;
    }

    public void setFuleKit(String fuleKit) {
        this.fuleKit = fuleKit;
    }

    public String getFuleKitValue() {
        return fuleKitValue;
    }

    public void setFuleKitValue(String fuleKitValue) {
        this.fuleKitValue = fuleKitValue;
    }

    public String getPacoverforUnnamedPerson() {
        return pacoverforUnnamedPerson;
    }

    public void setPacoverforUnnamedPerson(String pacoverforUnnamedPerson) {
        this.pacoverforUnnamedPerson = pacoverforUnnamedPerson;
    }

    public String getPacoverforUnnamedPersonValue() {
        return pacoverforUnnamedPersonValue;
    }

    public void setPacoverforUnnamedPersonValue(String pacoverforUnnamedPersonValue) {
        this.pacoverforUnnamedPersonValue = pacoverforUnnamedPersonValue;
    }

    public String getLegalLiabilityEmployee() {
        return legalLiabilityEmployee;
    }

    public void setLegalLiabilityEmployee(String legalLiabilityEmployee) {
        this.legalLiabilityEmployee = legalLiabilityEmployee;
    }

    public String getLegalLiabilityEmployeeValue() {
        return legalLiabilityEmployeeValue;
    }

    public void setLegalLiabilityEmployeeValue(String legalLiabilityEmployeeValue) {
        this.legalLiabilityEmployeeValue = legalLiabilityEmployeeValue;
    }

    public String getLegalLiabilityPaidDriver() {
        return legalLiabilityPaidDriver;
    }

    public void setLegalLiabilityPaidDriver(String legalLiabilityPaidDriver) {
        this.legalLiabilityPaidDriver = legalLiabilityPaidDriver;
    }

    public String getLegalLiabilityPaidDriverValue() {
        return legalLiabilityPaidDriverValue;
    }

    public void setLegalLiabilityPaidDriverValue(String legalLiabilityPaidDriverValue) {
        this.legalLiabilityPaidDriverValue = legalLiabilityPaidDriverValue;
    }

    public String getFiberGlassFuelTank() {
        return fiberGlassFuelTank;
    }

    public void setFiberGlassFuelTank(String fiberGlassFuelTank) {
        this.fiberGlassFuelTank = fiberGlassFuelTank;
    }

    public String getEmergencyCover() {
        return emergencyCover;
    }

    public void setEmergencyCover(String emergencyCover) {
        this.emergencyCover = emergencyCover;
    }

    public String getConsumables() {
        return consumables;
    }

    public void setConsumables(String consumables) {
        this.consumables = consumables;
    }

    public String getTyreCover() {
        return tyreCover;
    }

    public void setTyreCover(String tyreCover) {
        this.tyreCover = tyreCover;
    }

    public String getNcbProtection() {
        return ncbProtection;
    }

    public void setNcbProtection(String ncbProtection) {
        this.ncbProtection = ncbProtection;
    }

    public String getEngineProtector() {
        return engineProtector;
    }

    public void setEngineProtector(String engineProtector) {
        this.engineProtector = engineProtector;
    }

    public String getReturnInvoice() {
        return returnInvoice;
    }

    public void setReturnInvoice(String returnInvoice) {
        this.returnInvoice = returnInvoice;
    }

    public String getLossOfKey() {
        return lossOfKey;
    }

    public void setLossOfKey(String lossOfKey) {
        this.lossOfKey = lossOfKey;
    }

    public String getRoadSideAssistance() {
        return roadSideAssistance;
    }

    public void setRoadSideAssistance(String roadSideAssistance) {
        this.roadSideAssistance = roadSideAssistance;
    }

    public String getPassengerAssistCover() {
        return passengerAssistCover;
    }

    public void setPassengerAssistCover(String passengerAssistCover) {
        this.passengerAssistCover = passengerAssistCover;
    }

    public String getHydrostaticLockCover() {
        return hydrostaticLockCover;
    }

    public void setHydrostaticLockCover(String hydrostaticLockCover) {
        this.hydrostaticLockCover = hydrostaticLockCover;
    }

    public String getHospitalCashCover() {
        return hospitalCashCover;
    }

    public void setHospitalCashCover(String hospitalCashCover) {
        this.hospitalCashCover = hospitalCashCover;
    }

    public String getLossPersonalBelonging() {
        return lossPersonalBelonging;
    }

    public void setLossPersonalBelonging(String lossPersonalBelonging) {
        this.lossPersonalBelonging = lossPersonalBelonging;
    }

}