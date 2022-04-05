package com.square.pos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewPremiumQuote {

    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("idv")
    @Expose
    private String idv;
    @SerializedName("idv_min")
    @Expose
    private Integer idvMin;
    @SerializedName("idv_max")
    @Expose
    private Integer idvMax;
    @SerializedName("od")
    @Expose
    private String od;
    @SerializedName("ncb")
    @Expose
    private String ncb;
    @SerializedName("tenure")
    @Expose
    private String tenure;
    @SerializedName("current_ncb")
    @Expose
    private String currentNcb;
    @SerializedName("policy_type")
    @Expose
    private String policyType;
    @SerializedName("zero_dept")
    @Expose
    private String zeroDept;
    @SerializedName("tp")
    @Expose
    private String tp;
    @SerializedName("pa")
    @Expose
    private String pa;
    @SerializedName("net")
    @Expose
    private String net;
    @SerializedName("gst")
    @Expose
    private String gst;
    @SerializedName("tata_flag")
    @Expose
    private String tataFlag;
    @SerializedName("total_premium")
    @Expose
    private String totalPremium;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
     @SerializedName("previous_insurer")
    @Expose
    private String previousInsurer;
    @SerializedName("electrical_accessory")
    @Expose
    private String electricalAccessory;
    @SerializedName("non_electrical_accessory")
    @Expose
    private String nonElectricalAccessory;
    @SerializedName("fule_kit")
    @Expose
    private String fuleKit;
    @SerializedName("vehicle_modified_for_handicap")
    @Expose
    private String vehicleModifiedForHandicap;
    @SerializedName("anti_theft_device")
    @Expose
    private String antiTheftDevice;
    @SerializedName("voluntary_excess")
    @Expose
    private String voluntaryExcess;
    @SerializedName("member_of_aai")
    @Expose
    private String memberOfAai;
    @SerializedName("tppd_restricted_to")
    @Expose
    private String tppdRestrictedTo;
    @SerializedName("pacoverfor_unnamed_person")
    @Expose
    private String pacoverforUnnamedPerson;
    @SerializedName("legal_liability_paid_driver")
    @Expose
    private String legalLiabilityPaidDriver;
    @SerializedName("legal_liability_employee")
    @Expose
    private String legalLiabilityEmployee;
    @SerializedName("fiber_glass_fuel_tank")
    @Expose
    private String fiberGlassFuelTank;
    @SerializedName("fule_kit_tp")
    @Expose
    private String fuleKitTp;
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
    @SerializedName("hospital_cash_cover")
    @Expose
    private String hospitalCashCover;
    @SerializedName("emergency_cover")
    @Expose
    private String emergencyCover;

    @SerializedName("BreakingAllowStatus")
    @Expose
    private String breakingAllowStatus;
    @SerializedName("loss_personal_belonging")
    @Expose
    private String lossPersonalBelonging;
    @SerializedName("inspection")
    @Expose
    private Integer inspection;
    @SerializedName("imt23")
    @Expose
    private String imt23;

    public String getImt34() {
        return imt34;
    }

    public void setImt34(String imt34) {
        this.imt34 = imt34;
    }

    @SerializedName("imt34")
    @Expose
    private String imt34;

    public String getImt23() {
        return imt23;
    }

    public void setImt23(String imt23) {
        this.imt23 = imt23;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getIdv() {
        return idv;
    }

    public void setIdv(String idv) {
        this.idv = idv;
    }

    public Integer getIdvMin() {
        return idvMin;
    }

    public void setIdvMin(Integer idvMin) {
        this.idvMin = idvMin;
    }

    public Integer getIdvMax() {
        return idvMax;
    }

    public void setIdvMax(Integer idvMax) {
        this.idvMax = idvMax;
    }

    public String getOd() {
        return od;
    }

    public void setOd(String od) {
        this.od = od;
    }

    public String getNcb() {
        return ncb;
    }

    public void setNcb(String ncb) {
        this.ncb = ncb;
    }

    public String getTenure() {
        return tenure;
    }

    public void setTenure(String tenure) {
        this.tenure = tenure;
    }

    public String getPolicyType() {
        return policyType;
    }

    public void setPolicyType(String policyType) {
        this.policyType = policyType;
    }

    public String getZeroDept() {
        return zeroDept;
    }

    public void setZeroDept(String zeroDept) {
        this.zeroDept = zeroDept;
    }

    public String getTp() {
        return tp;
    }

    public void setTp(String tp) {
        this.tp = tp;
    }

    public String getPa() {
        return pa;
    }

    public void setPa(String pa) {
        this.pa = pa;
    }

    public String getNet() {
        return net;
    }

    public void setNet(String net) {
        this.net = net;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getTataFlag() {
        return tataFlag;
    }

    public void setTataFlag(String tataFlag) {
        this.tataFlag = tataFlag;
    }

    public String getTotalPremium() {
        return totalPremium;
    }

    public void setTotalPremium(String totalPremium) {
        this.totalPremium = totalPremium;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getElectricalAccessory() {
        return electricalAccessory;
    }

    public void setElectricalAccessory(String electricalAccessory) {
        this.electricalAccessory = electricalAccessory;
    }

    public String getNonElectricalAccessory() {
        return nonElectricalAccessory;
    }

    public void setNonElectricalAccessory(String nonElectricalAccessory) {
        this.nonElectricalAccessory = nonElectricalAccessory;
    }

    public String getFuleKit() {
        return fuleKit;
    }

    public void setFuleKit(String fuleKit) {
        this.fuleKit = fuleKit;
    }

    public String getVehicleModifiedForHandicap() {
        return vehicleModifiedForHandicap;
    }

    public void setVehicleModifiedForHandicap(String vehicleModifiedForHandicap) {
        this.vehicleModifiedForHandicap = vehicleModifiedForHandicap;
    }

    public String getAntiTheftDevice() {
        return antiTheftDevice;
    }

    public void setAntiTheftDevice(String antiTheftDevice) {
        this.antiTheftDevice = antiTheftDevice;
    }

    public String getVoluntaryExcess() {
        return voluntaryExcess;
    }

    public void setVoluntaryExcess(String voluntaryExcess) {
        this.voluntaryExcess = voluntaryExcess;
    }

    public String getMemberOfAai() {
        return memberOfAai;
    }

    public void setMemberOfAai(String memberOfAai) {
        this.memberOfAai = memberOfAai;
    }

    public String getTppdRestrictedTo() {
        return tppdRestrictedTo;
    }

    public void setTppdRestrictedTo(String tppdRestrictedTo) {
        this.tppdRestrictedTo = tppdRestrictedTo;
    }

    public String getPacoverforUnnamedPerson() {
        return pacoverforUnnamedPerson;
    }

    public void setPacoverforUnnamedPerson(String pacoverforUnnamedPerson) {
        this.pacoverforUnnamedPerson = pacoverforUnnamedPerson;
    }

    public String getLegalLiabilityPaidDriver() {
        return legalLiabilityPaidDriver;
    }

    public void setLegalLiabilityPaidDriver(String legalLiabilityPaidDriver) {
        this.legalLiabilityPaidDriver = legalLiabilityPaidDriver;
    }

    public String getLegalLiabilityEmployee() {
        return legalLiabilityEmployee;
    }

    public void setLegalLiabilityEmployee(String legalLiabilityEmployee) {
        this.legalLiabilityEmployee = legalLiabilityEmployee;
    }

    public String getFiberGlassFuelTank() {
        return fiberGlassFuelTank;
    }

    public void setFiberGlassFuelTank(String fiberGlassFuelTank) {
        this.fiberGlassFuelTank = fiberGlassFuelTank;
    }

    public String getFuleKitTp() {
        return fuleKitTp;
    }

    public void setFuleKitTp(String fuleKitTp) {
        this.fuleKitTp = fuleKitTp;
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

    public String getHospitalCashCover() {
        return hospitalCashCover;
    }

    public void setHospitalCashCover(String hospitalCashCover) {
        this.hospitalCashCover = hospitalCashCover;
    }

    public String getEmergencyCover() {
        return emergencyCover;
    }

    public void setEmergencyCover(String emergencyCover) {
        this.emergencyCover = emergencyCover;
    }

    public String getLossPersonalBelonging() {
        return lossPersonalBelonging;
    }

    public void setLossPersonalBelonging(String lossPersonalBelonging) {
        this.lossPersonalBelonging = lossPersonalBelonging;
    }

    public Integer getInspection() {
        return inspection;
    }

    public void setInspection(Integer inspection) {
        this.inspection = inspection;
    }

    public String getCurrentNcb() {
        return currentNcb;
    }

    public void setCurrentNcb(String currentNcb) {
        this.currentNcb = currentNcb;
    }

    public String getBreakingAllowStatus() {
        return breakingAllowStatus;
    }

    public void setBreakingAllowStatus(String breakingAllowStatus) {
        this.breakingAllowStatus = breakingAllowStatus;
    }

    public String getPreviousInsurer() {
        return previousInsurer;
    }

    public void setPreviousInsurer(String previousInsurer) {
        this.previousInsurer = previousInsurer;
    }
}