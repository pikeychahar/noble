package com.square.pos.model_health.v2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cover {

@SerializedName("company_name")
@Expose
private String companyName;
@SerializedName("suminsured")
@Expose
private String suminsured;
@SerializedName("plan")
@Expose
private String plan;
@SerializedName("order_no")
@Expose
private String orderNo;
@SerializedName("features")
@Expose
private String features;
@SerializedName("plan_benifit_category")
@Expose
private String planBenifitCategory;
@SerializedName("plan_benifit_desc")
@Expose
private String planBenifitDesc;
@SerializedName("plan_benifit")
@Expose
private String planBenifit;

public String getCompanyName() {
return companyName;
}

public void setCompanyName(String companyName) {
this.companyName = companyName;
}

public String getSuminsured() {
return suminsured;
}

public void setSuminsured(String suminsured) {
this.suminsured = suminsured;
}

public String getPlan() {
return plan;
}

public void setPlan(String plan) {
this.plan = plan;
}

public String getOrderNo() {
return orderNo;
}

public void setOrderNo(String orderNo) {
this.orderNo = orderNo;
}

public String getFeatures() {
return features;
}

public void setFeatures(String features) {
this.features = features;
}

public String getPlanBenifitCategory() {
return planBenifitCategory;
}

public void setPlanBenifitCategory(String planBenifitCategory) {
this.planBenifitCategory = planBenifitCategory;
}

public String getPlanBenifitDesc() {
return planBenifitDesc;
}

public void setPlanBenifitDesc(String planBenifitDesc) {
this.planBenifitDesc = planBenifitDesc;
}

public String getPlanBenifit() {
return planBenifit;
}

public void setPlanBenifit(String planBenifit) {
this.planBenifit = planBenifit;
}

}