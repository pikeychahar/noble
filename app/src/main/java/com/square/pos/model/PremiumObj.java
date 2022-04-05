package com.square.pos.model;

import java.util.ArrayList;

public class PremiumObj {

    private Integer idvMin;
    private Integer idvMax;
    Float legal_liability_employee;
    Float legal_liability_paid_driver;
    Float pacoverfor_unnamed_person;
    Float tppd_restricted_to;
    Float fule_kit_tp;
    private String currentNcb;
    String road_side_assistance;
    String breakingAllowStatus;
    String previousInsurer;

    public String getCurrentNcb() {
        return currentNcb;
    }

    public void setCurrentNcb(String currentNcb) {
        this.currentNcb = currentNcb;
    }

    public String getRoad_side_assistance() {
        return road_side_assistance;
    }

    public void setRoad_side_assistance(String road_side_assistance) {
        this.road_side_assistance = road_side_assistance;
    }

    public String getBreakingAllowStatus() {
        return breakingAllowStatus;
    }

    public void setBreakingAllowStatus(String breakingAllowStatus) {
        this.breakingAllowStatus = breakingAllowStatus;
    }

    private Integer inspection;
    private String logo, company, idv, od, ncb, tenure, policyType, zeroDept, tp, pa, net, gst,
            tataFlag, totalPremium, startDate, endDate;
    private ArrayList<String> arrayCover = new ArrayList<>();

    public Float getFuelKitTp() {
        return fule_kit_tp;
    }

    public void setFuelKitTp(Float fule_kit_tp) {
        this.fule_kit_tp = fule_kit_tp;
    }

    public Float getLegal_liability_employee() {
        return legal_liability_employee;
    }

    public void setLegal_liability_employee(Float legal_liability_employee) {
        this.legal_liability_employee = legal_liability_employee;
    }

    public Float getLegal_liability_paid_driver() {
        return legal_liability_paid_driver;
    }

    public void setLegal_liability_paid_driver(Float legal_liability_paid_driver) {
        this.legal_liability_paid_driver = legal_liability_paid_driver;
    }

    public Float getPacoverfor_unnamed_person() {
        return pacoverfor_unnamed_person;
    }

    public void setPacoverfor_unnamed_person(Float pacoverfor_unnamed_person) {
        this.pacoverfor_unnamed_person = pacoverfor_unnamed_person;
    }

    public Float getTppd_restricted_to() {
        return tppd_restricted_to;
    }

    public void setTppd_restricted_to(Float tppd_restricted_to) {
        this.tppd_restricted_to = tppd_restricted_to;
    }

    public Integer getInspection() {
        return inspection;
    }

    public void setInspection(Integer inspection) {
        this.inspection = inspection;
    }


    public ArrayList<String> getArrayCover() {
        return arrayCover;
    }

    public void setArrayCover(ArrayList<String> arrayCover) {
        this.arrayCover = arrayCover;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
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

    public String getPreviousInsurer() {
        return previousInsurer;
    }

    public void setPreviousInsurer(String previousInsurer) {
        this.previousInsurer = previousInsurer;
    }

}