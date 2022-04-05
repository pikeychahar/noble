package com.dmw.noble.model_health;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuoteUpdate {

@SerializedName("success")
@Expose
private String success;
@SerializedName("msg")
@Expose
private String msg;
@SerializedName("quoteid")
@Expose
private String quoteid;
@SerializedName("company")
@Expose
private String company;
@SerializedName("planType")
@Expose
private String planType;
@SerializedName("PlanTenure")
@Expose
private String planTenure;
@SerializedName("sumInsured")
@Expose
private String sumInsured;
@SerializedName("premium")
@Expose
private String premium;
@SerializedName("serviceTax")
@Expose
private String serviceTax;
@SerializedName("totalPremium")
@Expose
private String totalPremium;
@SerializedName("schemeId")
@Expose
private String schemeId;

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

public String getQuoteid() {
return quoteid;
}

public void setQuoteid(String quoteid) {
this.quoteid = quoteid;
}

public String getCompany() {
return company;
}

public void setCompany(String company) {
this.company = company;
}

public String getPlanType() {
return planType;
}

public void setPlanType(String planType) {
this.planType = planType;
}

public String getPlanTenure() {
return planTenure;
}

public void setPlanTenure(String planTenure) {
this.planTenure = planTenure;
}

public String getSumInsured() {
return sumInsured;
}

public void setSumInsured(String sumInsured) {
this.sumInsured = sumInsured;
}

public String getPremium() {
return premium;
}

public void setPremium(String premium) {
this.premium = premium;
}

public String getServiceTax() {
return serviceTax;
}

public void setServiceTax(String serviceTax) {
this.serviceTax = serviceTax;
}

public String getTotalPremium() {
return totalPremium;
}

public void setTotalPremium(String totalPremium) {
this.totalPremium = totalPremium;
}

public String getSchemeId() {
return schemeId;
}

public void setSchemeId(String schemeId) {
this.schemeId = schemeId;
}

}