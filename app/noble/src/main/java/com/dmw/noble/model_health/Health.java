package com.dmw.noble.model_health;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Health {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("quotation_id")
@Expose
private String quotationId;
@SerializedName("company")
@Expose
private String company;
@SerializedName("policy_type")
@Expose
private String policyType;
@SerializedName("name")
@Expose
private String name;
@SerializedName("email")
@Expose
private String email;
@SerializedName("mobile")
@Expose
private String mobile;
@SerializedName("pdf")
@Expose
private String pdf;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public String getQuotationId() {
return quotationId;
}

public void setQuotationId(String quotationId) {
this.quotationId = quotationId;
}

public String getCompany() {
return company;
}

public void setCompany(String company) {
this.company = company;
}

public String getPolicyType() {
return policyType;
}

public void setPolicyType(String policyType) {
this.policyType = policyType;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getEmail() {
return email;
}

public void setEmail(String email) {
this.email = email;
}

public String getMobile() {
return mobile;
}

public void setMobile(String mobile) {
this.mobile = mobile;
}

public String getPdf() {
return pdf;
}

public void setPdf(String pdf) {
this.pdf = pdf;
}

}