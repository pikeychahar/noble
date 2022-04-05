package com.dmw.noble.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VehicleOther {
    @SerializedName("status")
    @Expose
    private String success;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("financier_city")
    @Expose
    private String financierCity;
    @SerializedName("financier_name")
    @Expose
    private String financierName;
    @SerializedName("hypothecation")
    @Expose
    private String hypothecation;
    @SerializedName("chassies_no")
    @Expose
    private String chassiesNo;
    @SerializedName("engine_no")
    @Expose
    private String engineNo;
    @SerializedName("quotation_id")
    @Expose
    private String quotationId;
    @SerializedName("reg_number")
    @Expose
    private String regNumber;

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

    public String getFinancierCity() {
        return financierCity;
    }

    public void setFinancierCity(String financierCity) {
        this.financierCity = financierCity;
    }

    public String getFinancierName() {
        return financierName;
    }

    public void setFinancierName(String financierName) {
        this.financierName = financierName;
    }

    public String getHypothecation() {
        return hypothecation;
    }

    public void setHypothecation(String hypothecation) {
        this.hypothecation = hypothecation;
    }

    public String getChassiesNo() {
        return chassiesNo;
    }

    public void setChassiesNo(String chassiesNo) {
        this.chassiesNo = chassiesNo;
    }

    public String getEngineNo() {
        return engineNo;
    }

    public void setEngineNo(String engineNo) {
        this.engineNo = engineNo;
    }

    public String getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(String quotationId) {
        this.quotationId = quotationId;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

}