package com.square.pos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProposalCompany {

@SerializedName("success")
@Expose
private String success;
@SerializedName("msg")
@Expose
private String msg;
@SerializedName("company")
@Expose
private String company;
@SerializedName("premium")
@Expose
private String premium;
@SerializedName("policy_start_date")
@Expose
private String policyStartDate;
@SerializedName("idv")
@Expose
private String idv;
@SerializedName("tenure")
@Expose
private String tenure;
@SerializedName("status")
@Expose
private String status;
@SerializedName("quotation_id")
@Expose
private String quotationId;
@SerializedName("net")
@Expose
private String net;
@SerializedName("gst")
@Expose
private String gst;
@SerializedName("od")
@Expose
private String od;
@SerializedName("tp")
@Expose
private String tp;

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

public String getCompany() {
return company;
}

public void setCompany(String company) {
this.company = company;
}

public String getPremium() {
return premium;
}

public void setPremium(String premium) {
this.premium = premium;
}

public String getPolicyStartDate() {
return policyStartDate;
}

public void setPolicyStartDate(String policyStartDate) {
this.policyStartDate = policyStartDate;
}

public String getIdv() {
return idv;
}

public void setIdv(String idv) {
this.idv = idv;
}

public String getTenure() {
return tenure;
}

public void setTenure(String tenure) {
this.tenure = tenure;
}

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public String getQuotationId() {
return quotationId;
}

public void setQuotationId(String quotationId) {
this.quotationId = quotationId;
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

public String getOd() {
return od;
}

public void setOd(String od) {
this.od = od;
}

public String getTp() {
return tp;
}

public void setTp(String tp) {
this.tp = tp;
}

}