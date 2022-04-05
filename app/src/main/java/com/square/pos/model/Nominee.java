package com.square.pos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Nominee {

    @SerializedName("status")
    @Expose
    private String success;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("nominee_name")
    @Expose
    private String nomineeName;
    @SerializedName("nominee_relation")
    @Expose
    private String nomineeRelation;
    @SerializedName("nominee_gender")
    @Expose
    private String nomineeGender;
    @SerializedName("nominee_dob")
    @Expose
    private String nomineeDob;
    @SerializedName("quotation_id")
    @Expose
    private String quotationId;

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

    public String getNomineeName() {
        return nomineeName;
    }

    public void setNomineeName(String nomineeName) {
        this.nomineeName = nomineeName;
    }

    public String getNomineeRelation() {
        return nomineeRelation;
    }

    public void setNomineeRelation(String nomineeRelation) {
        this.nomineeRelation = nomineeRelation;
    }

    public String getNomineeGender() {
        return nomineeGender;
    }

    public void setNomineeGender(String nomineeGender) {
        this.nomineeGender = nomineeGender;
    }

    public String getNomineeDob() {
        return nomineeDob;
    }

    public void setNomineeDob(String nomineeDob) {
        this.nomineeDob = nomineeDob;
    }


    public String getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(String quotationId) {
        this.quotationId = quotationId;
    }

}